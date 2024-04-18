package edu.francistuttle;

public class RssItem {
    public String title;
    public String link;
    public String imageLink;
    public String description;
    public String pubDate;

    public RssItem(String title, String link, String imageLink, 
                    String description, String pubDate)
    {
        this.title = title;
        this.link = link;
        this.imageLink = imageLink;
        this.description = description;
        this.pubDate = pubDate;
    }

    public String toString()
    {
        return "Title: " + title + 
            " \n\t| Link: " + link + 
            " \n\t| ImageLink: " + imageLink + 
            " \n\t| Description: " + description + 
            " \n\t| PubDate: " + pubDate;
    }
}
