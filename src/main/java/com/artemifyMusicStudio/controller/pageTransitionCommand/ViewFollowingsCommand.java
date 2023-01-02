package com.artemifyMusicStudio.controller.pageTransitionCommand;

import android.content.Intent;
import android.view.View;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.FollowerFollowingDisplayPage;
import com.artemifyMusicStudio.PageActivity;


/**
 * A ViewFollowingsCommand class to handle the view followings request from a user
 *
 */
public class ViewFollowingsCommand implements View.OnClickListener {

    protected final ActivityServiceCache activityServiceCache;


    /** Constructor of ViewFollowingsCommand
     *
     * @param activityServiceCache a ActivityServiceCache object
     */
    public ViewFollowingsCommand(ActivityServiceCache activityServiceCache){
        this.activityServiceCache = activityServiceCache;
    }

    /**
     * Execute the view followings user command
     */
    @Override
    public void onClick(View view) {
        PageActivity currentPageActivity = this.activityServiceCache.getCurrentPageActivity();
        Intent it = new Intent(currentPageActivity, FollowerFollowingDisplayPage.class);
        it.putExtra("cache", this.activityServiceCache);
        it.putExtra("displayType", "Following");
        currentPageActivity.startActivity(it);
    }

}
