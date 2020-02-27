package com.example.sonoflordshiva.chatmessenger;

public class Users
{
    public String username;
    public String image;
    public String status;
    public String thumb_image;



    public Users(){

    }

    public Users(String username, String image, String status, String thumb_image) {
        this.username = username;
        this.image = image;
        this.status = status;
        this.thumb_image = thumb_image;
    }

    public String getName() {
        return username;
    }

    public void setName(String name) {
        this.username = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

}
