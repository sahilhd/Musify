package com.artemifyMusicStudio;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.artemifyMusicStudio.controller.CommandItemType;
import com.artemifyMusicStudio.controller.SimpleButtonCommandCreator;
import com.artemifyMusicStudio.controller.commandCreator.PageTransitionCommandCreator;
import com.artemifyMusicStudio.controller.commandCreator.UserInputRequestCommandCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * An AddToNewPlaylistPage
 */
public class AddToNewPlaylistPage extends PageActivity implements RadioGroup.OnCheckedChangeListener{
    private boolean isPublic = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_play_list_page);

        // parse and updated service cache
        parseActivityServiceCache();
        this.activityServiceCache.setCurrentPageActivity(this);

        // populate button
        populateMenuCommandCreatorMap();
        populateIdMenuMap();
        populateButtons();

        // set Radio Buttons
        RadioGroup radioGroup = findViewById(R.id.radioGroup_public_playlist);
        radioGroup.setOnCheckedChangeListener(this);

    }

    @Override
    protected SimpleButtonCommandCreator getSimpleOnClickCommandCreator(String creatorType) {
        EditText inputPlaylistName = findViewById(R.id.upload_playlist_name);
        EditText inputDescription = findViewById(R.id.playlist_desc);
        switch (creatorType){
            case "TransitionCommandCreator":
                return new PageTransitionCommandCreator(this.activityServiceCache);
            case "UserInputCommandCreator":
                return new UserInputRequestCommandCreator(this.activityServiceCache, inputPlaylistName,
                        inputDescription, isPublic);
            default:
                return null;
        }
    }

    @Override
    protected void populateIdMenuMap() {
        idMenuItemMap.put(CommandItemType.EXIT_PAGE, R.id.exit);
        idMenuItemMap.put(CommandItemType.ADD_TO_NEW_PLAYLIST, R.id.create_playlist);
    }

    @Override
    protected void populateMenuCommandCreatorMap() {
        ArrayList<CommandItemType> tempList1 = new ArrayList<>(
                List.of(CommandItemType.EXIT_PAGE)
        );
        ArrayList<CommandItemType> tempList2 = new ArrayList<>(
                List.of(CommandItemType.ADD_TO_NEW_PLAYLIST)
        );
        menuCommandCreatorMap.put("TransitionCommandCreator", tempList1);
        menuCommandCreatorMap.put("UserInputCommandCreator", tempList2);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        isPublic = checkedId == R.id.radio_public_playlist;
        resetCreateListener();
    }

    private void resetCreateListener(){
        SimpleButtonCommandCreator creator = getSimpleOnClickCommandCreator("UserInputCommandCreator");
        Button create = findViewById(R.id.create_playlist);
        create.setOnClickListener(creator.create(CommandItemType.ADD_TO_NEW_PLAYLIST));
    }
}
