# SocialMentionAutoComplete
Android EditText for searching and mentioning user from a source 

This Project contains `SocialMentionAutoComplete` for searching and highlighting a string in edit text from a data source.

#Usage

```xml
 <SocialMentionAutoComplete
        android:id="@+id/multiAutoCompleteTextView"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        android:layout_marginEnd="8dp"
        android:inputType="textShortMessage"
        android:layout_marginStart="8dp"
        android:layout_marginTop="8dp"/>
```

Get the processed text from the view by using

```java
  MentionAutoCompleteAdapter  multiAutoCompleteTextView = findViewById(R.id.multiAutoCompleteTextView);
  Log.d("mention string", " This is output " + multiAutoCompleteTextView.getProcessedString());
```
There are still a lot of fetures need to be done to this library

In this example the data source for the names are retrived using a server call By using retrofit library .
You can customize the adapter view according your needs and how format the resulting string from the textview .

```java
public class MentionAutoCompleteAdapter extends ArrayAdapter<MentionPerson> {

    public MentionAutoCompleteAdapter(@NonNull Context context,  @NonNull List<MentionPerson> objects) {
        super(context, 0, objects);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Nullable
    @Override
    public MentionPerson getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(@Nullable MentionPerson item) {
        return super.getPosition(item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        ViewHolder viewHolder;
        if (view == null || !(view.getTag() instanceof ViewHolder)) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_mention_people, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textView.setText(getItem(position).getName());

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        ViewHolder viewHolder;
        if (view == null || !(view.getTag() instanceof ViewHolder)) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_mention_people, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textView.setText(getItem(position).getName());

        return view;
    }

    static class ViewHolder {

        TextView textView;

        ViewHolder(View rootView) {
            initView(rootView);
        }

        private void initView(View rootView) {
            textView = rootView.findViewById(R.id.textView);
        }
    }
}
```
Model for the Mention data

```java
public class MentionPerson {

    @SerializedName("value")
    public String name;
    @SerializedName("uid")
    public String id;
    @SerializedName("image")
    public String imageURL;

    public String getFormattedValue() {
        return "@[" + name + "](" + id + ")";
    }

    public MentionPerson() {
    }

    public MentionPerson(String name, String id, String imageURL) {

        this.name = name;
        this.id = id;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getImageURL() {
        return imageURL;
    }

    @Override
    public String toString() {
        return name;
    }
}

```

code for AutoComplete text View 

```java
public class SocialMentionAutoComplete extends AppCompatMultiAutoCompleteTextView {

    MentionAutoCompleteAdapter mentionAutoCompleteAdapter;
    ArrayMap<String, MentionPerson> map = new ArrayMap<>();

    String formattedOfString = "@%s ";

    public SocialMentionAutoComplete(Context context) {
        super(context);
        initializeComponents();
    }

    public SocialMentionAutoComplete(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeComponents();
    }

    public SocialMentionAutoComplete(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeComponents();
    }

    private void initializeComponents() {
        addTextChangedListener(textWatcher);
        setOnItemClickListener(onItemSelectedListener);
        setTokenizer(new SpaceTokenizer());
    }

    AdapterView.OnItemClickListener onItemSelectedListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            MentionPerson mentionPerson = (MentionPerson) adapterView.getItemAtPosition(i);
            map.put("@" + mentionPerson.name, mentionPerson);
        }
    };


    /***
     *This function returns the contents of the AppCompatMultiAutoCompleteTextView into my desired Format
     *You can write your own function according to your needs
     **/

    public String getProcessedString() {

        String s = getText().toString();

        for (Map.Entry<String, MentionPerson> stringMentionPersonEntry : map.entrySet()) {
            s = s.replace(stringMentionPersonEntry.getKey(), stringMentionPersonEntry.getValue().getFormattedValue());
        }
        return s;
    }

    /**
    *This function will process the incoming text into mention format
    * You have to implement the processing logic
    * */
    public void setMentioningText(String text) {

        map.clear();

        Pattern p = Pattern.compile("\\[([^]]+)]\\(([^ )]+)\\)");
        Matcher m = p.matcher(text);

        String finalDesc = text;

        while (m.find()) {
            MentionPerson mentionPerson = new MentionPerson();
            String name = m.group(1);
            String id = m.group(2);
            //Processing Logic
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
        setText(spannable);
    }


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int lengthBefore, int lengthAfter) {

            if (!s.toString().isEmpty() && start < s.length()) {

                String name = s.toString().substring(0, start + 1);

                int lastTokenIndex = name.lastIndexOf(" @");
                int lastIndexOfSpace = name.lastIndexOf(" ");
                int nextIndexOfSpace = name.indexOf(" ", start);

                if (lastIndexOfSpace > 0 && lastTokenIndex < lastIndexOfSpace) {
                    String afterString = s.toString().substring(lastIndexOfSpace, s.length());
                    if (afterString.startsWith(" ")) return;
                }

                if (lastTokenIndex < 0) {
                    if (!name.isEmpty() && name.length() >= 1 && name.startsWith("@")) {
                        lastTokenIndex = 1;
                    } else
                        return;
                }

                int tokenEnd = lastIndexOfSpace;

                if (lastIndexOfSpace <= lastTokenIndex) {
                    tokenEnd = name.length();
                    if (nextIndexOfSpace != -1 && nextIndexOfSpace < tokenEnd) {
                        tokenEnd = nextIndexOfSpace;
                    }
                }

                if (lastTokenIndex >= 0) {
                    name = s.toString().substring(lastTokenIndex, tokenEnd).trim();
                    Pattern pattern = Pattern.compile("^(.+)\\s.+");
                    Matcher matcher = pattern.matcher(name);
                    if (!matcher.find()) {
                        name = name.replace("@", "").trim();
                        if (!name.isEmpty()) {
                            getUsers(name);
                        }
                    }
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    /*
    *This function returns results from the web server according to the user name
    * I have used Retrofit for Api Communications
    * */
    public void getUsers(String name) {

        Call<UsersResponse> call = Api.getInstance().getApiMethods().getUsers("79146d29997abef00f8daa4414997af9", name);
        call.enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                if (response.isSuccessful()) {
                    UsersResponse usersResponse = response.body();
                    if (usersResponse != null) {
                        List<MentionPerson> mentionPeople = usersResponse.getData();
                        if (mentionPeople != null && !mentionPeople.isEmpty()) {
                            mentionAutoCompleteAdapter =
                                    new MentionAutoCompleteAdapter(getContext(), mentionPeople);
                            SocialMentionAutoComplete.this.setAdapter(mentionAutoCompleteAdapter);
                            SocialMentionAutoComplete.this.showDropDown();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    public class SpaceTokenizer implements MultiAutoCompleteTextView.Tokenizer {

        public int findTokenStart(CharSequence text, int cursor) {

            int i = cursor;

            while (i > 0 && text.charAt(i - 1) != ' ') {
                i--;
            }
            while (i < cursor && text.charAt(i) == ' ') {
                i++;
            }
            return i;
        }

        public int findTokenEnd(CharSequence text, int cursor) {

            int i = cursor;
            int len = text.length();

            while (i < len) {
                if (text.charAt(i) == ' ') {
                    return i;
                } else {
                    i++;
                }
            }

            return len;
        }

        public CharSequence terminateToken(CharSequence text) {

            int i = text.length();

            while (i > 0 && text.charAt(i - 1) == ' ') {
                i--;
            }

            if (i > 0 && text.charAt(i - 1) == ' ') {
                return text;
            } else {
                // Returns colored text for selected token
                SpannableString sp = new SpannableString(String.format(formattedOfString, text));
                int textColor = ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null);
                sp.setSpan(new ForegroundColorSpan(textColor), 0, text.length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                return sp;
            }
        }
    }
}
```
