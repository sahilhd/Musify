package com.artemifyMusicStudio.controller.infoDisplayCommand;

import android.content.Intent;
import android.view.View;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.PageActivity;
import com.artemifyMusicStudio.UserDisplayPage;

/**
 * A ViewCreatorCommand object to handle the user view the creator of playlist request
 */

public class ViewCreatorCommand implements View.OnClickListener {
    private final ActivityServiceCache activityServiceCache;

    /**
     * Constructor for ViewCreatorCommand.
     *
     * @param activityServiceCache            a PageCreator Object
     */
    public ViewCreatorCommand(ActivityServiceCache activityServiceCache) {
        this.activityServiceCache = activityServiceCache;
    }

    /**
     * Execute the ViewCreatorCommand by invoking UserDisplayPage of the creator
     */
    @Override
    public void onClick(View view) {
        // Bring user to User Display Page
        PageActivity currentPageActivity = this.activityServiceCache.getCurrentPageActivity();
        Intent it = new Intent(currentPageActivity, UserDisplayPage.class);
        it.putExtra("cache", this.activityServiceCache);
        currentPageActivity.startActivity(it);
    }


}
