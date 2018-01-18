package com.example.ameen.rxandroidtest.response;

import com.example.ameen.rxandroidtest.mention.MentionPerson;

import java.util.ArrayList;

/**
 * Created by ameen on 1/1/18.
 * Happy Coding
 */

public class UsersResponse  {

    String status;
    String msg;

    ArrayList<MentionPerson>  data;

    public ArrayList<MentionPerson> getData() {
        return data;
    }
}
