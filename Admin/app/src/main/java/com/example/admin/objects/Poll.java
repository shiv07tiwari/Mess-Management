package com.example.admin.objects;

import java.util.ArrayList;

public class Poll {

    private String pollID;

    public String getPollID() {
        return pollID;
    }

    public void setPollID(String pollID) {
        this.pollID = pollID;
    }

    public Poll(String pollID, String pollquestion, String options) {
        this.pollID = pollID;
        this.pollquestion = pollquestion;
        this.options = options;
    }

    private String pollquestion;
    private String options;



    public String getPollquestion() {
        return pollquestion;
    }

    public void setPollquestion(String pollquestion) {
        this.pollquestion = pollquestion;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
}
