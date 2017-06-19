package com.example.max.eindopdrachtprog4app.domain;

import java.io.Serializable;

/**
 * Created by Max on 14-6-2017.
 */

public class Film implements Serializable {

    private String description;
    private boolean active;
    private String title;

    public Film(String title, String description,String active) {
        this.title = title;
        this.description= description;
        if(active == "1") {
            this.active = true;
        }else{
            this.active = false;
        }
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
