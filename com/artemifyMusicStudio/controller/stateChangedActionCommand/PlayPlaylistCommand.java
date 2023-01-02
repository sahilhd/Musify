package com.artemifyMusicStudio.controller.stateChangedActionCommand;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.PageActivity;
import com.artemifyMusicStudio.QueueDisplayPage;
import com.entity.Playlist;
import com.gateway.FileType;
import com.gateway.GatewayCreator;
import com.gateway.IGateway;
import com.presenters.LanguagePresenter;
import com.useCase.Queue;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A PlayPlaylistCommand object to handle the user play all songs in playlist request
 */

public class PlayPlaylistCommand implements View.OnClickListener {
    private final ActivityServiceCache activityServiceCache;

    private final LanguagePresenter languagePresenter;
    private final String playlistID;

    /**
     * Constructor for PlayPlaylistCommand.
     *
     * @param activityServiceCache            a PageCreator Object
     * @param languagePresenter      a LanguagePresenter Object
     * @param playlistID a String represents the playlist's id
     */
    public PlayPlaylistCommand(ActivityServiceCache activityServiceCache,
                               LanguagePresenter languagePresenter, String playlistID) {
        this.activityServiceCache = activityServiceCache;
        this.languagePresenter = languagePresenter;
        this.playlistID = playlistID;
    }


    /**
     * Execute PlayPlaylistCommand to add all songs in playlist to top of queue.
     *
     */
    @Override
    public void onClick(View view) {
        Queue queueManager = this.activityServiceCache.getQueueManager();
        Playlist currPlaylist = this.activityServiceCache.getPlaylistManager().findPlaylist(Integer.parseInt(this.playlistID));
        ArrayList<Integer> allSongsID = currPlaylist.getSongs();
        int counter = 0;
        for(Integer songID : allSongsID){
            queueManager.addToQueue(songID, counter);
            counter++;
        }
        String addedMsg =  this.languagePresenter.translateString("Added to your playing queue");
        Toast.makeText(this.activityServiceCache.getCurrentPageActivity(),
                addedMsg, Toast.LENGTH_LONG).show();
        // Updated the ActivityServiceCache.ser file
        PageActivity currentPageActivity = activityServiceCache.getCurrentPageActivity();
        GatewayCreator gatewayCreator = new GatewayCreator();
        IGateway ioGateway = gatewayCreator.createIGateway(FileType.SER,
                currentPageActivity);
        try {
            ioGateway.saveToFile("ActivityServiceCache.ser", this.activityServiceCache);
        } catch (IOException e) {
            Log.e("warning", "IO exception");
        }
        // Takes user to QueueDisplayPage
        Intent it = new Intent(currentPageActivity, QueueDisplayPage.class);
        it.putExtra("cache", this.activityServiceCache);
        currentPageActivity.startActivity(it);
    }

}
