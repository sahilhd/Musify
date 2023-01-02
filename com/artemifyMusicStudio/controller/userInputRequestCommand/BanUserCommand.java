package com.artemifyMusicStudio.controller.userInputRequestCommand;
import com.artemifyMusicStudio.ActivityServiceCache;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.artemifyMusicStudio.PageActivity;
import com.gateway.FileType;
import com.gateway.GatewayCreator;
import com.gateway.IGateway;
import com.presenters.LanguagePresenter;

import java.io.IOException;

/**
 * A BanUserCommand class to handle the ban user request from an admin user
 *
 */

public class BanUserCommand implements View.OnClickListener {
    private final LanguagePresenter languagePresenter;
    protected final ActivityServiceCache activityServiceCache;
    protected final EditText InputTargetName;


    /**
     * Constructor of BanUserCommand
     * @param activityServiceCache a PageCreator object
     * @param userInputTargetName a EditText to capture the user input for the username of a
     *                            to-be banned user.
     */
    public BanUserCommand(ActivityServiceCache activityServiceCache,
                          EditText userInputTargetName){
        this.activityServiceCache = activityServiceCache;
        this.languagePresenter = activityServiceCache.getLanguagePresenter();
        this.InputTargetName = userInputTargetName;
    }

    /**
     * Execute the ban user command
     */
    @Override
    public void onClick(View view) {
        String username = InputTargetName.getText().toString();
        // check if anyone with this username exists
        if (this.activityServiceCache.getUserAcctServiceManager().exists(username)) {
            // check if they're an admin
            if (this.activityServiceCache.
                    getUserAcctServiceManager().findUser(username).getIsAdmin()) {
                String msg = this.languagePresenter.
                        translateString("Invalid action. You cannot ban admins.");
                displayToastMsg(msg);
            } else if (this.activityServiceCache.getUserAcctServiceManager().ban(username)) {
                PageActivity currentPageActivity = activityServiceCache.getCurrentPageActivity();
                GatewayCreator gatewayCreator = new GatewayCreator();
                IGateway ioGateway = gatewayCreator.createIGateway(FileType.SER,
                        currentPageActivity);
                try {
                    ioGateway.saveToFile("ActivityServiceCache.ser", this.activityServiceCache);
                } catch (IOException e) {
                    Log.e("warning", "IO exception");
                }
                String msg = this.languagePresenter. translateString("Successfully banned");
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
