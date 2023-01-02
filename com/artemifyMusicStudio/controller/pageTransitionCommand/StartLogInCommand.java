package com.artemifyMusicStudio.controller.pageTransitionCommand;

import android.content.Intent;
import android.view.View;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.LoginPage;
import com.artemifyMusicStudio.PageActivity;

/**
 * A LogInCommand to allow user to send login request from the MainPage
 */

public class StartLogInCommand implements View.OnClickListener {
    private final ActivityServiceCache activityServiceCache;

    /**
     * Constructor of LogInCommand
     * @param activityServiceCache a ActivityServiceCache object
     */
    public StartLogInCommand(ActivityServiceCache activityServiceCache){
        this.activityServiceCache = activityServiceCache;
    }

    /**
     * Execute the login request by starting the LoginPage activity
     */
    @Override
    public void onClick(View view) {
        PageActivity currentPageActivity = this.activityServiceCache.getCurrentPageActivity();
        Intent it = new Intent(currentPageActivity, LoginPage.class);
        it.putExtra("cache", this.activityServiceCache);
        currentPageActivity.startActivity(it);
    }
}
