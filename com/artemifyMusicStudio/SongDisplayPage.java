package com.artemifyMusicStudio;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.artemifyMusicStudio.controller.CommandItemType;
import com.artemifyMusicStudio.controller.SimpleButtonCommandCreator;
import com.artemifyMusicStudio.controller.commandCreator.StateChangedActionCommandCreator;
import com.artemifyMusicStudio.controller.commandCreator.InfoDisplayCommandCreator;
import com.artemifyMusicStudio.controller.commandCreator.PageTransitionCommandCreator;
import com.artemifyMusicStudio.controller.stateChangedActionCommand.LikeSongCommand;
import com.useCase.SongManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A SongDisplayPage Activity
 */
public class SongDisplayPage extends PageActivity {

    private int songId;
    private String artist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_display_page);

        // parse and updated service cache
        parseActivityServiceCache();
        this.activityServiceCache.setCurrentPageActivity(this);
        // set target
        songId = Integer.parseInt(activityServiceCache.getTargetSongID());
        artist = activityServiceCache.getSongManager().getSongArtist(songId);
        activityServiceCache.setTargetUserID(artist);
        // populate button
        populateMenuCommandCreatorMap();
        populateIdMenuMap();
        populateButtons();
        setUpSongInformation();
    }

    @Override
    protected SimpleButtonCommandCreator getSimpleOnClickCommandCreator(String creatorType) {
        switch (creatorType){
            case "TransitionCommandCreator":
                return new PageTransitionCommandCreator(this.activityServiceCache);
            case "PopupCommandCreator":
                return new InfoDisplayCommandCreator(this.activityServiceCache);
            case "ActionCommandCreator":
                return new StateChangedActionCommandCreator(this.activityServiceCache);
            default:
                return null;
        }
    }

    @Override
    protected void populateIdMenuMap() {
        idMenuItemMap.put(CommandItemType.EXIT_PAGE, R.id.exit);
        idMenuItemMap.put(CommandItemType.VIEW_LYRICS, R.id.view_lyrics);
        idMenuItemMap.put(CommandItemType.VIEW_CREATOR, R.id.view_creator);
        idMenuItemMap.put(CommandItemType.JUMP_TO_CREATE_NEW_PLAYLIST, R.id.display_add_to_new_playlist);
        idMenuItemMap.put(CommandItemType.INVOKE_ADD_TO_EXISTING_PLAYLIST_DISPLAY, R.id.display_add_to_existing_playlist);
        idMenuItemMap.put(CommandItemType.PLAY_SONG, R.id.play_song);
        idMenuItemMap.put(CommandItemType.ADD_TO_QUEUE, R.id.display_add_to_queue);
    }

    @Override
    protected void populateMenuCommandCreatorMap() {
        ArrayList<CommandItemType> tempList1 = new ArrayList<>(
                List.of(CommandItemType.EXIT_PAGE,
                        CommandItemType.JUMP_TO_CREATE_NEW_PLAYLIST,
                        CommandItemType.INVOKE_ADD_TO_EXISTING_PLAYLIST_DISPLAY)
        );
        ArrayList<CommandItemType> tempList2 = new ArrayList<>(
                List.of(CommandItemType.VIEW_LYRICS, CommandItemType.VIEW_CREATOR)
        );
        ArrayList<CommandItemType> tempList3 = new ArrayList<>(
                List.of(CommandItemType.PLAY_SONG, CommandItemType.ADD_TO_QUEUE)
        );
        menuCommandCreatorMap.put("TransitionCommandCreator", tempList1);
        menuCommandCreatorMap.put("PopupCommandCreator", tempList2);
        menuCommandCreatorMap.put("ActionCommandCreator", tempList3);
    }
    
    private void setUpSongInformation(){
        // primary setup
        SongManager songManager = activityServiceCache.getSongManager();
        String songName = songManager.getSongName(songId);
        String isPublic = songManager.isPublic(songId)? "public" : "private";
        int [] duration = songManager.getSongDuration(songId);
        String durationString = duration[0] + " : " + duration[1];
        // setup TextView
        ((TextView) findViewById(R.id.display_song_name)).setText(songName);
        ((TextView) findViewById(R.id.display_artist)).setText(artist);
        ((TextView) findViewById(R.id.display_like_song)).setText(String.valueOf(songManager.getSongNumLikes(songId)));
        ((TextView) findViewById(R.id.display_listen)).setText(String.valueOf(songManager.getSongNumListens(songId)));
        ((TextView) findViewById(R.id.display_song_public)).setText(isPublic);
        ((TextView) findViewById(R.id.display_song_duration)).setText(durationString);
        // setup ImageView
        ImageButton imageButton = findViewById(R.id.display_like_song_button);
        setupLikeButton(imageButton);
    }

    private boolean checkUnlike(int songId){
        String userId = activityServiceCache.getUserID();
        int userFavouritesID = activityServiceCache.getUserAcctServiceManager().getUserFavouritesID(userId);
        return !activityServiceCache.getPlaylistManager().getListOfSongsID(userFavouritesID).contains(songId);
    }

    private void setupLikeButton(ImageButton imageButton){
        if (checkUnlike(songId)){
            imageButton.setBackgroundResource(R.drawable.empty_heart);
        } else{
            imageButton.setBackgroundResource(R.drawable.like_button);
        }
        imageButton.setOnClickListener(new LikeSongCommand(activityServiceCache, songId));
    }
}