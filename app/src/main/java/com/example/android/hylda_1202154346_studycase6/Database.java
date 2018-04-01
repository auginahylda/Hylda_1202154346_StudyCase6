package com.example.android.hylda_1202154346_studycase6;

import com.google.firebase.firestore.IgnoreExtraProperties;

/**
 * Created by hp on 01/04/2018.
 */

public class Database {
    String commenters, comments, commentedImage;

    //method kosong untuk membaca data
    public Database {
    }

    //konstruktor
    public Database(String commenters, String comments, String commentedImage) {
        this.commenters = commenters;
        this.comments = comments;
        this.commentedImage = commentedImage;
    }

    //method getter
    public String getCommenters() {
        return commenters;
    }

    public String getComments() {
        return comments;
    }

    public String getCommentedImage() {
        return commentedImage;
    }
}
