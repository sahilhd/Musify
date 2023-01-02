package com.artemifyMusicStudio.controller.stateChangedActionCommand;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.PageActivity;
import com.gateway.FileType;
import com.gateway.GatewayCreator;
import com.gateway.IGateway;
import com.presenters.LanguagePresenter;
import com.useCase.UserAccess;

import java.io.IOException;


/**
 * A FollowUserCommand class to handle the follow user request from a user
 *
 */
@SuppressLint("UseSwitchCompatOrMaterialCode")
public class FollowAndUnFollowUserCommand implements CompoundButton.OnCheckedChangeListener {

    private final ActivityServiceCache activityServiceCache;
    private final UserAccess acctServiceManager;
    private final LanguagePresenter languagePresenter;
    private final String userID;
    private final String targetUserID;
    private final Switch followUnfollowSwitch;
    private final TextView numOfFollowersDisplay;

    /** Constructor of FollowUserCommand
     *
     *
     */
    public FollowAndUnFollowUserCommand(ActivityServiceCache activityServiceCache,
                                        Switch followUnfollowSwitch,
                                        TextView numOfFollowersDisplay){
        this.activityServiceCache = activityServiceCache;
        this.acctServiceManager = activityServiceCache.getUserAcctServiceManager();
        this.languagePresenter = activityServiceCache.getLanguagePresenter();
        this.userID = activityServiceCache.getUserID();
        this.targetUserID = activityServiceCache.getTargetUserID();
        this.followUnfollowSwitch = followUnfollowSwitch;
        this.numOfFollowersDisplay = numOfFollowersDisplay;
    }


    /**
     * Execute the FollowAndUnfollow User command
     * @param compoundButton a compoundButton object
     * @param switchIsChecked a boolean to indicate whether the switch is checked or not
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean switchIsChecked) {
        PageActivity currentPageActivity = this.activityServiceCache.getCurrentPageActivity();
        if(switchIsChecked){
            this.acctServiceManager.addFollower(this.targetUserID, this.userID);
            this.followUnfollowSwitch.setText(this.languagePresenter.translateString("Follow"));
            String numFollowers = Integer.toString(activityServiceCache.getUserAcctServiceManager().getNumFollowers(targetUserID));
            this.numOfFollowersDisplay.setText(numFollowers);
            String msg = this.languagePresenter.translateString(
                    "Successfully followed " + this.targetUserID + "! (ꈍᴗꈍ)ε｀●)");
            Toast.makeText(currentPageActivity, msg, Toast.LENGTH_LONG).show();
        }
        else{
            this.acctServiceManager.removeFollower(this.targetUserID,this.userID);
            this.followUnfollowSwitch.setText(this.languagePresenter.translateString("UnFollow"));
            String numFollowers = Integer.toString(activityServiceCache.getUserAcctServiceManager().getNumFollowers(targetUserID));
            this.numOfFollowersDisplay.setText(numFollowers);
            String msg = "Successfully unfollowed " + this.targetUserID + ". (っ˘̩╭╮˘̩)っ";
            Toast.makeText(currentPageActivity, msg, Toast.LENGTH_LONG).show();
        }
        // Updated the ActivityServiceCache.ser file
        GatewayCreator gatewayCreator = new GatewayCreator();
        IGateway ioGateway = gatewayCreator.createIGateway(FileType.SER,
                currentPageActivity);
        try {
            ioGateway.saveToFile("ActivityServiceCache.ser", this.activityServiceCache);
        } catch (IOException e) {
            Log.e("warning", "IO exception");
        }

    }
}
