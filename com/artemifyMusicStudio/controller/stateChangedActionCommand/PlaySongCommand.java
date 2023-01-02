package com.artemifyMusicStudio.controller.stateChangedActionCommand;

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

/**
 * A Command to play a Song
 */
public class PlaySongCommand implements View.OnClickListener{

    private final ActivityServiceCache activityServiceCache;
    private final PageActivity songDisplayPage;
    private final LanguagePresenter languagePresenter;
    private final int songId;
    private final Queue queueManager;

    /**
     * Constructor of PlaySongCommand
     * @param activityServiceCache a ActivityServiceCache object
     */
    public PlaySongCommand(ActivityServiceCache activityServiceCache){
        this.activityServiceCache = activityServiceCache;
        this.songId = Integer.parseInt(activityServiceCache.getTargetSongID());
        songDisplayPage = activityServiceCache.getCurrentPageActivity();
        languagePresenter = activityServiceCache.getLanguagePresenter();
        queueManager = activityServiceCache.getQueueManager();
    }

    /**
     * Execute the play song command
     * @param v a View object
     */
    @Override
    public void onClick(View v) {
        if (queueManager.getNowPlaying() == songId){
            displayFailMsg(songDisplayPage);
        }
        else{
            displaySuccessfulMsg(songDisplayPage);
            queueManager.setNowPlaying(songId);
            // updated the ActivityServiceCache.ser file
            PageActivity currentPageActivity = activityServiceCache.getCurrentPageActivity();
            GatewayCreator gatewayCreator = new GatewayCreator();
            IGateway ioGateway = gatewayCreator.createIGateway(FileType.SER,
                    currentPageActivity);
            try {
                ioGateway.saveToFile("ActivityServiceCache.ser", this.activityServiceCache);
            } catch (IOException e) {
                Log.e("warning", "IO exception");
            }
        }
    }

    private void displaySuccessfulMsg(PageActivity currentPageActivity) {
        String Msg =  languagePresenter.translateString("You are playing the song!");
        Toast.makeText(currentPageActivity, Msg, Toast.LENGTH_LONG).show();
    }

    private void displayFailMsg(PageActivity currentPageActivity) {
        String Msg =  languagePresenter.translateString("You are already played the song");
        Toast.makeText(currentPageActivity, Msg, Toast.LENGTH_LONG).show();
    }
}
