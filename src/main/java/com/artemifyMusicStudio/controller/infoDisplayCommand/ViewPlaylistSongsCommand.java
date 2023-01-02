package com.artemifyMusicStudio.controller.infoDisplayCommand;

import android.content.Intent;
import android.view.View;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.PageActivity;
import com.artemifyMusicStudio.PlaylistSongsDisplayPage;


/**
 * A ViewPlaylistSongsCommand object to handle the user view all the songs in playlist request
 */

public class ViewPlaylistSongsCommand implements View.OnClickListener {

    private final ActivityServiceCache activityServiceCache;

    /**
     * Constructor for ViewPlaylistCommand.
     *
     * @param activityServiceCache            a PageCreator Object
     *
     */
    public ViewPlaylistSongsCommand(ActivityServiceCache activityServiceCache) {
        this.activityServiceCache = activityServiceCache;
    }

    /**
     * Execute LikePlaylistCommand to display all song names in the playlist.
     */
    @Override
    public void onClick(View view) {
        // Bring user to PlaylistSongsDisplayPage
        PageActivity currentPageActivity = this.activityServiceCache.getCurrentPageActivity();
        Intent it = new Intent(currentPageActivity, PlaylistSongsDisplayPage.class);
        it.putExtra("cache", this.activityServiceCache);
        currentPageActivity.startActivity(it);
    }

}
