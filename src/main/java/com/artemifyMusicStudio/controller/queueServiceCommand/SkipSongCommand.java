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

public class SkipSongCommand extends QueueServiceCommand{

    private final ActivityServiceCache activityServiceCache;
    private final LanguagePresenter languagePresenter;

    /**
     * Constructor for SkipSongCommand class
     * @param activityServiceCache instance of ActivityServiceCache class to parse important data
     *                             to conduct page activities
     * @param languagePresenter    instance of languagePresenter class to allow for output to screen
     * @param queueService         instance of Queue class to execute command using methods in Queue
     */
    public SkipSongCommand(ActivityServiceCache activityServiceCache,
                           LanguagePresenter languagePresenter, Queue queueService) {
        super(queueService);
        this.activityServiceCache = activityServiceCache;
        this.languagePresenter = languagePresenter;
    }

    @Override
    public void onClick(View view){
        PageActivity currentPageActivity = activityServiceCache.getCurrentPageActivity();
        int nowPlaying = activityServiceCache.getQueueManager().getNowPlaying();

        if(activityServiceCache.getSongManager().exists(nowPlaying)) {
            activityServiceCache.getQueueManager().popFromQueue();
            // Updated ActivityServiceCache.ser file
            GatewayCreator gatewayCreator = new GatewayCreator();
            IGateway ioGateway = gatewayCreator.createIGateway(FileType.SER,
                    currentPageActivity);
            try {
                ioGateway.saveToFile("ActivityServiceCache.ser", this.activityServiceCache);
            } catch (IOException e) {
                Log.e("warning", "IO exception");
            }
            String warningMsg =  this.languagePresenter.
                    translateString("Current song has been skipped, " +
                            "next song is being played.") ;
            Toast.makeText(currentPageActivity, warningMsg, Toast.LENGTH_LONG).show();
        } else {
            String warningMsg =  this.languagePresenter.
                    translateString("No song is currently being played.") ;
            Toast.makeText(currentPageActivity, warningMsg, Toast.LENGTH_LONG).show();
        }
    }

}
