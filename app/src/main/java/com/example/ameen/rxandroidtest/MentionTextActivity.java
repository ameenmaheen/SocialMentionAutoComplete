package com.example.ameen.rxandroidtest;

import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ameen.rxandroidtest.mention.MentionPerson;
import com.example.ameen.rxandroidtest.mention.SocialMentionTextView;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MentionTextActivity extends AppCompatActivity implements View.OnClickListener {

    protected EditText editText;
    protected Button button;
    protected SocialMentionTextView socialMentionTextView;

    ArrayMap<String, MentionPerson> map = new ArrayMap<>();

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
            process(editText.getText().toString());
        }
    }

    private void process(String text) {

        map.clear();

        Pattern p = Pattern.compile("\\[([^]]+)]\\(([^ )]+)\\)");
        Matcher m = p.matcher(text);

        String finalDesc = text;

        while (m.find()) {
            MentionPerson mentionPerson = new MentionPerson();
            String name = m.group(1);
            String id = m.group(2);
            finalDesc = finalDesc.replace("@[" + name + "](" + id + ")", "@" + name);

            mentionPerson.name = name;
            mentionPerson.id = id;
            map.put("@" + name, mentionPerson);
        }

        Spannable spannable = new SpannableString(finalDesc);
        for (Map.Entry<String, MentionPerson> stringMentionPersonEntry : map.entrySet()) {
            int startIndex = finalDesc.indexOf(stringMentionPersonEntry.getKey());
            int endIndex = startIndex + stringMentionPersonEntry.getKey().length();
            int textColor = ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null);
            spannable.setSpan(new ForegroundColorSpan(textColor), startIndex, endIndex , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        socialMentionTextView.setText(spannable);
        getProcessedString();
    }

    public String getProcessedString() {

        String s = socialMentionTextView.getText().toString();

        for (Map.Entry<String, MentionPerson> stringMentionPersonEntry : map.entrySet()) {
            s = s.replace(stringMentionPersonEntry.getKey(), stringMentionPersonEntry.getValue().getFormattedValue());
        }
        Log.d(TAG, "getProcessedString: "+s);
        return s;
    }

    private void initView() {
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(MentionTextActivity.this);
        socialMentionTextView = (SocialMentionTextView) findViewById(R.id.socialMentionTextView);
    }
}
