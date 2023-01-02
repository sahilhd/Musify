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

public class ShuffleQueueCommand extends QueueServiceCommand{

    private final ActivityServiceCache activityServiceCache;
    private final LanguagePresenter languagePresenter;

    /**
     * Constructor for ShuffleQueueCommand class
     * @param activityServiceCache instance of ActivityServiceCache class to parse important data
     *                             to conduct page activities
     * @param languagePresenter    instance of languagePresenter class to allow for output to screen
     * @param queueService         instance of Queue class to execute command using methods in Queue
     */
    public ShuffleQueueCommand(ActivityServiceCache activityServiceCache,
                                  LanguagePresenter languagePresenter, Queue queueService) {
        super(queueService);
        this.activityServiceCache = activityServiceCache;
        this.languagePresenter = languagePresenter;
    }

    @Override
    public void onClick(View view) {
        PageActivity currentPageActivity = activityServiceCache.getCurrentPageActivity();
        if (activityServiceCache.getQueueManager().getUpcomingSongs().size() == 0) {
            String warningMsg =  this.languagePresenter.
                    translateString("You have no upcoming songs in the queue to shuffle.") ;
            Toast.makeText(currentPageActivity, warningMsg, Toast.LENGTH_LONG).show();
        } else {
            activityServiceCache.getQueueManager().shuffleQueue();
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
                    translateString("Your upcoming songs in the queue have been shuffled.") ;
            Toast.makeText(currentPageActivity, warningMsg, Toast.LENGTH_LONG).show();
        }
    }

}
