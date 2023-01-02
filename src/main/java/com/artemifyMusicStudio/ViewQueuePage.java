package com.artemifyMusicStudio;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artemifyMusicStudio.controller.CommandItemType;
import com.artemifyMusicStudio.controller.SimpleButtonCommandCreator;
import com.artemifyMusicStudio.controller.commandCreator.PageTransitionCommandCreator;
import com.useCase.Queue;
import com.useCase.SongManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A ViewQueuePage Activity
 */
public class ViewQueuePage extends PageActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_queue_page);

        // parse and updated service cache
        parseActivityServiceCache();
        this.activityServiceCache.setCurrentPageActivity(this);

        // populate button
        populateMenuCommandCreatorMap();
        populateIdMenuMap();
        populateButtons();

        TextView tv = findViewById(R.id.playlist_name_display);
        String queueName = "Queue";
        tv.setText(queueName);

        showListSongs();

    }

    @Override
    protected SimpleButtonCommandCreator getSimpleOnClickCommandCreator(String creatorType) {
        if ("TransitionCommandCreator".equals(creatorType)) {
            return new PageTransitionCommandCreator(this.activityServiceCache);
        }
        return null;
    }

    @Override
    protected void populateIdMenuMap() {
        idMenuItemMap.put(CommandItemType.EXIT_PAGE, R.id.exit);
    }

    @Override
    protected void populateMenuCommandCreatorMap() {
        ArrayList<CommandItemType> tempList = new ArrayList<>(
                List.of(CommandItemType.EXIT_PAGE)
        );
        menuCommandCreatorMap.put("TransitionCommandCreator", tempList);
    }

    private void showListSongs() {

        LinearLayout songLists = findViewById(R.id.list_songs_display);
        // remove all existing songs in the lists to prevent redundancy
        songLists.removeAllViews();

        SongManager songManager = activityServiceCache.getSongManager();
        Queue queueManager = activityServiceCache.getQueueManager();
        ArrayList<Integer> allSongIDs = queueManager.getUpcomingSongs();
        int count = 0;
        for (Integer songID: allSongIDs) {
            String num = String.valueOf(count);
            String songName = songManager.getSongName(songID);
            String artistName = songManager.getSongArtist(songID);
            String displayName = num + ". " + songName;
            @SuppressLint("InflateParams") View oneSong = LayoutInflater.from(this).
                    inflate(R.layout.one_song_display, null);
            // set song name for this song
            TextView songNameDisplay = oneSong.findViewById(R.id.display_song_name);
            songNameDisplay.setText(displayName);
            // set artist name for this song
            TextView artistNameDisplay = oneSong.findViewById(R.id.display_artist_name);
            artistNameDisplay.setText(artistName);
            // put this song in ViewQueuePage
            songLists.addView(oneSong);
            count = count + 1;
        }
    }
}
