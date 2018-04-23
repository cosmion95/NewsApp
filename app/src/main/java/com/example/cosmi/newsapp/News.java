package com.example.cosmi.newsapp;

import java.util.Date;

/**
 * Created by cosmi on 3/20/2018.
 */

public class News {

    private String section;
    private String publicationDate;
    private String title;
    private String url;

    public News(String sectionId, String publicationDate, String title, String url) {
        this.section = sectionId;
        this.publicationDate = publicationDate;
        this.title = title;
        this.url = url;
    }

    public String getSection() {
        return section;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
