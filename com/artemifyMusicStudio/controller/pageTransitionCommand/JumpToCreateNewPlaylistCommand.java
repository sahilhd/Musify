package com.artemifyMusicStudio.controller.pageTransitionCommand;

import android.content.Intent;
import android.view.View;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.AddToNewPlaylistPage;
import com.artemifyMusicStudio.PageActivity;

public class JumpToCreateNewPlaylistCommand implements View.OnClickListener{

    private final ActivityServiceCache activityServiceCache;

    public JumpToCreateNewPlaylistCommand(ActivityServiceCache activityServiceCache){
        this.activityServiceCache = activityServiceCache;
    }

    @Override
    public void onClick(View v) {
        PageActivity currentPageActivity = activityServiceCache.getCurrentPageActivity();
        Intent it = new Intent(currentPageActivity, AddToNewPlaylistPage.class);
        it.putExtra("cache", this.activityServiceCache);
        currentPageActivity.startActivity(it);
    }

}
