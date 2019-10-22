package com.gusalnim.myfirstapp.demo.net;

/**
 * Created by gusalnim on 15/10/2019.
 */
public class DemoMenu {
    private String title;
    private String link;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "DemoMenu{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
