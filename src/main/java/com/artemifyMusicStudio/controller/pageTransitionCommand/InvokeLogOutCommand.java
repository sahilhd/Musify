package com.artemifyMusicStudio.controller.pageTransitionCommand;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.artemifyMusicStudio.ActivityServiceCache;
import com.artemifyMusicStudio.LoginPage;
import com.artemifyMusicStudio.PageActivity;
import com.gateway.FileType;
import com.gateway.GatewayCreator;
import com.gateway.IGateway;
import com.useCase.PlaylistEntityContainer;
import com.useCase.SongEntityContainer;
import com.useCase.UserEntityContainer;

import java.io.FileNotFoundException;
import java.io.IOException;

public class InvokeLogOutCommand implements View.OnClickListener{
    private final ActivityServiceCache activityServiceCache;

    public InvokeLogOutCommand(ActivityServiceCache activityServiceCache){
        this.activityServiceCache = activityServiceCache;
    }

    @Override
    public void onClick(View v) {
        // Retrieve the entity containers
        UserEntityContainer users = activityServiceCache.getUserAcctServiceManager().getUsers();
        PlaylistEntityContainer playlists = activityServiceCache.getPlaylistManager().getPlaylists();
        SongEntityContainer songs = activityServiceCache.getSongManager().getSongs();

        // Retrieve the gateway and write the updated entities to .ser files
        try{
            GatewayCreator gatewayCreator = new GatewayCreator();
            IGateway ioGateway = gatewayCreator.createIGateway(FileType.SER,
                    this.activityServiceCache.getCurrentPageActivity());
            ioGateway.saveToFile("Users.ser", users);
            ioGateway.saveToFile("Playlists.ser", playlists);
            ioGateway.saveToFile("Songs.ser", songs);
            ioGateway.saveToFile("ActivityServiceCache.ser", activityServiceCache);

            PageActivity currentPageActivity = activityServiceCache.getCurrentPageActivity();
            Intent it = new Intent(currentPageActivity, LoginPage.class);
            it.putExtra("cache", this.activityServiceCache);
            currentPageActivity.startActivity(it);
        }catch (FileNotFoundException e) {
            Log.e("warning", "cannot find file");
        } catch (IOException e){
            Log.e("warning", "IO exception");
        }

    }
}
