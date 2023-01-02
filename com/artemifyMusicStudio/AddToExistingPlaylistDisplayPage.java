package com.artemifyMusicStudio;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.artemifyMusicStudio.controller.SimpleButtonCommandCreator;
import com.artemifyMusicStudio.controller.pageTransitionCommand.AddToExistingPlaylistCommand;
import com.artemifyMusicStudio.controller.pageTransitionCommand.ExitPageCommand;
import com.presenters.LanguagePresenter;
import com.useCase.PlaylistManager;
import com.useCase.UserAccess;

import java.util.ArrayList;

/**
 * This page display all existing play list to add
 */
public class AddToExistingPlaylistDisplayPage extends PageActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_existing_playlist_display_page);

        // parse service cache, searchResult and searchType
        parseActivityServiceCache();
        this.activityServiceCache.setCurrentPageActivity(this);

        // populate the button
        populateButtons();
    }


    @Override
    protected void populateButtons() {
        LanguagePresenter languagePresenter = this.activityServiceCache.getLanguagePresenter();
        PlaylistManager playlistServiceManager = this.activityServiceCache.getPlaylistManager();
        UserAccess acctServiceManager = this.activityServiceCache.getUserAcctServiceManager();
        String myUserID = this.activityServiceCache.getUserID();

        // reset to empty view
        resetViewState();

        // populate playlist buttons
        populatePublicPlaylistButtons(languagePresenter, acctServiceManager, playlistServiceManager,
                myUserID);
        populatePrivatePlaylistButtons(languagePresenter, acctServiceManager, playlistServiceManager,
                myUserID);

        // populate Exit button
        Button exitButton = findViewById(R.id.exit);
        exitButton.setOnClickListener(new ExitPageCommand(this.activityServiceCache));
    }

    protected void populatePublicPlaylistButtons(LanguagePresenter languagePresenter,
                                                 UserAccess acctServiceManager,
                                                 PlaylistManager playlistServiceManager,
                                                 String myUserID){
        ArrayList<Integer> playlistIDs = acctServiceManager.getListOfPlaylistsIDs(myUserID,
                "Public");
        ArrayList<String> playlistNames = playlistServiceManager.getListOfPlaylistNames(playlistIDs);
        // Populate Song display buttons
        populateTargetInfoButtons(languagePresenter,
                playlistIDs,playlistNames,R.id.playlist_display_layout);
    }

    protected void populatePrivatePlaylistButtons(LanguagePresenter languagePresenter,
                                                  UserAccess acctServiceManager,
                                                  PlaylistManager playlistServiceManager,
                                                  String myUserID){
        ArrayList<Integer> privatePlaylistIDs = acctServiceManager.getListOfPlaylistsIDs(myUserID,
                "Private");
        ArrayList<String> privatePlaylistNames = playlistServiceManager.getListOfPlaylistNames(privatePlaylistIDs);
        // Populate Song display buttons
        populateTargetInfoButtons(languagePresenter,
                privatePlaylistIDs,privatePlaylistNames,R.id.playlist_display_layout);
    }

    protected void populateTargetInfoButtons(LanguagePresenter languagePresenter,
                                             ArrayList<Integer> targetIDs,
                                             ArrayList<String> targetNames, Integer layoutID){
        // Get layout and create buttons
        LinearLayout publicSongDisplay = findViewById(layoutID);
        int count = 0;
        for (int targetID: targetIDs){
            String buttonDescription = languagePresenter.translateString(targetNames.get(count));
            Button button = new Button(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            button.setId(count);
            button.setText(buttonDescription);
            View.OnClickListener onClickListener = new AddToExistingPlaylistCommand(this.activityServiceCache,
                    Integer.parseInt(this.activityServiceCache.getTargetSongID()), targetID);
            button.setOnClickListener(onClickListener);

            // populate the button to the layout
            publicSongDisplay.addView(button);
            count += 1;
        }
        publicSongDisplay.setGravity(Gravity.CENTER);
    }

    private void resetViewState(){
        LinearLayout layout = findViewById(R.id.playlist_display_layout);
        layout.removeAllViews();
    }

    @Override
    protected SimpleButtonCommandCreator getSimpleOnClickCommandCreator(String creatorType) {
        return null;
    }

    @Override
    protected void populateIdMenuMap() {}

    @Override
    protected void populateMenuCommandCreatorMap() {}

}