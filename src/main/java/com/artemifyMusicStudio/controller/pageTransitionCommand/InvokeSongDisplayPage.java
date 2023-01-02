package com.artemifyMusicStudio.controller.pageTransitionCommand;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.PageActivity;
import com.artemifyMusicStudio.SongDisplayPage;
import com.gateway.FileType;
import com.gateway.GatewayCreator;
import com.gateway.IGateway;

import java.io.IOException;

/**
 * A command to invoke the SongDisplayPage
 */
public class InvokeSongDisplayPage implements View.OnClickListener{
    private final ActivityServiceCache activityServiceCache;
    private final String targetSongID;

    /**
     * Constructor of InvokeSongDisplayPage command
     * @param activityServiceCache a ActivityServiceCache
     * @param targetSongID a target song id
     */
    public InvokeSongDisplayPage(ActivityServiceCache activityServiceCache, String targetSongID){
        this.activityServiceCache = activityServiceCache;
        this.targetSongID = targetSongID;
    }

    /**
     * A method to execute the command when user click the button
     * @param view a View object
     */
    @Override
    public void onClick(View view) {
        this.activityServiceCache.setTargetSongID(this.targetSongID);
        String userId = activityServiceCache.getSongManager().getSongArtist(Integer.parseInt(targetSongID));
        this.activityServiceCache.setTargetUserID(userId);
        PageActivity currentPageActivity = activityServiceCache.getCurrentPageActivity();
        GatewayCreator gatewayCreator = new GatewayCreator();
        IGateway ioGateway = gatewayCreator.createIGateway(FileType.SER,
                currentPageActivity);
        try {
            ioGateway.saveToFile("ActivityServiceCache.ser", this.activityServiceCache);
        } catch (IOException e) {
            Log.e("warning", "IO exception");
        }

        Intent it = new Intent(currentPageActivity, SongDisplayPage.class);
        it.putExtra("cache", this.activityServiceCache);
        currentPageActivity.startActivity(it);
    }
}
