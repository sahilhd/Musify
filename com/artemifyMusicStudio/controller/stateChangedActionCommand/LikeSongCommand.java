package com.artemifyMusicStudio.controller.stateChangedActionCommand;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.PageActivity;
import com.artemifyMusicStudio.R;
import com.gateway.FileType;
import com.gateway.GatewayCreator;
import com.gateway.IGateway;
import com.presenters.LanguagePresenter;

import java.io.IOException;

public class LikeSongCommand implements View.OnClickListener{

    private final ActivityServiceCache activityServiceCache;
    private final int songId;
    private final PageActivity songDisplayPage;
    private final LanguagePresenter languagePresenter;
    private final ImageButton imageButton;
    private final TextView likeView;
    private final int userFavouritesID;

    public LikeSongCommand(ActivityServiceCache activityServiceCache, int songId){
        this.activityServiceCache = activityServiceCache;
        this.songId = songId;
        songDisplayPage = activityServiceCache.getCurrentPageActivity();
        languagePresenter = activityServiceCache.getLanguagePresenter();
        String userId = activityServiceCache.getUserID();
        userFavouritesID = activityServiceCache.getUserAcctServiceManager().getUserFavouritesID(userId);

        imageButton = songDisplayPage.findViewById(R.id.display_like_song_button);
        likeView = songDisplayPage.findViewById(R.id.display_like_song);
    }
    @Override
    public void onClick(View v) {
        if (checkUnlike()){
            displayLikeMsg(songDisplayPage);
            imageButton.setBackgroundResource(R.drawable.like_button);
            activityServiceCache.getSongManager().likeSong(songId);
            activityServiceCache.getPlaylistManager().addToPlaylist(userFavouritesID, songId);
        } else{
            displayUnlikeMsg(songDisplayPage);
            imageButton.setBackgroundResource(R.drawable.empty_heart);
            activityServiceCache.getSongManager().unlikeSong(songId);
            activityServiceCache.getPlaylistManager().removeFromFavoritePlaylist(userFavouritesID, songId);
        }
        // Updated the ActivityServiceCache.ser file
        PageActivity currentPageActivity = activityServiceCache.getCurrentPageActivity();
        GatewayCreator gatewayCreator = new GatewayCreator();
        IGateway ioGateway = gatewayCreator.createIGateway(FileType.SER,
                currentPageActivity);
        try {
            ioGateway.saveToFile("ActivityServiceCache.ser", this.activityServiceCache);
        } catch (IOException e) {
            Log.e("warning", "IO exception");
        }

        // setLike
        setLike();
    }

    private void displayLikeMsg(PageActivity currentPageActivity) {
        String Msg =  languagePresenter.translateString("You like the song!");
        Toast.makeText(currentPageActivity, Msg, Toast.LENGTH_LONG).show();
    }

    private void displayUnlikeMsg(PageActivity currentPageActivity) {
        String Msg =  languagePresenter.translateString("You unlike the song");
        Toast.makeText(currentPageActivity, Msg, Toast.LENGTH_LONG).show();
    }

    private boolean checkUnlike(){
        return !activityServiceCache.getPlaylistManager().getListOfSongsID(userFavouritesID).contains(songId);
    }

    private void setLike(){
        String like = String.valueOf(activityServiceCache.getSongManager().getSongNumLikes(songId));
        likeView.setText(like);
    }
}
