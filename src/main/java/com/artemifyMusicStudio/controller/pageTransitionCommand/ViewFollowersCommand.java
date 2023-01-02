package com.artemifyMusicStudio.controller.pageTransitionCommand;

import android.content.Intent;
import android.view.View;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.FollowerFollowingDisplayPage;
import com.artemifyMusicStudio.PageActivity;

/**
 * A ViewFollowersCommand class to handle the view followers request from a user
 *
 */
public class ViewFollowersCommand implements View.OnClickListener {

    private final ActivityServiceCache activityServiceCache;

    /** Constructor of ViewFollowersCommand
     *
     * @param activityServiceCache a ActivityServiceCache object
     */
    public ViewFollowersCommand(ActivityServiceCache activityServiceCache){
        this.activityServiceCache = activityServiceCache;
    }

    /**
     * Execute the view followers user command
     */
    @Override
    public void onClick(View view) {
        PageActivity currentPageActivity = this.activityServiceCache.getCurrentPageActivity();
        Intent it = new Intent(currentPageActivity, FollowerFollowingDisplayPage.class);
        it.putExtra("cache", this.activityServiceCache);
        it.putExtra("displayType", "Follower");
        currentPageActivity.startActivity(it);
    }

}
