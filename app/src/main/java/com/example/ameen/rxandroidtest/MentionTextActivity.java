package com.example.ameen.rxandroidtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ameen.rxandroidtest.mention.SocialMentionTextView;

public class MentionTextActivity extends AppCompatActivity implements View.OnClickListener,SocialMentionTextView.OnMentionClickListener {

    protected EditText editText;
    protected Button button;
    protected SocialMentionTextView socialMentionTextView;


    private static final String TAG = "MentionTextActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_mention_text);
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            socialMentionTextView.setMentionText(editText.getText().toString());
        }
    }



    private void initView() {
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        button.setOnClickListener(MentionTextActivity.this);
        socialMentionTextView = findViewById(R.id.socialMentionTextView);
        socialMentionTextView.setOnMentionClickListener(this);
    }

    @Override
    public void onMentionClick(String personId) {
        Log.d(TAG, "onMentionClick: "+personId);
    }
}
