package com.artemifyMusicStudio.controller.infoDisplayCommand;

import com.artemifyMusicStudio.PageActivity;
import com.artemifyMusicStudio.RegularPopupDialog;

/**
 * An abstract class for commands that create a simple popup with a title and body text.
 */
public abstract class RegularPopupCommand {
    protected String popupTitle;
    protected String popupBody;

    /**
     * Method that creates the required popup
     * @param currActivity a PageActivity object
     */
    public void displayPopup(PageActivity currActivity){
        RegularPopupDialog regularPopupDialog = new RegularPopupDialog(popupTitle, popupBody);
        regularPopupDialog.show(currActivity.getSupportFragmentManager(), "Pop Up Dialog");
    }
}