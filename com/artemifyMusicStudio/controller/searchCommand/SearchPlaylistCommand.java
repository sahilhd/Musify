package com.artemifyMusicStudio.controller.searchCommand;

import android.widget.EditText;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.useCase.PlaylistManager;

import java.util.ArrayList;

/**
 * A command class to handle the request of searching a playlist
 */
public class SearchPlaylistCommand extends SearchServiceCommand {
    private final PlaylistManager playlistServiceManager;

    /**
     * Constructor of SearchPlaylistCommand
     * @param activityServiceCache a ActivityServiceCache object
     *
     */
    public SearchPlaylistCommand(ActivityServiceCache activityServiceCache,
                                 EditText inputTargetName){
        super(activityServiceCache, inputTargetName);
        this.playlistServiceManager = this.activityServiceCache.getPlaylistManager();
        this.searchType = "Playlist";
    }

    /**
     * Execute the searching of playlist by invoking method in playlist manager
     */

    @Override
    protected ArrayList<String> getTargetEntityIDs(String searchString) {
        return this.playlistServiceManager.getListOfStringPlaylistIDs(searchString);
    }

    @Override
    protected String getSearchResultDescription(int index, String searchName, String targetEntityID) {
        int playlistID = Integer.parseInt(targetEntityID);
        return "  " + index + ". " + searchName + ", created by "
                + this.playlistServiceManager.getCreatorUsername(playlistID);
    }
}
