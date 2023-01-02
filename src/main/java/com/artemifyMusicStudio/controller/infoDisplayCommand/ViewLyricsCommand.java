package com.artemifyMusicStudio.controller.infoDisplayCommand;

import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.artemifyMusicStudio.ActivityServiceCache;

/**
 * A ViewLyricsCommand to view the time created and the lyrics of the song
 */
public class ViewLyricsCommand implements View.OnClickListener {

    private final ActivityServiceCache activityServiceCache;
    private final int songId;

    public ViewLyricsCommand(ActivityServiceCache activityServiceCache){
        this.activityServiceCache = activityServiceCache;
        this.songId = Integer.parseInt(activityServiceCache.getTargetSongID());
    }

    @Override
    public void onClick(View v) {
        String msg = "♪ " + activityServiceCache.getSongManager().getSongLyrics(songId) + " ♪";
        AlertDialog.Builder builder = new AlertDialog.Builder(activityServiceCache.getCurrentPageActivity());
        builder.setTitle("Lyrics")
                .setMessage(msg)
                .create()
                .show();
    }
}
