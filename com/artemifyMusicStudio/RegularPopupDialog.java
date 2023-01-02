package com.artemifyMusicStudio;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

/**
 * A regular popup that has a simple title and body text
 */
public class RegularPopupDialog extends AppCompatDialogFragment {

    private final String popupTitle;
    private final String popupBody;

    public RegularPopupDialog(String popupTitle, String popupBody){
        this.popupTitle = popupTitle;
        this.popupBody = popupBody;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.regular_popup_layout, null);

        builder.setTitle(popupTitle);
        builder.setMessage(popupBody);
        Button okButton = view.findViewById(R.id.ok_button);
        okButton.setOnClickListener(view1 -> {
            Dialog currDialog = getDialog();
            if (currDialog != null) {
                currDialog.dismiss();
            }
        });

        builder.setView(view);
        return builder.create();
    }
}
