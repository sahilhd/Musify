package com.artemifyMusicStudio;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.artemifyMusicStudio.controller.CommandItemType;
import com.artemifyMusicStudio.controller.SimpleButtonCommandCreator;
import com.artemifyMusicStudio.controller.stateChangedActionCommand.LikePlaylistCommand;
import com.artemifyMusicStudio.controller.commandCreator.StateChangedActionCommandCreator;
import com.artemifyMusicStudio.controller.commandCreator.InfoDisplayCommandCreator;
import com.artemifyMusicStudio.controller.commandCreator.PageTransitionCommandCreator;
import com.useCase.PlaylistManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A PlaylistDisplayPage Activity
 */
public class PlaylistDisplayPage extends PageActivity {
    private int playlistID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_display_page);

        // parse and updated service cache
        parseActivityServiceCache();
        this.activityServiceCache.setCurrentPageActivity(this);
        // set up target
        playlistID = Integer.parseInt(activityServiceCache.getTargetPlaylistID());
        String artist = activityServiceCache.getPlaylistManager().getCreatorUsername(playlistID);
        activityServiceCache.setTargetUserID(artist);

        // populate button
        populateMenuCommandCreatorMap();
        populateIdMenuMap();
        populateButtons();
        setUpPlaylistInformation();

    }

    @Override
    protected SimpleButtonCommandCreator getSimpleOnClickCommandCreator(String creatorType) {
        switch (creatorType){
            case "PopupCommandCreator":
                return new InfoDisplayCommandCreator(this.activityServiceCache);
            case "TransitionCommandCreator":
                return new PageTransitionCommandCreator(this.activityServiceCache);
            case "ActionCommandCreator":
                return new StateChangedActionCommandCreator(this.activityServiceCache);
            default:
                return null;
        }
    }

    @Override
    protected void populateIdMenuMap() {
        idMenuItemMap.put(CommandItemType.VIEW_PLAYLIST_SONGS, R.id.view_playlist_songs);
        idMenuItemMap.put(CommandItemType.VIEW_CREATOR, R.id.view_creator_in_playlist);
        idMenuItemMap.put(CommandItemType.PLAY_PLAYLIST, R.id.play_playlist);
        idMenuItemMap.put(CommandItemType.EXIT_PAGE, R.id.exit);

    }

    @Override
    protected void populateMenuCommandCreatorMap() {
        ArrayList<CommandItemType> tempList1 = new ArrayList<>(
                Arrays.asList(CommandItemType.VIEW_PLAYLIST_SONGS,
                        CommandItemType.VIEW_CREATOR)
        );
        ArrayList<CommandItemType> tempList2 = new ArrayList<>(
                List.of(CommandItemType.EXIT_PAGE)
        );
        ArrayList<CommandItemType> tempList3 = new ArrayList<>(
                List.of(CommandItemType.PLAY_PLAYLIST)
        );
        menuCommandCreatorMap.put("PopupCommandCreator", tempList1);
        menuCommandCreatorMap.put("TransitionCommandCreator", tempList2);
        menuCommandCreatorMap.put("ActionCommandCreator", tempList3);
    }

    private void setUpPlaylistInformation(){

        // primary setup
        PlaylistManager playlistManager = activityServiceCache.getPlaylistManager();
        String playlistName = playlistManager.getPlaylistName(playlistID);
        int numLikes = playlistManager.getNumLikes(playlistID);
        String creatorName = playlistManager.getCreatorUsername(playlistID);
        String description = playlistManager.getPlaylistDescription(playlistID);
        String isPublic = playlistManager.IsPublic(playlistID)? "public" : "private";

        // setup TextView
        // display playlist name
        TextView playlistNameDisplay = findViewById(R.id.playlist_name_display);
        playlistNameDisplay.setText(playlistName);
        // display numLikes
        TextView numLikesDisplay = findViewById(R.id.display_num_likes);
        numLikesDisplay.setText(String.valueOf(numLikes));
        // display creatorName
        TextView creatorNameDisplay = findViewById(R.id.creator_info_display);
        creatorNameDisplay.setText(creatorName);
        // display playlist description
        TextView playlistDescriptionDisplay = findViewById(R.id.playlist_description_display);
        playlistDescriptionDisplay.setText(description);
        // display public/private
        TextView visibility = findViewById(R.id.song_public_display);
        visibility.setText(isPublic);

        // setup ImageView
        ImageButton imageButton = findViewById(R.id.display_like_playlist_button);
        if (checkUnlike(playlistID)){
            imageButton.setBackgroundResource(R.drawable.empty_heart);
        } else{
            imageButton.setBackgroundResource(R.drawable.like_button);
        }
        String userID = this.activityServiceCache.getUserID();
        imageButton.setOnClickListener(new LikePlaylistCommand(activityServiceCache, userID, playlistID));
    }

    private boolean checkUnlike(int playlistID){
        String userId = activityServiceCache.getUserID();
        return !activityServiceCache.getUserAcctServiceManager().getUserLikedPlaylistsIDs(userId).contains(playlistID);
    }
}