package model;

import java.io.Serializable;

public class Action implements Serializable {

    public String decisionTitle;  // Decision title
    public String option;         // Option that was affected

    public Action(String decisionTitle, String option) {
        this.decisionTitle = decisionTitle;
        this.option = option;
    }
}
