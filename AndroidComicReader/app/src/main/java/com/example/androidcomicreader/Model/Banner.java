package com.example.androidcomicreader.Model;

public class Banner {
    private  int ID;
    private String Link;

    public Banner(){
    }

    public String getLink() {
        return Link;
}

    public void setLink(String link) {
        Link = link;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public  Banner(int ID, String Link)
    {
      this.ID = ID;
      this.Link = Link;
    }
}
