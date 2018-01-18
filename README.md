# SocialMentionAutoComplete
Android EditText for searching and mentioning user from a source 

This Project contains `SocialMentionAutoComplete` for searching and highlighting a string in edit text from a data source.

# Usage

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

# Demo 
-------
This is the example used in the Libary

![old_ui](https://github.com/ameenmaheen/SocialMentionAutoComplete/blob/master/art/old_ui.gif)

You can improve the view by Creating a improved custom `ArrayAdapter`

![new_ui](https://github.com/ameenmaheen/SocialMentionAutoComplete/blob/master/art/updated_ui.gif)
