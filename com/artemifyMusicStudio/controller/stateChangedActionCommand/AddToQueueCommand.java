package com.artemifyMusicStudio.controller.stateChangedActionCommand;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.PageActivity;
import com.artemifyMusicStudio.QueueDisplayPage;
import com.gateway.FileType;
import com.gateway.GatewayCreator;
import com.gateway.IGateway;

import java.io.IOException;

/**
 * A Command to add a song to Queue
 */
public class AddToQueueCommand implements View.OnClickListener{

    private final ActivityServiceCache activityServiceCache;

    /**
     * Constructor of AddToQueueCommand
     * @param activityServiceCache a ActivityServiceCache object
     */
    public AddToQueueCommand(ActivityServiceCache activityServiceCache){
        this.activityServiceCache = activityServiceCache;
    }

    /**
     * Execute the add to queue command
     * @param v a View object
     */
    @Override
    public void onClick(View v) {
        PageActivity currentPageActivity = activityServiceCache.getCurrentPageActivity();
        int songId = Integer.parseInt(activityServiceCache.getTargetSongID());
        activityServiceCache.getQueueManager().addToQueueEnd(songId);

        // Updated the ActivityServiceCache.ser file
        GatewayCreator gatewayCreator = new GatewayCreator();
        IGateway ioGateway = gatewayCreator.createIGateway(FileType.SER,
                currentPageActivity);
        try {
            ioGateway.saveToFile("ActivityServiceCache.ser", this.activityServiceCache);
        } catch (IOException e) {
            Log.e("warning", "IO exception");
        }

        displaySuccessfulMsg(currentPageActivity);
        Intent it = new Intent(currentPageActivity, QueueDisplayPage.class);
        it.putExtra("cache", this.activityServiceCache);
        currentPageActivity.startActivity(it);
    }

    private void displaySuccessfulMsg(PageActivity currentPageActivity) {
        String Msg =  activityServiceCache.getLanguagePresenter().translateString("You add the song to queue!");
        Toast.makeText(currentPageActivity, Msg, Toast.LENGTH_LONG).show();
    }
}
