// heavily adapted program from: https://www.geeksforgeeks.org/how-to-create-language-translator-in-android-using-firebase-ml-kit/

/*
package com.presenters;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;


public class FirebaseAPITranslator {
    // creating variables for our image view,
    // text view and two buttons.
    private EditText edtLanguage;
    private TextView translateLanguageTV;
    private Button translateLanguageBtn;

    // create a variable for our
    // firebase language translator.
    FirebaseTranslator englishGermanTranslator;
    FirebaseTranslator englishAfrikanTranslator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        // on below line we are creating our firebase translate option.
        FirebaseTranslatorOptions options =
                new FirebaseTranslatorOptions.Builder()
                        // below line we are specifying our source language.
                        .setSourceLanguage(FirebaseTranslateLanguage.EN)
                        // in below line we are displaying our target language.
                        .setTargetLanguage(FirebaseTranslateLanguage.DE)
                        .setTargetLanguage(FirebaseTranslateLanguage.AF)
                        // after that we are building our options.
                        .build();
        // below line is to get instance
        // for firebase natural language.
        englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);
        englishAfrikanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options);


        // on below line we are initializing our variables.
        //edtLanguage = findViewById(com.artemifyMusicStudio.R.id.idEdtLanguage);
        //translateLanguageTV = findViewById(com.artemifyMusicStudio.R.id.idTVTranslatedLanguage);
        //translateLanguageBtn = findViewById(com.artemifyMusicStudio.R.id.idBtnTranslateLanguage);

        // adding on click listener for button
        translateLanguageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling method to download language
                // modal to which we have to translate.
                String string = edtLanguage.getText().toString();
                downloadModal(string);


            }
        });
    }

    private void downloadModal(String input) {
        // below line is use to download the modal which
        // we will require to translate in german language
        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder().requireWifi().build();

        // below line is use to download our modal.
        englishGermanTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                // this method is called when modal is downloaded successfully.
                Toast.makeText(MainActivity.this, "Please wait language modal is being downloaded.", Toast.LENGTH_SHORT).show();

                // calling method to translate our entered text.
                translateLanguage(input);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Fail to download modal", Toast.LENGTH_SHORT).show();
            }
        });

        englishAfrikanTranslator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // this method is called when modal is downloaded successfully.
                Toast.makeText(MainActivity.this, "Please wait language modal is being downloaded.", Toast.LENGTH_SHORT).show();

                // calling method to translate our entered text.
                translateLanguage(input);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Fail to download modal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private  void translateLanguage(String input) {
        englishGermanTranslator.translate(input).addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                //display the language
                translateLanguageTV.setText(s);
                System.out.println(s);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Fail to translate", Toast.LENGTH_SHORT).show();
            }
        });

        englishAfrikanTranslator.translate(input).addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                translateLanguageTV.setText(s);
                System.out.println(s);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Fail to translate", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
*/