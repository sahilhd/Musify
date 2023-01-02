package com.artemifyMusicStudio.controller.userInputRequestCommand;

import android.widget.EditText;
import android.widget.Toast;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.PageActivity;
import com.presenters.LanguagePresenter;
import com.useCase.UserAccess;

import java.util.HashMap;

/**
 * A AdminLogInCommand object to handle the Admin log in request
 */
public class AdminLogInCommand extends UserLogInCommand {

    /**
     * Constructor of AdminLogInCommand
     *
     * @param activityServiceCache a ActivityServiceCache object
     * @param languagePresenter a LanguagePresenter object
     * @param acctServiceManager UserAccess UseCase object
     */
    public AdminLogInCommand(ActivityServiceCache activityServiceCache, LanguagePresenter languagePresenter,
                             UserAccess acctServiceManager, EditText inputUserName, EditText inputPassword){
        super(activityServiceCache, languagePresenter, acctServiceManager, inputUserName, inputPassword);
        this.userAccountType = "Admin";
    }

    /**
     * Execute AdminLogInCommand by conducting the following actions
     *
     * 1. Ask user to provide userID and password
     * 2. Conduct account authentication with the following three criteria
     *      i. If the authentication fails because the user provides either the wrong username or password, ask the
     *         user to try again to login. The user will be directed back to LogInPage automatically
     *
     *      ii. If user does not have admin right, print a String message to tell user that he/she does not have an
     *         Admin right. The user will be directed back to LogInPage automatically
     *
     *      iii. If user has admin right, print welcome message and invoke AdminPage
     *
     *
     */

    @Override
    protected boolean getSecondLayerAuthenticationStatus(HashMap<String, Boolean> authenticationStatus) {
        return Boolean.FALSE.equals(authenticationStatus.get("IsAdmin"));
    }

    @Override
    protected void displaySuccessfulLogInMsg(String userID, PageActivity currentPageActivity) {
        String welcomeMsg =  this.languagePresenter.translateString("Welcome " + userID + ", You are now logged in as an Admin");
        Toast.makeText(currentPageActivity, welcomeMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void secondLayerAuthenticationFailAction(PageActivity currentPageActivity) {
        String warningMsg = this.languagePresenter.translateString("Sorry, you don't have admin rights \uD83D\uDE21");
        Toast.makeText(currentPageActivity, warningMsg, Toast.LENGTH_LONG).show();
    }


}
