package com.artemifyMusicStudio;

import android.os.Bundle;
import android.widget.TextView;

import com.artemifyMusicStudio.controller.CommandItemType;
import com.artemifyMusicStudio.controller.SimpleButtonCommandCreator;
import com.artemifyMusicStudio.controller.commandCreator.PageTransitionCommandCreator;
import com.artemifyMusicStudio.controller.commandCreator.UserInputRequestCommandCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * An AdminPage Activity
 */
public class AdminPage extends PageActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        //parse and updated service cache
        parseActivityServiceCache();
        this.activityServiceCache.setCurrentPageActivity(this);

        // populate button
        populateMenuCommandCreatorMap();
        populateIdMenuMap();
        populateButtons();
        //set user name
        TextView tv = findViewById(R.id.user_name);
        tv.append(activityServiceCache.getUserID());
    }

    @Override
    protected void populateMenuCommandCreatorMap() {
        // separate the commands into their respective types

        // deleting, banning, unbanning and grant admin rights are under user input commands
        ArrayList<CommandItemType> popupList = new ArrayList<>(
                List.of(CommandItemType.DELETE_USER, CommandItemType.BAN_USER,
                        CommandItemType.UNBAN_USER, CommandItemType.GRANT_ADMIN_RIGHT)
        );
        // quit admin mode and logging out are under transition command
        ArrayList<CommandItemType> transitionList = new ArrayList<>(
                List.of(CommandItemType.QUIT_ADMIN_MODE, CommandItemType.LOG_OUT)
        );

        // match the lists created above to their respective command creator types
        menuCommandCreatorMap.put("TransitionCommandCreator", transitionList);
        menuCommandCreatorMap.put("UserInputCommandCreator", popupList);
    }

    @Override
    protected void populateIdMenuMap() {
        // populate the commands with the respective ids' of their buttons in the xml
        idMenuItemMap.put(CommandItemType.LOG_OUT, R.id.adminLogOut);
        idMenuItemMap.put(CommandItemType.QUIT_ADMIN_MODE, R.id.exitAdminMode);
        idMenuItemMap.put(CommandItemType.DELETE_USER, R.id.deleteUser);
        idMenuItemMap.put(CommandItemType.BAN_USER, R.id.banUser);
        idMenuItemMap.put(CommandItemType.UNBAN_USER, R.id.unbanUser);
        idMenuItemMap.put(CommandItemType.GRANT_ADMIN_RIGHT, R.id.MakeAdmin);
    }

    @Override
    protected SimpleButtonCommandCreator getSimpleOnClickCommandCreator(String creatorType) {
        // get the required command creators
        switch (creatorType) {
            case "TransitionCommandCreator":
                return new PageTransitionCommandCreator(this.activityServiceCache);
            case "UserInputCommandCreator" :
                return new UserInputRequestCommandCreator(this.activityServiceCache);
            default:
                return null;
        }
    }
}