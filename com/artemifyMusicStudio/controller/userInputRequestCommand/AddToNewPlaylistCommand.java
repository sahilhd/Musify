package com.artemifyMusicStudio.controller.userInputRequestCommand;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.PageActivity;
import com.artemifyMusicStudio.PlaylistDisplayPage;
import com.gateway.FileType;
import com.gateway.GatewayCreator;
import com.gateway.IGateway;
import com.presenters.LanguagePresenter;
import com.useCase.PlaylistManager;
import com.useCase.SongManager;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * A AddToNewPlaylistCommand that will create a new playlist, then add the song to this newly created playlist
 */
public class AddToNewPlaylistCommand implements View.OnClickListener {
    private final ActivityServiceCache activityServiceCache;
    private final LanguagePresenter languagePresenter;
    private final EditText inputPlaylistName;
    private final EditText inputDescription;
    private final boolean isPublic;
    private final int songId;

    public AddToNewPlaylistCommand(ActivityServiceCache activityServiceCache,
                                    LanguagePresenter languagePresenter, EditText inputPlaylistName,
                                    EditText inputDescription, boolean isPublic){
        this.activityServiceCache = activityServiceCache;
        this.languagePresenter = languagePresenter;
        this.inputPlaylistName = inputPlaylistName;
        this.inputDescription = inputDescription;
        this.isPublic = isPublic;
        songId = Integer.parseInt(activityServiceCache.getTargetSongID());
    }

    @Override
    public void onClick(View v) {
        String playlistName = inputPlaylistName.getText().toString();
        String description = inputDescription.getText().toString();
        Timestamp dateTimeCreated = new Timestamp(System.currentTimeMillis());
        boolean success = extractValidData(playlistName, description);
        if (success){
            // add playlist
            PlaylistManager playlistManager = activityServiceCache.getPlaylistManager();
            String userID = activityServiceCache.getUserID();
            playlistManager.addPlaylist(playlistName, description, userID, dateTimeCreated, isPublic);
            int newPlaylistID = playlistManager.latestPlaylistID();
            activityServiceCache.getUserAcctServiceManager().addToUserPlaylist(userID,
                    newPlaylistID, isPublic);
            PageActivity currentPageActivity = activityServiceCache.getCurrentPageActivity();
            // add to playlist
            SongManager songManager = activityServiceCache.getSongManager();
            String songName = songManager.getSongName(songId);
            if (playlistManager.addableToPlaylist(newPlaylistID, songManager.isPublic(songId))){
                displaySuccessfulMsg(playlistName, currentPageActivity, songName);
                activityServiceCache.getPlaylistManager().addToPlaylist(newPlaylistID, songId);
            }else{
                displayFailMsg(playlistName, currentPageActivity, songName);
            }
            // set target playlist id
            activityServiceCache.setTargetPlaylistID(String.valueOf(newPlaylistID));

            // updated ActivityServiceCache.ser file
            GatewayCreator gatewayCreator = new GatewayCreator();
            IGateway ioGateway = gatewayCreator.createIGateway(FileType.SER,
                    currentPageActivity);
            try {
                ioGateway.saveToFile("ActivityServiceCache.ser", this.activityServiceCache);
            } catch (IOException e) {
                Log.e("warning", "IO exception");
            }

            // go to playlist display page
            goToPlaylistDisplayPage();
        }
    }

    private void displaySuccessfulMsg(String playlistName, PageActivity currentPageActivity, String songName) {
        String Msg =  this.languagePresenter.translateString("You create the playlist "
                + playlistName + " successfully and add the song " + songName);
        Toast.makeText(currentPageActivity, Msg, Toast.LENGTH_LONG).show();
    }

    private void displayFailMsg(String playlistName, PageActivity currentPageActivity, String songName) {
        String Msg =  this.languagePresenter.translateString("The privacy of the song "
                + songName +" does not match the playlist " + playlistName);
        Toast.makeText(currentPageActivity, Msg, Toast.LENGTH_LONG).show();
    }

    private boolean extractValidData(String playlistName, String desc){
        String [] defaultName = {"Favourite", "My Songs", "Private Songs"};
        if (playlistName.length() == 0 || desc.length() == 0){
            String Msg =  this.languagePresenter.translateString("You does not fill all the " +
                    "blanks");
            Toast.makeText(activityServiceCache.getCurrentPageActivity(), Msg, Toast.LENGTH_LONG).show();
            return false;
        }else {
            for (String defaultPlaylist: defaultName){
                if (playlistName.equals(defaultPlaylist)){
                    String Msg =  this.languagePresenter.translateString("The entered " +
                            "name is the same as default playlists' name.");
                    Toast.makeText(activityServiceCache.getCurrentPageActivity(), Msg, Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        }
        return true;
    }

    private void goToPlaylistDisplayPage(){
        PageActivity currentPageActivity = activityServiceCache.getCurrentPageActivity();
        Intent it = new Intent(currentPageActivity, PlaylistDisplayPage.class);
        it.putExtra("cache", this.activityServiceCache);
        currentPageActivity.startActivity(it);
    }
}
