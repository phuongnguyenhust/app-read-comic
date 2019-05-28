package com.example.androidcomicreader.Model;

public class Comic {
    public int ID;
    public String Name;
    public String Image;

    public Comic(int ID, String Name, String Image) {
        this.ID = ID;
        this.Name = Name;
        this.Image = Image;
    }
    public Comic() {
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public String getName() {
        return Name;
    }

    public void setImage(String image) {
        Image = image;
    }
}