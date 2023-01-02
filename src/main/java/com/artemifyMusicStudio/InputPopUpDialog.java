package com.artemifyMusicStudio;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.artemifyMusicStudio.controller.CommandItemType;
import com.artemifyMusicStudio.controller.userInputRequestCommand.BanUserCommand;
import com.artemifyMusicStudio.controller.userInputRequestCommand.DeleteUserCommand;
import com.artemifyMusicStudio.controller.userInputRequestCommand.MakeAdminUserCommand;
import com.artemifyMusicStudio.controller.userInputRequestCommand.UnBanUserCommand;
import com.artemifyMusicStudio.controller.queueServiceCommand.RemoveFromQueueCommand;
import com.artemifyMusicStudio.controller.searchCommand.SearchPlaylistCommand;
import com.artemifyMusicStudio.controller.searchCommand.SearchSongCommand;
import com.artemifyMusicStudio.controller.searchCommand.SearchUserCommand;

/**
 * A InputPopDialog class
 */
public class InputPopUpDialog extends AppCompatDialogFragment {
    private final ActivityServiceCache activityServiceCache;
    private String popUpDescription = "";
    private String hintOfInputInfo = "";
    private final CommandItemType executionType;

    /**
     * A constructor of InputPopUpDialog
     * @param activityServiceCache a ActivityServiceCache object
     */
    public InputPopUpDialog(ActivityServiceCache activityServiceCache, CommandItemType executionType){
        this.activityServiceCache = activityServiceCache;
        this.executionType = executionType;
    }

    /**
     * A setter to set the popUpDescription
     * @param popUpDescription a pop description string
     */
    public void setPopUpDescription(String popUpDescription) {
        this.popUpDescription = popUpDescription;
    }

    /**
     * A setter to set the hintOfInputInfo
     * @param hintOfInputInfo a string for the hint
     */
    public void setHintOfInputInfo(String hintOfInputInfo) {
        this.hintOfInputInfo = hintOfInputInfo;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Attache the input prompt layout to builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.input_prompt_layout, null);

        // Set the title of the pop up and the hint of the required input
        builder.setTitle(this.popUpDescription);
        EditText inputInfo = view.findViewById(R.id.user_input_info);
        inputInfo.setHint(this.hintOfInputInfo);

        // Populate Submit and Cancel button
        Button submitButton = view.findViewById(R.id.submit_button);
        View.OnClickListener targetCommand = this.getTargetCommand(view);
        submitButton.setOnClickListener(targetCommand);

        Button cancelButton = view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(view1 -> {
            Dialog currDialog = getDialog();
            if (currDialog != null) {
                currDialog.dismiss();
            }
        });
        builder.setView(view);
        return  builder.create();
    }

    private View.OnClickListener getTargetCommand(View view){
        EditText userInputTargetName = view.findViewById(R.id.user_input_info);
        switch (this.executionType){
            case SEARCH_SONG:
                return new SearchSongCommand(this.activityServiceCache, userInputTargetName);
            case SEARCH_PLAYLIST:
                return new SearchPlaylistCommand(this.activityServiceCache, userInputTargetName);
            case SEARCH_USER:
                return new SearchUserCommand(this.activityServiceCache, userInputTargetName);
            case REMOVE_FROM_QUEUE:
                return new RemoveFromQueueCommand(this.activityServiceCache, userInputTargetName);
            case GRANT_ADMIN_RIGHT:
                return new MakeAdminUserCommand(this.activityServiceCache, userInputTargetName);
            case UNBAN_USER:
                return new UnBanUserCommand(this.activityServiceCache, userInputTargetName);
            case BAN_USER:
                return new BanUserCommand(this.activityServiceCache, userInputTargetName);
            case DELETE_USER:
                return new DeleteUserCommand(this.activityServiceCache, userInputTargetName);
            default:
                return null;
        }
    }

}
