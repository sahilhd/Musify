package com.artemifyMusicStudio.controller.pageTransitionCommand;

import android.content.Intent;
import android.view.View;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.PageActivity;
import com.artemifyMusicStudio.RegularUserHomePage;

public class EnableRegularUserHomePageCommand implements View.OnClickListener{
    private final ActivityServiceCache activityServiceCache;

    public EnableRegularUserHomePageCommand(ActivityServiceCache activityServiceCache) {
        this.activityServiceCache = activityServiceCache;
    }

    @Override
    public void onClick(View view) {
        PageActivity currentPageActivity = activityServiceCache.getCurrentPageActivity();
        Intent it = new Intent(currentPageActivity, RegularUserHomePage.class);
        it.putExtra("cache", this.activityServiceCache);
        currentPageActivity.startActivity(it);
    }
}
