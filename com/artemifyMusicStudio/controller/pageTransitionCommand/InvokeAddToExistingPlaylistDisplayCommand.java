package com.artemifyMusicStudio.controller.pageTransitionCommand;

import android.content.Intent;
import android.view.View;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.AddToExistingPlaylistDisplayPage;
import com.artemifyMusicStudio.PageActivity;

/**
 * Invoke the AddToExistingPlaylistDisplay Page
 */
public class InvokeAddToExistingPlaylistDisplayCommand implements View.OnClickListener{
    private final ActivityServiceCache activityServiceCache;

    /**
     * Constructor of InvokeAddToExistingPlaylistDisplayCommand
     * @param activityServiceCache ActivityServiceCache
     */
    public InvokeAddToExistingPlaylistDisplayCommand(ActivityServiceCache activityServiceCache){
        this.activityServiceCache = activityServiceCache;
    }

    @Override
    public void onClick(View view) {
        PageActivity currentPageActivity = this.activityServiceCache.getCurrentPageActivity();
        Intent it = new Intent(currentPageActivity, AddToExistingPlaylistDisplayPage.class);
        it.putExtra("cache", this.activityServiceCache);
        currentPageActivity.startActivity(it);
    }
}
