package com.artemifyMusicStudio.controller.queueServiceCommand;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.PageActivity;
import com.artemifyMusicStudio.QueueDisplayPage;
import com.gateway.FileType;
import com.gateway.GatewayCreator;
import com.gateway.IGateway;
import com.presenters.LanguagePresenter;

import java.io.IOException;

public class RemoveFromQueueCommand implements View.OnClickListener {

    private final ActivityServiceCache activityServiceCache;
    private final LanguagePresenter languagePresenter;
    private final EditText inputSongIndex;

    /**
     * Constructor for PlayPreviousSongCommand class
     * @param activityServiceCache instance of ActivityServiceCache class to parse important data
     *                             to conduct page activities
     * @param inputSongIndex       instance of EditText, the input from the user on our app
     */
    public RemoveFromQueueCommand(ActivityServiceCache activityServiceCache, EditText inputSongIndex) {
        this.activityServiceCache = activityServiceCache;
        this.languagePresenter = activityServiceCache.getLanguagePresenter();
        this.inputSongIndex = inputSongIndex;
    }

    @Override
    public void onClick(View v) {
        PageActivity currentPageActivity = activityServiceCache.getCurrentPageActivity();
        String text = inputSongIndex.getText().toString();
        try {
            int songIndex = Integer.parseInt(text);

            int songID = activityServiceCache.getQueueManager().getUpcomingSongs().get(songIndex);
            String songName = activityServiceCache.getSongManager().getSongName(songID);
            activityServiceCache.getQueueManager().removeFromQueue(songIndex);

            // Updated ActivityServiceCache.ser file
            GatewayCreator gatewayCreator = new GatewayCreator();
            IGateway ioGateway = gatewayCreator.createIGateway(FileType.SER,
                    currentPageActivity);
            try {
                ioGateway.saveToFile("ActivityServiceCache.ser", this.activityServiceCache);
            } catch (IOException e) {
                Log.e("warning", "IO exception");
            }

            Intent it = new Intent(currentPageActivity, QueueDisplayPage.class);
            it.putExtra("cache", this.activityServiceCache);
            currentPageActivity.startActivity(it);
            String warningMsg =  this.languagePresenter.
                    translateString(songName + " has been removed from the queue.") ;
            Toast.makeText(currentPageActivity, warningMsg, Toast.LENGTH_LONG).show();

        } catch (NumberFormatException e) {
            String warningMsg =  this.languagePresenter.
                    translateString("Input is not an integer, please enter a valid input.") ;
            Toast.makeText(currentPageActivity, warningMsg, Toast.LENGTH_LONG).show();
        } catch (IndexOutOfBoundsException e) {
            String warningMsg =  this.languagePresenter.
                    translateString("There is no song at your given index. ") ;
            Toast.makeText(currentPageActivity, warningMsg, Toast.LENGTH_LONG).show();
        }

    }
}
