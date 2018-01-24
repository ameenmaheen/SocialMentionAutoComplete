package com.example.ameen.rxandroidtest.mention;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;

import com.example.ameen.rxandroidtest.R;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ameen on 17/1/18.
 * Happy Coding
 */

public class SocialMentionTextView extends AppCompatTextView {

    String originalString;
    OnMentionClickListener onMentionClickListener;

    public SocialMentionTextView(Context context) {
        super(context);
        init();
    }

    public SocialMentionTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SocialMentionTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        int textColor = ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null);
        setLinkTextColor(textColor);
        setLinksClickable(true);
        setMovementMethod(LinkMovementMethod.getInstance());
        setFocusable(false);
    }

    public void setOnMentionClickListener(OnMentionClickListener onMentionClickListener) {
        this.onMentionClickListener = onMentionClickListener;
    }

    public void setMentionText(String text) {

        originalString = text;

        ArrayMap<String, MentionPerson> map = new ArrayMap<>();

        Pattern p = Pattern.compile("\\[([^]]+)]\\(([^ )]+)\\)");
        Matcher m = p.matcher(text);
        String finalDesc = text;

        while (m.find()) {

            String name = m.group(1);
            String id = m.group(2);
            /*
             * My way of formatting the input i get to the out i need conversion
             * my input : @[Sajesh Cc](user:665c23720db84014ae3f83c67aca8046)
             * my out : @Sajesh Cc
             * */

            finalDesc = finalDesc.replace("@[" + name + "](" + id + ")", "@" + name);

            MentionPerson mentionPerson = new MentionPerson();
            mentionPerson.name = name;
            mentionPerson.id = id;
            map.put("@" + name, mentionPerson);
        }

        Spannable spannable = new SpannableString(finalDesc);
        for (Map.Entry<String, MentionPerson> stringMentionPersonEntry : map.entrySet()) {
            int startIndex = finalDesc.indexOf(stringMentionPersonEntry.getKey());
            int endIndex = startIndex + stringMentionPersonEntry.getKey().length();
            InternalURLSpan internalURLSpan   = new InternalURLSpan();
            internalURLSpan.text = stringMentionPersonEntry.getKey();
            spannable.setSpan(internalURLSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        setText(spannable);
    }


    class InternalURLSpan extends ClickableSpan {
        public String text;

        @Override
        public void onClick(View view) {
            handleLinkClicked(text);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }

    public interface OnMentionClickListener {

        void onMentionClick(String personId);
    }

    public void handleLinkClicked(String value) {

        if (onMentionClickListener != null)
            if (value.startsWith("@")) {
                String result = processUser(value);
                if (result != null)
                    onMentionClickListener.onMentionClick(result);
            }
    }

    private String processUser(String value) {

        String result = null;
        Pattern p = Pattern.compile("\\[([^]]+)]\\(([^ )]+)\\)");
        Matcher m = p.matcher(originalString);
        while (m.find()) {
            if (("@" + m.group(1)).contains(value)) {
                result = m.group(2).substring(m.group(2).indexOf(":") + 1);
                break;
            }
        }
        return result;
    }
}
