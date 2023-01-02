package com.artemifyMusicStudio.controller.pageTransitionCommand;

import android.content.Intent;
import android.view.View;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.CreateNewPlayListPage;
import com.artemifyMusicStudio.PageActivity;

/**
 * To handle the request that a user choose to invoke the NewPlaylistPage to create new playlist in RegularUserHomePage
 */
public class InvokeNewPlaylistPageCommand implements View.OnClickListener {

    private final ActivityServiceCache activityServiceCache;

    public InvokeNewPlaylistPageCommand(ActivityServiceCache activityServiceCache){
        this.activityServiceCache = activityServiceCache;
    }

    @Override
    public void onClick(View v) {
        PageActivity currentPageActivity = activityServiceCache.getCurrentPageActivity();
        Intent it = new Intent(currentPageActivity, CreateNewPlayListPage.class);
        it.putExtra("cache", this.activityServiceCache);
        currentPageActivity.startActivity(it);
    }
}
