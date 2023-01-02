package com.artemifyMusicStudio;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artemifyMusicStudio.controller.CommandItemType;
import com.artemifyMusicStudio.controller.SimpleButtonCommandCreator;
import com.artemifyMusicStudio.controller.commandCreator.PageTransitionCommandCreator;
import com.artemifyMusicStudio.controller.pageTransitionCommand.ExitPageCommand;
import com.presenters.LanguagePresenter;
import com.useCase.UserAccess;

import java.util.ArrayList;

/**
 * A page to displaying follower and followings for a user
 */
public class FollowerFollowingDisplayPage extends PageActivity {
    private String displayType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_following_display_page);

        // parse service cache, searchResult and searchType
        parseActivityServiceCache();
        this.activityServiceCache.setCurrentPageActivity(this);
        this.displayType = (String) getIntent().getSerializableExtra("displayType");

        // populate head msg
        populateHeadMsg();

        // populate buttons
        populateButtons();

    }

    private void populateHeadMsg(){
        LanguagePresenter languagePresenter = this.activityServiceCache.getLanguagePresenter();
        TextView tv = findViewById(R.id.page_head_msg);
        tv.setText(languagePresenter.translateString(this.displayType));
    }

    private ArrayList<String> getDisplayNames(UserAccess acctServiceManager){
        switch (this.displayType){
            case "Follower":
                return acctServiceManager.getFollowers(this.activityServiceCache.getTargetUserID());
            case "Following":
                return acctServiceManager.getFollowing(this.activityServiceCache.getTargetUserID());
            default:
                return new ArrayList<>();
        }
    }

    @Override
    protected void populateButtons() {
        LanguagePresenter languagePresenter = this.activityServiceCache.getLanguagePresenter();
        LinearLayout resultDisplay = findViewById(R.id.follower_following_result_display_layout);
        resetViewState(resultDisplay);
        ArrayList<String> displayTargetNames = getDisplayNames(this.activityServiceCache.getUserAcctServiceManager());
        int count = 0;
        PageTransitionCommandCreator pageTransitionCommandCreator = new PageTransitionCommandCreator(this.activityServiceCache);
        for (String targetName : displayTargetNames){
            String buttonDescription = languagePresenter.translateString(targetName);
            Button button = new Button(this);
            button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            button.setId(count);
            button.setText(buttonDescription);
            pageTransitionCommandCreator.setTargetID(targetName);
            View.OnClickListener onClickListener = pageTransitionCommandCreator.create(CommandItemType.INVOKE_USER_DISPLAY);
            button.setOnClickListener(onClickListener);

            // populate the button to the layout
            resultDisplay.addView(button);
            count += 1;
        }
        resultDisplay.setGravity(Gravity.CENTER);
        Button exitButton = findViewById(R.id.exit);
        exitButton.setOnClickListener(new ExitPageCommand(this.activityServiceCache));
    }

    private void resetViewState(LinearLayout layout){
        layout.removeAllViews();
    }

    @Override
    protected SimpleButtonCommandCreator getSimpleOnClickCommandCreator(String creatorType) {
        return null;
    }

    @Override
    protected void populateIdMenuMap() {}

    @Override
    protected void populateMenuCommandCreatorMap() {}

}