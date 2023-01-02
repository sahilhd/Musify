package com.artemifyMusicStudio;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.artemifyMusicStudio.controller.CommandItemType;
import com.artemifyMusicStudio.controller.SimpleButtonCommandCreator;
import com.artemifyMusicStudio.controller.commandCreator.UserInputRequestCommandCreator;
import com.artemifyMusicStudio.controller.commandCreator.PageTransitionCommandCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * A UploadSongPage Activity
 */
public class UploadSongPage extends PageActivity implements RadioGroup.OnCheckedChangeListener {

    private boolean isPublic = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_song_page);

        // parse and updated service cache
        parseActivityServiceCache();
        this.activityServiceCache.setCurrentPageActivity(this);

        // populate button
        populateMenuCommandCreatorMap();
        populateIdMenuMap();
        populateButtons();

        // set Radio Buttons
        RadioGroup radioGroup = findViewById(R.id.radioGroup_public);
        radioGroup.setOnCheckedChangeListener(this);

    }

    @Override
    protected void populateMenuCommandCreatorMap() {
        ArrayList<CommandItemType> tempList1 = new ArrayList<>(
                List.of(CommandItemType.EXIT_PAGE)
        );
        ArrayList<CommandItemType> tempList2 = new ArrayList<>(
                List.of(CommandItemType.UPLOAD_SONG)
        );
        menuCommandCreatorMap.put("TransitionCommandCreator", tempList1);
        menuCommandCreatorMap.put("UserInputCommandCreator", tempList2);
    }

    @Override
    protected SimpleButtonCommandCreator getSimpleOnClickCommandCreator(String creatorType) {
        EditText inputSongName = findViewById(R.id.upload_song_name);
        EditText inputMinute = findViewById(R.id.min);
        EditText inputSecond = findViewById(R.id.sec);
        EditText inputLyrics = findViewById(R.id.lyrics);
        switch (creatorType){
            case "TransitionCommandCreator":
                return new PageTransitionCommandCreator(this.activityServiceCache);
            case "UserInputCommandCreator":
                return new UserInputRequestCommandCreator(this.activityServiceCache, inputSongName,
                        inputMinute, inputSecond, inputLyrics, isPublic);
            default:
                return null;
        }
    }

    @Override
    protected void populateIdMenuMap() {
        idMenuItemMap.put(CommandItemType.EXIT_PAGE, R.id.exit);
        idMenuItemMap.put(CommandItemType.UPLOAD_SONG, R.id.create_song);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        isPublic = checkedId == R.id.radio_public;
        resetCreateListener();
    }

    private void resetCreateListener(){
        SimpleButtonCommandCreator creator = getSimpleOnClickCommandCreator("UserInputCommandCreator");
        Button create = findViewById(R.id.create_song);
        create.setOnClickListener(creator.create(CommandItemType.UPLOAD_SONG));
    }
}