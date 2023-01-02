package com.artemifyMusicStudio;

import android.os.Bundle;
import android.widget.TextView;

import com.artemifyMusicStudio.controller.SimpleButtonCommandCreator;
import com.artemifyMusicStudio.controller.CommandItemType;
import com.artemifyMusicStudio.controller.commandCreator.PageTransitionCommandCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * A RegularUserHomePage Activity
 */
public class RegularUserHomePage extends PageActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_user_home_page);

        // parse and updated service cache
        parseActivityServiceCache();
        this.activityServiceCache.setCurrentPageActivity(this);

        // populate button
        populateMenuCommandCreatorMap();
        populateIdMenuMap();
        populateButtons();
        // set user name
        TextView tv = findViewById(R.id.welcome_username);
        tv.append(activityServiceCache.getUserID());
    }

    @Override
    protected void populateMenuCommandCreatorMap() {
        ArrayList<CommandItemType> tempList = new ArrayList<>(
                List.of(
                        CommandItemType.INVOKE_SEARCH, CommandItemType.ENABLE_ADMIN_MODE,
                        CommandItemType.INVOKE_SONG_UPLOAD,
                        CommandItemType.INVOKE_CREATE_NEW_PLAYLIST,
                        CommandItemType.PROFILE_AND_SETTING,
                        CommandItemType.INVOKE_QUEUE,
                        CommandItemType.LOG_OUT)
        );
        menuCommandCreatorMap.put("TransitionCommandCreator", tempList);
    }

    @Override
    protected SimpleButtonCommandCreator getSimpleOnClickCommandCreator(String creatorType) {
        if ("TransitionCommandCreator".equals(creatorType)) {
            return new PageTransitionCommandCreator(this.activityServiceCache);
        }
        return null;
    }

    @Override
    protected void populateIdMenuMap(){
        idMenuItemMap.put(CommandItemType.INVOKE_SEARCH, R.id.search);
        idMenuItemMap.put(CommandItemType.INVOKE_QUEUE, R.id.queue_page);
        idMenuItemMap.put(CommandItemType.PROFILE_AND_SETTING, R.id.profile_setting);
        idMenuItemMap.put(CommandItemType.INVOKE_CREATE_NEW_PLAYLIST, R.id.create_playlist);
        idMenuItemMap.put(CommandItemType.INVOKE_SONG_UPLOAD, R.id.upload_song);
        idMenuItemMap.put(CommandItemType.ENABLE_ADMIN_MODE, R.id.enable_admin);
        idMenuItemMap.put(CommandItemType.LOG_OUT, R.id.log_out);
    }
}