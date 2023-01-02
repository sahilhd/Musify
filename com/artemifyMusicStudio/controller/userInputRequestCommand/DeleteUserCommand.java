package com.artemifyMusicStudio.controller.userInputRequestCommand;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.PageActivity;
import com.gateway.FileType;
import com.gateway.GatewayCreator;
import com.gateway.IGateway;
import com.presenters.LanguagePresenter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A DeleteUserCommand class to handle delete user request from an admin user
 *
 */
public class DeleteUserCommand implements View.OnClickListener {
    private final LanguagePresenter languagePresenter;
    protected final ActivityServiceCache activityServiceCache;
    protected final EditText InputTargetName;

    /**
     * Constructor of DeleteUserCommand class
     * @param activityServiceCache a PageCreator object
     * @param userInputTargetName a EditText to capture the user input for the username of a
     *                            to-be deleted user.
     */
    public DeleteUserCommand(ActivityServiceCache activityServiceCache,
                             EditText userInputTargetName){
        this.activityServiceCache = activityServiceCache;
        this.languagePresenter = activityServiceCache.getLanguagePresenter();
        this.InputTargetName = userInputTargetName;
    }

    /**
     * Execute the delete user request from a admin user
     */
    @Override
    public void onClick(View view) {
        String username = InputTargetName.getText().toString();
        if (this.activityServiceCache.getUserAcctServiceManager().exists(username)) {
            if (this.activityServiceCache.getUserAcctServiceManager().findUser(username).getIsAdmin()) {
                String msg = this.languagePresenter.
                        translateString("Invalid action. You cannot delete admins.");
                displayToastMsg(msg);
            }
            else{
                // get a list of all this user's playlists' IDs
                ArrayList<String> userOwnPlaylistsIds =
                        this.activityServiceCache.getUserAcctServiceManager().
                                getListOfAllPlaylistIDsFromUser(username);
                // get a list of all this user's songs' IDs
                ArrayList<String> userCreatedSongsIds = this.activityServiceCache.getSongManager().
                        getStringSongIDsFromCreator(username);
                // deleting all of this user's playlists
                this.activityServiceCache.getPlaylistManager().
                        deletePlaylistsByIDs(userOwnPlaylistsIds);
                // deleting all of this user's songs
                this.activityServiceCache.getSongManager().deleteSongsByIDs(userCreatedSongsIds);
                // finally, deleting the user
                this.activityServiceCache.getUserAcctServiceManager().delete(username);
                PageActivity currentPageActivity = activityServiceCache.getCurrentPageActivity();
                GatewayCreator gatewayCreator = new GatewayCreator();
                IGateway ioGateway = gatewayCreator.createIGateway(FileType.SER,
                        currentPageActivity);
                try {
                    ioGateway.saveToFile("ActivityServiceCache.ser", this.activityServiceCache);
                } catch (IOException e) {
                    Log.e("warning", "IO exception");
                }
                String msg = this.languagePresenter.translateString("Successfully deleted");
                displayToastMsg(msg);
            }
        }
        // if there are no users with the input username
        else{
            String msg = this.languagePresenter.translateString("User does not exist");
            displayToastMsg(msg);
        }
    }

    // method to display pop-up message
    public void displayToastMsg(String msg) {
        PageActivity currentPageActivity = activityServiceCache.getCurrentPageActivity();
        Toast.makeText(currentPageActivity, msg, Toast.LENGTH_LONG).show();
    }
}
