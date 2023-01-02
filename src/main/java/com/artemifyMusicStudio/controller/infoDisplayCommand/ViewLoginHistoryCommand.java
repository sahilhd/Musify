package com.artemifyMusicStudio.controller.infoDisplayCommand;

import android.view.View;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.PageActivity;
import com.presenters.LanguagePresenter;
import com.useCase.UserAccess;

import java.util.ArrayList;

/**
 * A ViewLoginHistoryCommand class to handle the view login history request from a user
 *
 */
public class ViewLoginHistoryCommand extends RegularPopupCommand implements View.OnClickListener {

    private final ActivityServiceCache activityServiceCache;
    private final UserAccess acctServiceManager;
    private final LanguagePresenter languagePresenter;
    private final String userID;

    /** Constructor of ViewLoginHistoryCommand
     *
     * @param activityServiceCache a PageCreator Object
     * @param languagePresenter A LanguagePresenter object
     * @param userID the id of the user for whom the login history is being displayed
     */
    public ViewLoginHistoryCommand(ActivityServiceCache activityServiceCache,
                                   LanguagePresenter languagePresenter, String userID){
        this.activityServiceCache = activityServiceCache;
        this.acctServiceManager = this.activityServiceCache.getUserAcctServiceManager();
        this.languagePresenter = languagePresenter;
        this.userID = userID;
    }

    /**
     * Execute the ViewLoginHistoryCommand to query the user's previous login history
     */
    @Override
    public void onClick(View view) {
        this.popupTitle = this.languagePresenter.translateString("Hey " + userID + ", you were logged in previously at: ");
        ArrayList<String> logInHistory = this.acctServiceManager.getPreviousLogin(this.userID);
        StringBuilder tempBody = new StringBuilder();
        for (String history: logInHistory){
            tempBody.append(history).append("\n");
        }
        this.popupBody = String.valueOf(tempBody);
        PageActivity currActivity = this.activityServiceCache.getCurrentPageActivity();
        displayPopup(currActivity);
    }
}
