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

/**
 * A MakeAdminUserCommand class to grant another user the admin right
 *
 */

public class MakeAdminUserCommand implements View.OnClickListener {
    private final LanguagePresenter languagePresenter;
    protected final ActivityServiceCache activityServiceCache;
    protected final EditText InputTargetName;

    /**
     * Constructor of DeleteUserCommand class
     * @param activityServiceCache a PageCreator object
     * @param userInputTargetName a EditText to capture the user input for the username of a
     *                            to-be admin user.
     */
    public MakeAdminUserCommand(ActivityServiceCache activityServiceCache,
                             EditText userInputTargetName){
        this.activityServiceCache = activityServiceCache;
        this.languagePresenter = activityServiceCache.getLanguagePresenter();
        this.InputTargetName = userInputTargetName;
    }

    /**
     * Execute the request of granting a user the admin right
     */
    @Override
    public void onClick(View view) {
        String username = InputTargetName.getText().toString();
        if(this.activityServiceCache.getUserAcctServiceManager().makeAdmin(username)){
            PageActivity currentPageActivity = activityServiceCache.getCurrentPageActivity();
            GatewayCreator gatewayCreator = new GatewayCreator();
            IGateway ioGateway = gatewayCreator.createIGateway(FileType.SER,
                    currentPageActivity);
            try {
                ioGateway.saveToFile("ActivityServiceCache.ser", this.activityServiceCache);
            } catch (IOException e) {
                Log.e("warning", "IO exception");
            }
            String msg = this.languagePresenter.translateString("Admin rights granted");
            displayToastMsg(msg);
        // if there are no users with the input username
        }else{
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
