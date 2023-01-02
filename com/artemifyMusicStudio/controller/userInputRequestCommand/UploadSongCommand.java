package com.artemifyMusicStudio.controller.userInputRequestCommand;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.PageActivity;
import com.artemifyMusicStudio.SongDisplayPage;
import com.gateway.FileType;
import com.gateway.GatewayCreator;
import com.gateway.IGateway;
import com.presenters.LanguagePresenter;
import com.useCase.SongManager;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * A UploadSongCommand to upload the song to the songManager and to public or private playlist depending on the
 * isPublic attribute of the song when uploading
 */
public class UploadSongCommand implements View.OnClickListener {

    private final ActivityServiceCache activityServiceCache;
    private final LanguagePresenter languagePresenter;
    private final EditText inputSongName;
    private final EditText inputMinute;
    private final EditText inputSecond;
    private final EditText inputLyrics;
    private final boolean isPublic;

    public UploadSongCommand(ActivityServiceCache activityServiceCache,
                             LanguagePresenter languagePresenter, EditText inputSongName,
                             EditText inputMinute, EditText inputSecond, EditText inputLyrics,
                             boolean isPublic){
        this.activityServiceCache = activityServiceCache;
        this.languagePresenter = languagePresenter;
        this.inputSongName = inputSongName;
        this.inputMinute = inputMinute;
        this.inputSecond = inputSecond;
        this.inputLyrics = inputLyrics;
        this.isPublic = isPublic;
    }

    @Override
    public void onClick(View v) {
        // get data
        String songName = inputSongName.getText().toString();
        String minuteString = inputMinute.getText().toString();
        String secondString = inputSecond.getText().toString();
        String artist = activityServiceCache.getUserID();
        Timestamp dateTimeCreated = new Timestamp(System.currentTimeMillis());
        String lyrics = inputLyrics.getText().toString();
        // validate data
        boolean success = extractValidData(songName, minuteString, secondString, lyrics);
        if (success){
            int minute = Integer.parseInt(minuteString);
            int second = Integer.parseInt(secondString);
            // add song
            SongManager songManager = activityServiceCache.getSongManager();
            songManager.addSong(songName, new int[]{minute, second}, artist,
                    dateTimeCreated, lyrics, isPublic);
            activityServiceCache.setTargetSongID(String.valueOf(songManager.latestSongID()));
            activityServiceCache.setTargetUserID(activityServiceCache.getUserID());

            // update the ActivityServiceCache.ser file
            PageActivity currentPageActivity = activityServiceCache.getCurrentPageActivity();
            GatewayCreator gatewayCreator = new GatewayCreator();
            IGateway ioGateway = gatewayCreator.createIGateway(FileType.SER,
                    currentPageActivity);
            try {
                ioGateway.saveToFile("ActivityServiceCache.ser", this.activityServiceCache);
            } catch (IOException e) {
                Log.e("warning", "IO exception");
            }
            // go to song display page
            displaySuccessfulMsg(songName, currentPageActivity);
            currentPageActivity = activityServiceCache.getCurrentPageActivity();
            Intent it = new Intent(currentPageActivity, SongDisplayPage.class);
            it.putExtra("cache", this.activityServiceCache);
            currentPageActivity.startActivity(it);
            currentPageActivity.finish();
        }
    }

    private void displaySuccessfulMsg(String songName, PageActivity currentPageActivity) {
        String Msg =  this.languagePresenter.translateString("You upload the song "
                + songName + " successfully!");
        Toast.makeText(currentPageActivity, Msg, Toast.LENGTH_LONG).show();
    }

    private boolean extractValidData(String songName, String minute, String second, String lyrics){
        if (songName.length() == 0 || minute.length() == 0
                || second.length() == 0 || lyrics.length() == 0){
            String Msg =  this.languagePresenter.translateString("You does not fill all the " +
                    "blanks");
            Toast.makeText(activityServiceCache.getCurrentPageActivity(), Msg, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
