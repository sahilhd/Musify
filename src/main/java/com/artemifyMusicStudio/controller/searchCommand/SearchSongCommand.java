package com.artemifyMusicStudio.controller.searchCommand;

import android.widget.EditText;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.useCase.SongManager;

import java.util.ArrayList;

/**
 * A command to handle the request of searching a Song
 */
public class SearchSongCommand extends SearchServiceCommand {
    private final SongManager songManager;

    /**
     * Constructor of SearchSongCommand
     * @param activityServiceCache a ActivityServiceCache object
     *
     */
    public SearchSongCommand(ActivityServiceCache activityServiceCache,
                             EditText InputTargetName){
        super(activityServiceCache, InputTargetName);
        this.searchType = "Song";
        this.songManager = this.activityServiceCache.getSongManager();
    }

    @Override
    protected ArrayList<String> getTargetEntityIDs(String searchString) {
        return this.songManager.getStringSongsIDsFromSongName(searchString);
    }

    @Override
    protected String getSearchResultDescription(int index, String searchName, String targetEntityID) {
        int songID = Integer.parseInt(targetEntityID);
        return "  " + index + ". " + searchName + ", created by "
                + this.songManager.getSongArtist(songID);
    }

}
