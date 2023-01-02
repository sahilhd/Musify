package com.artemifyMusicStudio;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artemifyMusicStudio.controller.CommandItemType;
import com.artemifyMusicStudio.controller.SimpleButtonCommandCreator;
import com.artemifyMusicStudio.controller.commandCreator.InfoDisplayCommandCreator;
import com.artemifyMusicStudio.controller.commandCreator.PageTransitionCommandCreator;
import com.artemifyMusicStudio.controller.pageTransitionCommand.ExitPageCommand;
import com.gateway.FileType;
import com.gateway.GatewayCreator;
import com.gateway.IGateway;
import com.presenters.LanguagePresenter;
import com.useCase.PlaylistManager;
import com.useCase.SongManager;
import com.useCase.UserAccess;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A ProfileAndSettingPage
 */
public class ProfileAndSettingPage extends PageActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_and_setting_page);

        // parse service cache, searchResult and searchType
        parseActivityServiceCache();
        this.activityServiceCache.setCurrentPageActivity(this);

        // set UserDisplayPage info
        setUpUserDisplayInfo();

        // populateButtons
        populateButtons();

        //set username
        TextView tv = findViewById(R.id.my_user_name);
        tv.setText(activityServiceCache.getUserID());
    }

    @Override
    protected void populateButtons() {
        LanguagePresenter languagePresenter = this.activityServiceCache.getLanguagePresenter();
        PlaylistManager playlistServiceManager = this.activityServiceCache.getPlaylistManager();
        UserAccess acctServiceManager = this.activityServiceCache.getUserAcctServiceManager();
        SongManager songManager = this.activityServiceCache.getSongManager();
        String myUserID = this.activityServiceCache.getUserID();

        // populate Song, Playlist and LikedPlaylistButtons
        populatePublicSongButtons(languagePresenter, acctServiceManager, playlistServiceManager,
                songManager, myUserID);
        populatePrivateSongButtons(languagePresenter, acctServiceManager, playlistServiceManager,
                songManager, myUserID);
        populateFavouriteSongsButtons(languagePresenter, acctServiceManager, playlistServiceManager,
                songManager, myUserID);
        populatePublicPlaylistButtons(languagePresenter, acctServiceManager, playlistServiceManager,
                myUserID);
        populatePrivatePlaylistButtons(languagePresenter, acctServiceManager, playlistServiceManager,
                myUserID);
        populateLikedPlaylistButtons(languagePresenter, acctServiceManager, playlistServiceManager,
                myUserID);

        // populate View Login History button
        populateViewLoginHistoryButton();

        // popular View Followers/Following buttons
        populateViewFollowerAndFollowingButtons();

        // populate Exit button
        Button exitButton = findViewById(R.id.exit);
        exitButton.setOnClickListener(new ExitPageCommand(this.activityServiceCache));
    }

    protected void populatePublicSongButtons(LanguagePresenter languagePresenter,
                                             UserAccess acctServiceManager,
                                             PlaylistManager playlistServiceManager,
                                             SongManager songManager,
                                             String myUserID){
        int publicSongsPlaylistID = acctServiceManager.getUserPublicSongsID(myUserID);
        ArrayList<Integer> songsIDs = playlistServiceManager.getListOfSongsID(publicSongsPlaylistID);
        ArrayList<String> songNames = songManager.getPlaylistSongNames(songsIDs);

        // Populate Song display buttons
        populateTargetInfoButtons(languagePresenter, songsIDs,songNames,R.id.my_display_public_songs,
                CommandItemType.INVOKE_SONG_DISPLAY);
    }

    protected void populatePrivateSongButtons(LanguagePresenter languagePresenter,
                                             UserAccess acctServiceManager,
                                             PlaylistManager playlistServiceManager,
                                             SongManager songManager,
                                             String myUserID){
        int privateSongsPlaylistID = acctServiceManager.getUserPrivateSongsID(myUserID);
        ArrayList<Integer> songsIDs = playlistServiceManager.getListOfSongsID(privateSongsPlaylistID);
        ArrayList<String> songNames = songManager.getPlaylistSongNames(songsIDs);

        // Populate Song display buttons
        populateTargetInfoButtons(languagePresenter, songsIDs,songNames,R.id.my_display_private_songs,
                CommandItemType.INVOKE_SONG_DISPLAY);
    }

    protected void populateFavouriteSongsButtons(LanguagePresenter languagePresenter,
                                                 UserAccess acctServiceManager,
                                                 PlaylistManager playlistServiceManager,
                                                 SongManager songManager,
                                                 String myUserID){
        int favouriteSongsPlaylistID = acctServiceManager.getUserFavouritesID(myUserID);
        ArrayList<Integer> favouriteSongIDs = playlistServiceManager.getListOfSongsID(favouriteSongsPlaylistID);
        ArrayList<String>  favouriteSongNames = new ArrayList<>();
        for (int songID: favouriteSongIDs){
            String creatorName = songManager.getSongArtist(songID);
            String songName = songManager.getSongName(songID);
            String composite_name = songName + ", created by " + creatorName;
            favouriteSongNames.add(composite_name);
        }

        // Populate Favourite
        populateTargetInfoButtons(languagePresenter, favouriteSongIDs,favouriteSongNames,
                R.id.my_display_favourites_songs,
                CommandItemType.INVOKE_SONG_DISPLAY);
    }

    protected void populatePublicPlaylistButtons(LanguagePresenter languagePresenter,
                                                 UserAccess acctServiceManager,
                                                 PlaylistManager playlistServiceManager,
                                                 String myUserID){
        ArrayList<Integer> playlistIDs = acctServiceManager.getListOfPlaylistsIDs(myUserID,
                "Public");
        ArrayList<String> playlistNames = playlistServiceManager.getListOfPlaylistNames(playlistIDs);
        // Populate Song display buttons
        populateTargetInfoButtons(languagePresenter, playlistIDs,playlistNames,R.id.my_display_public_playlist,
                CommandItemType.INVOKE_PLAYLIST_DISPLAY);
    }

    protected void populatePrivatePlaylistButtons(LanguagePresenter languagePresenter,
                                                 UserAccess acctServiceManager,
                                                 PlaylistManager playlistServiceManager,
                                                 String myUserID){
        ArrayList<Integer> privatePlaylistIDs = acctServiceManager.getListOfPlaylistsIDs(myUserID,
                "Private");
        ArrayList<String> privatePlaylistNames = playlistServiceManager.getListOfPlaylistNames(privatePlaylistIDs);
        // Populate Song display buttons
        populateTargetInfoButtons(languagePresenter, privatePlaylistIDs,privatePlaylistNames,R.id.my_display_private_playlist,
                CommandItemType.INVOKE_PLAYLIST_DISPLAY);
    }

    protected void populateLikedPlaylistButtons(LanguagePresenter languagePresenter,
                                                UserAccess acctServiceManager,
                                                PlaylistManager playlistServiceManager,
                                                String myUserID){
        ArrayList<Integer> likedPlaylistIDs = acctServiceManager.getUserLikedPlaylistsIDs(myUserID);
        ArrayList<String>  likedPlaylistNames = new ArrayList<>();
        for (int playlistID: likedPlaylistIDs){
            String creator_username = playlistServiceManager.getCreatorUsername(playlistID);
            String playlist_name = playlistServiceManager.getPlaylistName(playlistID);
            String composite_name = playlist_name + ", created by " + creator_username;
            likedPlaylistNames.add(composite_name);
        }
        populateTargetInfoButtons(languagePresenter, likedPlaylistIDs,likedPlaylistNames,
                R.id.my_display_liked_playlist, CommandItemType.INVOKE_PLAYLIST_DISPLAY);
    }

    protected void populateViewLoginHistoryButton(){
        InfoDisplayCommandCreator infoDisplayCommandCreator = new InfoDisplayCommandCreator(this.activityServiceCache);
        Button viewFollowerButton = findViewById(R.id.my_login_history);
        View.OnClickListener viewFollowerCommand = infoDisplayCommandCreator.create(CommandItemType.VIEW_LOGIN_HISTORY);
        viewFollowerButton.setOnClickListener(viewFollowerCommand);
    }

    protected void populateViewFollowerAndFollowingButtons(){
        // set target user to be the current user so that we can reuse some commands from UserDisplayPage
        String currUserID = this.activityServiceCache.getUserID();
        this.activityServiceCache.setTargetUserID(currUserID);
        // Updated ActivityServiceCache.ser file
        GatewayCreator gatewayCreator = new GatewayCreator();
        IGateway ioGateway = gatewayCreator.createIGateway(FileType.SER,
                this);
        try {
            ioGateway.saveToFile("ActivityServiceCache.ser", this.activityServiceCache);
        } catch (IOException e) {
            Log.e("warning", "IO exception");
        }

        PageTransitionCommandCreator pageTransitionCommandCreator = new PageTransitionCommandCreator(this.activityServiceCache);

        // populate view follower button
        Button viewFollowerButton = findViewById(R.id.my_view_followers);
        View.OnClickListener viewFollowerCommand = pageTransitionCommandCreator.create(CommandItemType.VIEW_FOLLOWERS);
        viewFollowerButton.setOnClickListener(viewFollowerCommand);

        // populate view following button
        Button viewFollowingButton = findViewById(R.id.my_view_followings);
        View.OnClickListener viewFollowingCommand = pageTransitionCommandCreator.create(CommandItemType.VIEW_FOLLOWINGS);
        viewFollowingButton.setOnClickListener(viewFollowingCommand);
    }

    protected void populateTargetInfoButtons(LanguagePresenter languagePresenter,
                                             ArrayList<Integer> targetIDs,
                                             ArrayList<String> targetNames, Integer layoutID,
                                             CommandItemType targetCommandType){
        // Get layout and create buttons
        LinearLayout targetEntityDisplay = findViewById(layoutID);
        resetViewState(targetEntityDisplay);

        int count = 0;
        PageTransitionCommandCreator pageTransitionCommandCreator = new PageTransitionCommandCreator(this.activityServiceCache);
        for (int targetID: targetIDs){
            String buttonDescription = languagePresenter.translateString(targetNames.get(count));
            Button button = new Button(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            button.setId(count);
            button.setText(buttonDescription);
            pageTransitionCommandCreator.setTargetID(Integer.toString(targetID));
            View.OnClickListener onClickListener = pageTransitionCommandCreator.create(targetCommandType);
            button.setOnClickListener(onClickListener);

            // populate the button to the layout
            targetEntityDisplay.addView(button);
            count += 1;
        }
        targetEntityDisplay.setGravity(Gravity.CENTER);
    }

    private void resetViewState(LinearLayout layout){
        layout.removeAllViews();
    }

    protected void setUpUserDisplayInfo(){
        // get the user's name for display
        String myUserID = activityServiceCache.getUserID();
        //calculate the user's accumulated likes
        int accumulateLikes = 0;
        //calculate the likes from this user's songs
        int publicSongs = activityServiceCache.getUserAcctServiceManager().getUserPublicSongsID(myUserID);
        int privateSongs = activityServiceCache.getUserAcctServiceManager().getUserPrivateSongsID(myUserID);
        ArrayList<Integer> publicSongsID = activityServiceCache.getPlaylistManager().getListOfSongsID(publicSongs);
        ArrayList<Integer> privateSongsID = activityServiceCache.getPlaylistManager().getListOfSongsID(privateSongs);
        for (int i: publicSongsID) {
            accumulateLikes += activityServiceCache.getSongManager().getSongNumLikes(i);
        }
        for (int i: privateSongsID) {
            accumulateLikes += activityServiceCache.getSongManager().getSongNumLikes(i);
        }
        //calculate the likes from this user's playlists
        ArrayList<Integer> publicPlaylists = activityServiceCache.getUserAcctServiceManager().
                getListOfPlaylistsIDs(myUserID, "Public");
        ArrayList<Integer> privatePlaylists = activityServiceCache.getUserAcctServiceManager().
                getListOfPlaylistsIDs(myUserID, "Private");
        for (int i: publicPlaylists) {
            accumulateLikes += activityServiceCache.getPlaylistManager().getNumLikes(i);
        }
        for (int i: privatePlaylists) {
            accumulateLikes += activityServiceCache.getPlaylistManager().getNumLikes(i);
        }
        String numFollowers = Integer.toString(activityServiceCache.getUserAcctServiceManager().getNumFollowers(myUserID));
        String numFollowing = Integer.toString(activityServiceCache.getUserAcctServiceManager().getNumFollowing(myUserID));
        String numLikes = Integer.toString(accumulateLikes);

        // populate num of follower info
        TextView numOfFollowersDisplay = findViewById(R.id.my_num_followers);
        numOfFollowersDisplay.setText(numFollowers);

        // populate num of following info
        TextView numOfFollowingDisplay = findViewById(R.id.my_num_followings);
        numOfFollowingDisplay.setText(numFollowing);

        // populate like info
        TextView numOfLikes = findViewById(R.id.my_num_of_likes);
        numOfLikes.setText(numLikes);
    }

    /**
     *
     * No usage in this class, no actual implementation
     */
    @Override
    protected SimpleButtonCommandCreator getSimpleOnClickCommandCreator(String creatorType) {
        return null;
    }

    /**
     * No usage in this class, no actual implementation
     */
    @Override
    protected void populateIdMenuMap() {}

    /**
     * No usage in this class, no actual implementation
     */
    @Override
    protected void populateMenuCommandCreatorMap() {}

}