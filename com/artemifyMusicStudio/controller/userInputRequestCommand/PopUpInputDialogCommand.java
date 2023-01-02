package com.artemifyMusicStudio.controller.userInputRequestCommand;

import android.view.View;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.InputPopUpDialog;
import com.artemifyMusicStudio.PageActivity;
import com.artemifyMusicStudio.controller.CommandItemType;

/**
 * A command to pop up the dialog for search
 */

public class PopUpInputDialogCommand implements View.OnClickListener{
    private final ActivityServiceCache activityServiceCache;
    private final String popUpDescription;
    private final String hintOfInputInfo;
    private final InputPopUpDialog inputPopUpDialog;

    /**
     * Constructor of the SearchPopUpCommand
     * @param activityServiceCache a ActivityServiceCache object
     * @param popUpDescription a String to describe the search pop up dialog
     * @param hintOfInputInfo a String to describe what a user should input in the dialog
     * @param targetCommandType a Command Type to represent the target command when user
     *                          hit submit in the pop up dialog
     */
    public PopUpInputDialogCommand(ActivityServiceCache activityServiceCache,
                                   String popUpDescription, String hintOfInputInfo,
                                   CommandItemType targetCommandType){
        this.activityServiceCache = activityServiceCache;
        this.popUpDescription = popUpDescription;
        this.hintOfInputInfo = hintOfInputInfo;
        this.inputPopUpDialog = new InputPopUpDialog(this.activityServiceCache, targetCommandType);
    }

    @Override
    public void onClick(View view) {
        this.inputPopUpDialog.setPopUpDescription(this.popUpDescription);
        this.inputPopUpDialog.setHintOfInputInfo(this.hintOfInputInfo);
        PageActivity currActivity = this.activityServiceCache.getCurrentPageActivity();
        this.inputPopUpDialog.show(currActivity.getSupportFragmentManager(), "Pop Up Dialog");
    }
}
