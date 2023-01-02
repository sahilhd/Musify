package com.artemifyMusicStudio.controller.queueServiceCommand;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.PageActivity;
import com.gateway.FileType;
import com.gateway.GatewayCreator;
import com.gateway.IGateway;
import com.presenters.LanguagePresenter;
import com.useCase.Queue;

import java.io.IOException;
import java.util.ArrayList;

public class PlayPreviousSongCommand extends QueueServiceCommand {

    private final ActivityServiceCache activityServiceCache;
    private final LanguagePresenter languagePresenter;

    /**
     * Constructor for PlayPreviousSongCommand class
     * @param activityServiceCache instance of ActivityServiceCache class to parse important data
     *                             to conduct page activities
     * @param languagePresenter    instance of languagePresenter class to allow for output to screen
     * @param queueService         instance of Queue class to execute command using methods in Queue
     */
    public PlayPreviousSongCommand(ActivityServiceCache activityServiceCache,
                                   LanguagePresenter languagePresenter, Queue queueService) {
        super(queueService);
        this.activityServiceCache = activityServiceCache;
        this.languagePresenter = languagePresenter;
    }

    @Override
    public void onClick(View view) {
        PageActivity currentPageActivity = activityServiceCache.getCurrentPageActivity();
        if (activityServiceCache.getQueueManager().getRecentlyPlayedSongs().size() == 0) {
            String warningMsg = this.languagePresenter.
                    translateString("No song has been played previously.");
            Toast.makeText(currentPageActivity, warningMsg, Toast.LENGTH_LONG).show();
        } else {
            ArrayList<Integer> recentlyPlayedSongs = activityServiceCache.getQueueManager().
                    getRecentlyPlayedSongs();
            int prevSong = recentlyPlayedSongs.get(0);
            recentlyPlayedSongs.remove(0);
            int currSong = activityServiceCache.getQueueManager().getNowPlaying();
            activityServiceCache.getQueueManager().addToQueue(currSong, 0);
            activityServiceCache.getQueueManager().setNowPlaying(prevSong);
            activityServiceCache.getQueueManager().setRecentlyPlayedSongs(recentlyPlayedSongs);
            GatewayCreator gatewayCreator = new GatewayCreator();
            IGateway ioGateway = gatewayCreator.createIGateway(FileType.SER,
                    currentPageActivity);
            try {
                ioGateway.saveToFile("ActivityServiceCache.ser", this.activityServiceCache);
            } catch (IOException e) {
                Log.e("warning", "IO exception");
            }
            String warningMsg = this.languagePresenter.
                    translateString("Previous is song is being played.");
            Toast.makeText(currentPageActivity, warningMsg, Toast.LENGTH_LONG).show();
        }
    }
}
