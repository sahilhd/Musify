package com.artemifyMusicStudio.controller.pageTransitionCommand;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.PageActivity;
import com.artemifyMusicStudio.PlaylistDisplayPage;
import com.gateway.FileType;
import com.gateway.GatewayCreator;
import com.gateway.IGateway;

import java.io.IOException;

/**
 * A command to invoke the PlaylistDisplayPage
 */
public class InvokePlaylistDisplayPage implements View.OnClickListener{
    private final ActivityServiceCache activityServiceCache;
    private final String targetPlaylistID;

    /**
     * Constructor of InvokePlaylistDisplayPage
     * @param activityServiceCache a ActivityServiceCache object
     * @param targetPlaylistID a target play list id
     */
    public InvokePlaylistDisplayPage(ActivityServiceCache activityServiceCache,
                                     String targetPlaylistID){
        this.activityServiceCache = activityServiceCache;
        this.targetPlaylistID = targetPlaylistID;
    }

    /**
     *  A method to invoke the playlist display page when user clicks the button
      * @param view a View object
     */
    @Override
    public void onClick(View view) {
        this.activityServiceCache.setTargetPlaylistID(this.targetPlaylistID);
        PageActivity currentPageActivity = activityServiceCache.getCurrentPageActivity();
        GatewayCreator gatewayCreator = new GatewayCreator();
        IGateway ioGateway = gatewayCreator.createIGateway(FileType.SER,
                currentPageActivity);
        try {
            ioGateway.saveToFile("ActivityServiceCache.ser", this.activityServiceCache);
        } catch (IOException e) {
            Log.e("warning", "IO exception");
        }
        Intent it = new Intent(currentPageActivity, PlaylistDisplayPage.class);
        it.putExtra("cache", this.activityServiceCache);
        currentPageActivity.startActivity(it);
    }
}
