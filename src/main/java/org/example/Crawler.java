package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class Crawler {
    HashSet<String> urlSet;
    int maxDepth =2;
    Crawler(){
       urlSet = new HashSet<>();
    }
    public void getPageTextsAndLinks(String url, int depth){
        if(urlSet.contains(url)){
            return;
        }
        if(depth >= maxDepth){
            return;
        }
        urlSet.add(url);
        System.out.println(url);

        depth++;
        try {
            Document document = Jsoup.connect(url).timeout(5000).get();
            // Indexer work start here
            Indexer indexer = new Indexer(document, url);

            System.out.println(document.title());
            Elements availableLinkOnPage = document.select("a[href]");
            for (Element currentLink : availableLinkOnPage) {
                getPageTextsAndLinks(currentLink.attr("abs:href"), depth);
            }
        }
        catch (IOException ioException){
            ioException.printStackTrace(); 
        }
    }
    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        crawler.getPageTextsAndLinks("https://www.javatpoint.com",0);
    }
}