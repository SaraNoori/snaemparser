package edu.francistuttle;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

import java.util.List;
import java.util.ArrayList;


public class App 
{
    public static void main( String[] args ) throws Exception
    {
        List<RssItem> list = parseRssFeed("https://rss.nytimes.com/services/xml/rss/nyt/Technology.xml", 10);
        for (int i = 0; i < list.size(); i++)
        {
            System.out.println(list.get(i));
        }
    }

    /*
    public static void printStuff() throws Exception
    {
        Document document = linkHTTP("https://www.nasa.gov/news-release/feed/");
        

        Element root = document.getDocumentElement();
        System.out.println(root.getNodeName());

        NodeList items = document.getElementsByTagName("item");

        for (int index = 0; index < items.getLength(); index++)
        {
            Node node = items.item(index);

            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;
                System.out.println(element.getElementsByTagName("title").item(0).getTextContent());
                System.out.println("\t"+ element.getElementsByTagName("link").item(0).getTextContent());
                System.out.println("\t" + element.getElementsByTagName("pubDate").item(0).getTextContent());
            }
        }

    }
    */

    public static Document linkHTTP(String url)
    {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try (CloseableHttpResponse response1 = client.execute(httpGet)) {
            final HttpEntity entity = response1.getEntity();
            if (entity != null) {
                try (InputStream inputStream = entity.getContent()) {
			    // pass inputStream to builder.parse()
                return readFromInputStream(inputStream);
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Hosed: " + e.toString());
        }

        return null;

    }

    public static Document readFromInputStream(InputStream stream) throws Exception
    {
        // document builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // parse file
        Document document = builder.parse(stream);

        document.getDocumentElement().normalize();

        return document;
    }

    public static List<RssItem> parseRssFeed(String url, int maxItems)
    {
        Document document = linkHTTP(url);
        

        Element root = document.getDocumentElement();

        NodeList items = document.getElementsByTagName("item");

        List<RssItem> rssList = new ArrayList<RssItem>();

        for (int index = 0; (index < items.getLength()) && (index < maxItems); index++)
        {
            Node node = items.item(index);

            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;
                String title = element.getElementsByTagName("title").item(0).getTextContent();
                String link = element.getElementsByTagName("link").item(0).getTextContent();
                String pubDate = element.getElementsByTagName("pubDate").item(0).getTextContent();

                RssItem item = new RssItem(title, link, "", "", pubDate);
                rssList.add(item);
            }
        }
        return rssList;
    }

}
