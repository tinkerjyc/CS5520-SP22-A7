package edu.neu.madcourse.numad22sp_yunxiangli;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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


    // called upon clicking Send sticker button (This function is kinda redundant),
    // the real one in processed inside the MainPageActivity.
    public void onClickSendStickerButton(View view)
    {
        Toast.makeText(UserRecordContainerActivity.this, "Send sticker button clicked "
                , Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, StickersDisplayActivity.class));
    }
}