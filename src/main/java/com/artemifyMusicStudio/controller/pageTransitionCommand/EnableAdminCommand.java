package com.artemifyMusicStudio.controller.pageTransitionCommand;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.AdminPage;
import com.artemifyMusicStudio.PageActivity;
import com.presenters.LanguagePresenter;
import com.useCase.UserAccess;

/**
 * The EnableAdminCommand object to allow user to enter to admin mode in RegularUserHomePage if he/she has admin right
 */
public class EnableAdminCommand implements View.OnClickListener {
    private final ActivityServiceCache activityServiceCache;
    private final LanguagePresenter languagePresenter;

    private final UserAccess acctServiceManager;
    private final String userID;

    /**
     * Constructor of the EnableAdminCommand class
     * @param activityServiceCache a PageCreator object
     *
     */
    public EnableAdminCommand(ActivityServiceCache activityServiceCache){
        super();
        this.activityServiceCache = activityServiceCache;
        this.languagePresenter = this.activityServiceCache.getLanguagePresenter();
        this.acctServiceManager = this.activityServiceCache.getUserAcctServiceManager();
        this.userID = this.activityServiceCache.getUserID();
    }

    @Override
    public void onClick(View v) {
        PageActivity currentPageActivity = activityServiceCache.getCurrentPageActivity();
        if (this.acctServiceManager.isAdmin(userID)){
            displaySuccessfulMsg(currentPageActivity);
            Intent it = new Intent(currentPageActivity, AdminPage.class);
            it.putExtra("cache", this.activityServiceCache);
            currentPageActivity.startActivity(it);
        }
        else{
            displayFailMsg(currentPageActivity);
        }
    }

    private void displaySuccessfulMsg(PageActivity currentPageActivity) {
        String Msg =  this.languagePresenter.translateString("You are now logged in as an admin.");
        Toast.makeText(currentPageActivity, Msg, Toast.LENGTH_LONG).show();
    }

    private void displayFailMsg(PageActivity currentPageActivity) {
        String Msg =  this.languagePresenter.translateString("Sorry, you don't have admin rights.");
        Toast.makeText(currentPageActivity, Msg, Toast.LENGTH_LONG).show();
    }
}
