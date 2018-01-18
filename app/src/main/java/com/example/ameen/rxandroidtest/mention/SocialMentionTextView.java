package com.example.ameen.rxandroidtest.mention;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by ameen on 17/1/18.
 * Happy Coding
 */

public class SocialMentionTextView extends AppCompatTextView {

    public SocialMentionTextView(Context context) {
        super(context);
    }

    public SocialMentionTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SocialMentionTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }
}
