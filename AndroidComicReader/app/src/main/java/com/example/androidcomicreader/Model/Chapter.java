package com.example.androidcomicreader.Model;

public class Chapter {
public int ID;
public String Name;
public int MangaID;

public Chapter(){

}
    public Chapter(int ID, String name){
        this.ID = ID;
        Name = name;
       // MangaID = mangaID;

    }

public Chapter(int ID, String name, int mangaID){
    this.ID = ID;
    Name = name;
    MangaID = mangaID;

}

    public void setName(String name) {
        Name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public int getMangaID() {
        return MangaID;
    }

    public void setMangaID(int mangaID) {
        MangaID = mangaID;
    }
}
