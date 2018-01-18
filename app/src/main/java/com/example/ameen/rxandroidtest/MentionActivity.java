package com.example.ameen.rxandroidtest;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ameen.rxandroidtest.mention.MentionPerson;
import com.example.ameen.rxandroidtest.mention.SocialMentionAutoComplete;

public class MentionActivity extends AppCompatActivity implements View.OnClickListener {

    protected SocialMentionAutoComplete multiAutoCompleteTextView;
    protected Button buttonProcess;

    private static final String TAG = "MentionActivity";


    ArrayMap<String, MentionPerson> mentionMap = new ArrayMap<>();

    MentionPerson mentionPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_mention);
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonProcess) {
            Log.d(TAG, "onClick: " + multiAutoCompleteTextView.getProcessedString());
        }
    }


    private void initView() {
        multiAutoCompleteTextView = findViewById(R.id.multiAutoCompleteTextView);
        buttonProcess = findViewById(R.id.buttonProcess);
        buttonProcess.setOnClickListener(MentionActivity.this);
    }
}
