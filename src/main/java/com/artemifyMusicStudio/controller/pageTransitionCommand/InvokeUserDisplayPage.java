package com.artemifyMusicStudio.controller.pageTransitionCommand;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.PageActivity;
import com.artemifyMusicStudio.UserDisplayPage;
import com.gateway.FileType;
import com.gateway.GatewayCreator;
import com.gateway.IGateway;

import java.io.IOException;

/**
 * A command to invoke UserDisplayPage
 */
public class InvokeUserDisplayPage implements View.OnClickListener{
    private final ActivityServiceCache activityServiceCache;
    private final String targetUserID;

    /**
     * Constructor of InvokeUserDisplayPage
     * @param activityServiceCache a ActivityServiceCache object
     */
    public InvokeUserDisplayPage(ActivityServiceCache activityServiceCache,
                                 String targetUserID){
        this.activityServiceCache = activityServiceCache;
        this.targetUserID = targetUserID;
    }

    /**
     * A method to execute the command when user click the button
     * @param view a View object
     */
    @Override
    public void onClick(View view) {
        this.activityServiceCache.setTargetUserID(targetUserID);
        PageActivity currentPageActivity = activityServiceCache.getCurrentPageActivity();
        GatewayCreator gatewayCreator = new GatewayCreator();
        IGateway ioGateway = gatewayCreator.createIGateway(FileType.SER,
                currentPageActivity);
        try {
            ioGateway.saveToFile("ActivityServiceCache.ser", this.activityServiceCache);
        } catch (IOException e) {
            Log.e("warning", "IO exception");
        }
        Intent it = new Intent(currentPageActivity, UserDisplayPage.class);
        it.putExtra("cache", this.activityServiceCache);
        currentPageActivity.startActivity(it);
    }
}
