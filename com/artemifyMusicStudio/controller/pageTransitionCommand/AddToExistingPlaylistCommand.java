package com.artemifyMusicStudio.controller.pageTransitionCommand;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.PageActivity;
import com.gateway.FileType;
import com.gateway.GatewayCreator;
import com.gateway.IGateway;
import com.presenters.LanguagePresenter;
import com.useCase.PlaylistManager;
import com.useCase.SongManager;

import java.io.IOException;


/**
 * An AddToExistingPlaylistCommand class to add the currently viewed song to an existing playlist by user input
 */
public class AddToExistingPlaylistCommand implements View.OnClickListener {
    private final ActivityServiceCache activityServiceCache;
    private final LanguagePresenter languagePresenter;
    private final PlaylistManager playlistServiceManager;
    private final SongManager songManager;
    private final int songID;
    private final int playlistID;

    /**
     * Constructor of AddToExistingCommand
     *
     * @param activityServiceCache a PageCreator object
     *
     */
    public AddToExistingPlaylistCommand(ActivityServiceCache activityServiceCache,
                                        int songID, int playlistID){
        this.activityServiceCache = activityServiceCache;
        playlistServiceManager = activityServiceCache.getPlaylistManager();
        this.languagePresenter = activityServiceCache.getLanguagePresenter();
        this.songManager = activityServiceCache.getSongManager();
        this.songID = songID;
        this.playlistID = playlistID;
    }

    /**
     * Execute the AddToExistingPlaylistCommand
     */
    @Override
    public void onClick(View view) {
        boolean songIsPublic = songManager.isPublic(songID);
        // check if it is possible to add the song to the selected playlist
        boolean succeed = playlistServiceManager.addableToPlaylist(playlistID, songIsPublic);
        if (!succeed) {
            String msg = "You cannot add a private song to a public playlist";
            Toast.makeText(this.activityServiceCache.getCurrentPageActivity(),
                    this.languagePresenter.translateString(msg), Toast.LENGTH_LONG).show();}
        else {
            if (playlistServiceManager.getListOfSongsID(playlistID).contains(songID)) {
                String msg = "Already added! (⌒‿⌒) You can find this song in \uD83D\uDCBD " +
                        songManager.getSongName(songID) + " \uD83D\uDCBD.";
                Toast.makeText(this.activityServiceCache.getCurrentPageActivity(),
                        this.languagePresenter.translateString(msg), Toast.LENGTH_LONG).show();
            }else {
                playlistServiceManager.addToPlaylist(playlistID, songID);
                // Updated ActivityServiceCache.ser file
                PageActivity currentPageActivity = this.activityServiceCache.getCurrentPageActivity();
                GatewayCreator gatewayCreator = new GatewayCreator();
                IGateway ioGateway = gatewayCreator.createIGateway(FileType.SER,
                        currentPageActivity);
                try {
                    ioGateway.saveToFile("ActivityServiceCache.ser", this.activityServiceCache);
                } catch (IOException e) {
                    Log.e("warning", "IO exception");
                }
                String msg = "Successfully added! (⌒‿⌒) You can now find this song in \uD83D\uDCBD " +
                        songManager.getSongName(songID) + " \uD83D\uDCBD.";
                Toast.makeText(this.activityServiceCache.getCurrentPageActivity(),
                        this.languagePresenter.translateString(msg), Toast.LENGTH_LONG).show();
            }
        }
    }


}
