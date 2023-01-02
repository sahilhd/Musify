package com.artemifyMusicStudio.controller.userInputRequestCommand;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.AdminPage;
import com.artemifyMusicStudio.PageActivity;
import com.artemifyMusicStudio.RegularUserHomePage;
import com.gateway.FileType;
import com.gateway.GatewayCreator;
import com.gateway.IGateway;
import com.presenters.LanguagePresenter;
import com.presenters.LanguageType;
import com.presenters.PresenterCreator;
import com.useCase.UserAccess;

import java.io.IOException;
import java.util.HashMap;

public abstract class UserLogInCommand implements View.OnClickListener {

    protected final ActivityServiceCache activityServiceCache;
    protected final LanguagePresenter languagePresenter;
    protected final UserAccess acctServiceManager;
    protected String userAccountType = "";
    protected final EditText inputUserName;
    protected final EditText inputPassword;

    /**
     * Constructor of UserLogInCommand
     *
     * @param acctServiceManager a UserAccess object
     */
    public UserLogInCommand(ActivityServiceCache activityServiceCache, LanguagePresenter languagePresenter,
                            UserAccess acctServiceManager, EditText inputUserName, EditText inputPassword) {
        this.activityServiceCache = activityServiceCache;
        this.languagePresenter = languagePresenter;
        this.acctServiceManager = acctServiceManager;
        this.inputUserName = inputUserName;
        this.inputPassword = inputPassword;
    }

    /**
     * A template to execute the command of RegularLogInCommand, AdminLogInCommand, etc....
     */
    @Override
    public void onClick(View view) {
        String userID = inputUserName.getText().toString();
        String pwd = inputPassword.getText().toString();

        // User Authentication
        HashMap<String, Boolean> authenticationStatus = this.acctServiceManager.checkLogin(userID, pwd);
        boolean exists = Boolean.TRUE.equals(authenticationStatus.get("Exists"));
        boolean failForSecondLayerAuthentication = this.getSecondLayerAuthenticationStatus(authenticationStatus);

        if (exists){
            if (failForSecondLayerAuthentication){this.secondLayerAuthenticationFailAction(this.activityServiceCache.getCurrentPageActivity());}
            else{this.logInSuccessfulAction(userID);}
            // seems to stop over here
        }
        else{
            String warningMsg = this.languagePresenter.translateString("Incorrect username or password, please try again");
            Toast.makeText(this.activityServiceCache.getCurrentPageActivity(),
                    warningMsg, Toast.LENGTH_LONG).show();
        }
    }


    /**
     * A protected method to update the Language presenter and userID upon a successful login
     * @param userID a String to represent a
     */
    protected void updateServiceCache(String userID){
        String userPreferredDisplayLanguage = this.acctServiceManager.getUserDisplayLanguage(userID);
        PresenterCreator presenterCreator = new PresenterCreator();
        LanguagePresenter userLanguagePresenter = presenterCreator.createLanguagePresenter(
                LanguageType.valueOf(userPreferredDisplayLanguage.toUpperCase())
        );
        this.activityServiceCache.setLanguagePresenter(userLanguagePresenter);
        this.activityServiceCache.setUserID(userID);
    }

    /**
     * A protected method to handle the action if login is successful
     */
    protected void logInSuccessfulAction(String userID){
        PageActivity currentPageActivity = this.activityServiceCache.getCurrentPageActivity();
        this.displaySuccessfulLogInMsg(userID, currentPageActivity);
        this.updateServiceCache(userID);
        GatewayCreator gatewayCreator = new GatewayCreator();
        IGateway ioGateway = gatewayCreator.createIGateway(FileType.SER,
                currentPageActivity);
        try {
            ioGateway.saveToFile("ActivityServiceCache.ser", this.activityServiceCache);
        } catch (IOException e) {
            Log.e("warning", "IO exception");
        }
        switch (this.userAccountType){
            case "Regular":
                Intent itRegular = new Intent(currentPageActivity, RegularUserHomePage.class);
                itRegular.putExtra("cache", this.activityServiceCache);
                currentPageActivity.startActivity(itRegular);
                break;
            case "Admin":
                Intent itAdmin = new Intent(currentPageActivity, AdminPage.class);
                itAdmin.putExtra("cache", this.activityServiceCache);
                currentPageActivity.startActivity(itAdmin);
                break;
            default:
                Toast.makeText(currentPageActivity,
                        this.languagePresenter.translateString("Current account is not ready"),
                        Toast.LENGTH_LONG).show();
        }
    }

    /**
     * While the first layer of log in authentication is always check whether userID and password are match, this
     * abstract protected helper allows the subclass to select their second layer of log in authentication.
     *
     * @param authenticationStatus a HashMap that contains all authentication status for a user
     * @return a boolean to indicate whether the account has the correct rights to log in with current userAccountType
     */
    protected abstract boolean getSecondLayerAuthenticationStatus(HashMap<String,
            Boolean> authenticationStatus);

    /**
     * A protected method to force the subclass to define their own way of displaying welcome message if the user
     * successful log in
     *
     * @param userID  a userID
     * @param currentPageActivity a page activity
     */
    protected abstract void displaySuccessfulLogInMsg(String userID,
                                                      PageActivity currentPageActivity);

    /**
     * A protected method to handle the action if the user fails for second layer authentication when logging in
     * @param currentPageActivity a PageActivity object
     */
    protected abstract void secondLayerAuthenticationFailAction(PageActivity currentPageActivity);
}
