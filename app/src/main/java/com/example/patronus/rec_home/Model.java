package com.example.patronus.rec_home;

public class Model {
    private  int imageview;
    private  String textview;
    public Model(int img,String txt){
        this.imageview=img;
        this.textview=txt;
    }

    public String getTextview() {
        return textview;
    }

    public void setTextview(String textview) {
        this.textview = textview;
    }

    public int getImageview() {
        return imageview;
    }

    public void setImageview(int imageview) {
        this.imageview = imageview;
    }
}
