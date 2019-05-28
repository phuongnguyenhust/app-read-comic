package com.example.androidcomicreader.Model;

public class Link {
    private int ID;
    private String Link;
    private int ChapterID;

    public Link(int ID , String link) {
        this.ID = ID;
        this.Link = link;

    }

    public Link(int ID, String link, int chapterID) {
        this.ID = ID;
        Link = link;
        ChapterID =chapterID;

    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getLink() {
        return Link;
    }

    public int getChapterID() {
        return ChapterID;
    }

    public void setChapterID(int chapterID) {
        ChapterID = chapterID;
    }
}

