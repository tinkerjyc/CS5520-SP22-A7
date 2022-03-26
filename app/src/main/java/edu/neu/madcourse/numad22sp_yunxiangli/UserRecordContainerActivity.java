package edu.neu.madcourse.numad22sp_yunxiangli;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This class represents the activity of the user record container(each row).
 */
public class UserRecordContainerActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_record_container);
    }


    // called upon clicking Send sticker button
    public void onClickSendStickerButton(View view)
    {
        System.out.println("Send sticker button clicked");
        startActivity(new Intent(this, StickersDisplayActivity.class));
    }
}