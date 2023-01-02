package com.artemifyMusicStudio.controller.pageTransitionCommand;

import android.content.Intent;
import android.view.View;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.CreateAccountPage;
import com.artemifyMusicStudio.PageActivity;

/**
 * A CreateAccountCommand to handle the request of create account in MainPage
 */
public class CreateAccountCommand implements View.OnClickListener {
    private final ActivityServiceCache activityServiceCache;

    /**
     * Constructor of CreateAccountCommand class
     * @param activityServiceCache a ActivityServiceCache object
     */
    public CreateAccountCommand(ActivityServiceCache activityServiceCache){
        this.activityServiceCache = activityServiceCache;
    }

    @Override
    public void onClick(View view) {
        PageActivity currentPageActivity = this.activityServiceCache.getCurrentPageActivity();
        Intent it = new Intent(currentPageActivity, CreateAccountPage.class);
        it.putExtra("cache", this.activityServiceCache);
        currentPageActivity.startActivity(it);
    }
}
