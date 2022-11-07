package com.ca_dreamers.cadreamers.SavedVideo;

public class ModelSavedVideo {

    private String strAbsolutePath;
    private String strNameOfFile;


    // creating constructor for our variables.
    public ModelSavedVideo(String strAbsolutePath, String strNameOfFile) {
        this.strAbsolutePath = strAbsolutePath;
        this.strNameOfFile = strNameOfFile;
    }

    // creating getter and setter methods.
    public String getStrAbsolutePath() {
        return strAbsolutePath;
    }

    public void setStrAbsolutePath(String strAbsolutePath) {
        this.strAbsolutePath = strAbsolutePath;
    }

    public String getStrNameOfFile() {
        return strNameOfFile;
    }

    public void setStrNameOfFile(String strNameOfFile) {
        this.strNameOfFile = strNameOfFile;
    }
}
