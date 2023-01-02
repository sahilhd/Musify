package com.artemifyMusicStudio.controller.pageTransitionCommand;

import android.view.View;

import com.artemifyMusicStudio.ActivityServiceCache;

/**
 * A ExitPageCommand to handle various exit(close) page request
 */
public class ExitPageCommand implements View.OnClickListener {

    private final ActivityServiceCache activityServiceCache;

    public ExitPageCommand(ActivityServiceCache activityServiceCache){
            this.activityServiceCache = activityServiceCache;
    }

    @Override
    public void onClick(View v) {
        activityServiceCache.getCurrentPageActivity().finish();
    }
}
