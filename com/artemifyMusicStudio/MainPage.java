package com.artemifyMusicStudio;

import android.os.Bundle;

import com.artemifyMusicStudio.controller.CommandItemType;
import com.artemifyMusicStudio.controller.SimpleButtonCommandCreator;
import com.artemifyMusicStudio.controller.commandCreator.PageTransitionCommandCreator;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A MainPage Activity
 */
public class MainPage extends PageActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        // parse service cache
        parseActivityServiceCache();
        this.activityServiceCache.setCurrentPageActivity(this);

        // populate button
        populateMenuCommandCreatorMap();
        populateIdMenuMap();
        populateButtons();
    }

    @Override
    protected void populateIdMenuMap() {
        this.idMenuItemMap.put(CommandItemType.LOG_IN, R.id.user_login);
        this.idMenuItemMap.put(CommandItemType.ACCOUNT_CREATION, R.id.create_account);
    }

    @Override
    protected void populateMenuCommandCreatorMap() {
        ArrayList<CommandItemType> tempList1 = new ArrayList<>(
                Arrays.asList(CommandItemType.LOG_IN, CommandItemType.ACCOUNT_CREATION)
        );
        menuCommandCreatorMap.put("TransitionCommandCreator", tempList1);
    }

    @Override
    protected SimpleButtonCommandCreator getSimpleOnClickCommandCreator(String creatorType){
        if ("TransitionCommandCreator".equals(creatorType)) {
            return new PageTransitionCommandCreator(this.activityServiceCache);
        }
        return null;
    }
}