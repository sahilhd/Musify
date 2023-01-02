package com.artemifyMusicStudio.controller.userInputRequestCommand;

import android.widget.EditText;
import android.widget.Toast;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.PageActivity;
import com.presenters.LanguagePresenter;
import com.useCase.UserAccess;

import java.util.HashMap;

/**
 * A RegularLogInCommand to handle user's request for log in as a regular user
 */

public class RegularLogInCommand extends UserLogInCommand {

    /**
     * Constructor of RegularLogInCommand
     * @param activityServiceCache a PageCreator Object
     * @param languagePresenter a LanguagePresenter Object
     * @param acctServiceManager a UserAccess object
     */
    public RegularLogInCommand(ActivityServiceCache activityServiceCache, LanguagePresenter languagePresenter,
                               UserAccess acctServiceManager, EditText inputUserName, EditText inputPassword){
        super(activityServiceCache, languagePresenter, acctServiceManager, inputUserName, inputPassword);
        this.userAccountType = "Regular";
    }

    /**
     * Execute the RegularLogInCommand by conducting the following actions
     *
     * 1. Ask user to provide userID and password
     * 2. Conduct account authentication with the following three criteria
     *      i. If the account is banned, the execution terminates and the user will be directed back to MainPage
     *         automatically
     *
     *      ii. If the authentication fails because the user provides either the wrong username or password, ask
     *          the user to try again. The user will be directed back to LoginPage automatically
     *
     *      iii. If authentication is succeeded, print welcome message and invoke UserMainPage for the user
     *
     */


    @Override
    protected boolean getSecondLayerAuthenticationStatus(HashMap<String, Boolean> authenticationStatus) {
        return Boolean.TRUE.equals(authenticationStatus.get("IsBanned"));
    }

    @Override
    protected void displaySuccessfulLogInMsg(String userID, PageActivity currentPageActivity) {
        String welcomeMsg =  this.languagePresenter.translateString("Welcome " + userID + ", you are now logged in as a regular user.") ;
        Toast.makeText(currentPageActivity, welcomeMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void secondLayerAuthenticationFailAction(PageActivity currentPageActivity) {
        String warningMsg =  this.languagePresenter.translateString("This account is banned.") ;
        Toast.makeText(currentPageActivity, warningMsg, Toast.LENGTH_LONG).show();
    }

}
