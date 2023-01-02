package com.artemifyMusicStudio.controller.pageTransitionCommand;

import android.content.Intent;
import android.view.View;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.PageActivity;
import com.artemifyMusicStudio.ViewQueuePage;

/**
 * A InvokeQueueCommand to handle the request when user want to view queue in RegularUserHomePage
 */

public class ViewQueueCommand implements View.OnClickListener {
    private final ActivityServiceCache activityServiceCache;

    public ViewQueueCommand(ActivityServiceCache activityServiceCache){
        this.activityServiceCache = activityServiceCache;
    }

    @Override
    public void onClick(View v) {
        PageActivity currentPageActivity = activityServiceCache.getCurrentPageActivity();
        Intent it = new Intent(currentPageActivity, ViewQueuePage.class);
        it.putExtra("cache", this.activityServiceCache);
        currentPageActivity.startActivity(it);
    }
}
