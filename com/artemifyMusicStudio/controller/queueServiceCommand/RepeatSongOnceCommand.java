package com.artemifyMusicStudio.controller.queueServiceCommand;

import android.view.View;
import android.widget.Toast;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.PageActivity;
import com.presenters.LanguagePresenter;
import com.useCase.Queue;

public class RepeatSongOnceCommand extends QueueServiceCommand{

    private final ActivityServiceCache activityServiceCache;
    private final LanguagePresenter languagePresenter;

    /**
     * Constructor for RepeatSongOnceCommand class
     * @param activityServiceCache instance of ActivityServiceCache class to parse important data
     *                             to conduct page activities
     * @param languagePresenter    instance of languagePresenter class to allow for output to screen
     * @param queueService         instance of Queue class to execute command using methods in Queue
     */
    public RepeatSongOnceCommand(ActivityServiceCache activityServiceCache,
                                  LanguagePresenter languagePresenter, Queue queueService) {
        super(queueService);
        this.activityServiceCache = activityServiceCache;
        this.languagePresenter = languagePresenter;
    }
    @Override
    public void onClick (View view) {
        PageActivity currentPageActivity = activityServiceCache.getCurrentPageActivity();
        int nowPlaying = activityServiceCache.getQueueManager().getNowPlaying();

        if(activityServiceCache.getSongManager().exists(nowPlaying)) {
            activityServiceCache.getQueueManager().addToQueue(nowPlaying, 0);
            String warningMsg =  this.languagePresenter.
                    translateString("Current song will be repeated once.") ;
            Toast.makeText(currentPageActivity, warningMsg, Toast.LENGTH_LONG).show();

        } else {
            String warningMsg =  this.languagePresenter.
                    translateString("No song is currently being played.") ;
            Toast.makeText(currentPageActivity, warningMsg, Toast.LENGTH_LONG).show();
        }
    }

}
