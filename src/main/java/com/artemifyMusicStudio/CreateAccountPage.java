package com.artemifyMusicStudio;

import android.os.Bundle;
import android.widget.EditText;

import com.artemifyMusicStudio.controller.CommandItemType;
import com.artemifyMusicStudio.controller.SimpleButtonCommandCreator;
import com.artemifyMusicStudio.controller.commandCreator.UserInputRequestCommandCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * An CreateAccountPage Activity
 */
public class CreateAccountPage extends PageActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_page);

        // parse and updated service cache
        parseActivityServiceCache();
        this.activityServiceCache.setCurrentPageActivity(this);

        // populate button
        populateMenuCommandCreatorMap();
        populateIdMenuMap();
        populateButtons();
    }

    @Override
    protected void populateIdMenuMap() {
        this.idMenuItemMap.put(CommandItemType.CREATE_REGULAR_ACCOUNT, R.id.conduct_account_creation);
    }

    @Override
    protected void populateMenuCommandCreatorMap() {
        ArrayList<CommandItemType> tempList1 = new ArrayList<>(
                List.of(CommandItemType.CREATE_REGULAR_ACCOUNT)
        );
        menuCommandCreatorMap.put("UserInputCommandCreator", tempList1);
    }

    @Override
    protected SimpleButtonCommandCreator getSimpleOnClickCommandCreator(String creatorType){
        EditText inputUserName = findViewById(R.id.inputUserName);
        EditText inputPassword = findViewById(R.id.inputPassword);
        if ("UserInputCommandCreator".equals(creatorType)) {
            return new UserInputRequestCommandCreator(this.activityServiceCache, inputUserName, inputPassword);
        }
        return null;
    }
}