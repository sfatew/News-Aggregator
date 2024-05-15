package project.datacollecting;

import project.datacollecting.scraper.CointelegraphScraper;
import project.datacollecting.scraper.MediumScraper;
import project.datacollecting.scraper.TwitterScraper;
import project.datacollecting.scraper.WiredScraper;

public class Scraping {

        public static void main(String[] args) {
            MediumScraper mediumScraper = new MediumScraper("https://medium.com/tag/blockchain/recommended", "C:\\Users\\MY LAPTOP\\OneDrive\\Documents\\GitHub\\News-Aggregator\\data\\output_medium.json", 10);
            mediumScraper.scrapeArticlesList();
        
            CointelegraphScraper cointelegraphScraper = new CointelegraphScraper("https://cointelegraph.com/tags/blockchain", "C:\\Users\\MY LAPTOP\\OneDrive\\Documents\\GitHub\\News-Aggregator\\data\\output_cointelegraph.json", 20);
            cointelegraphScraper.scrapeArticlesList();

            TwitterScraper twitterScraper = new TwitterScraper("https://twitter.com/search?q=blockchain&src=recent_search_click", "C:\\Users\\MY LAPTOP\\OneDrive\\Documents\\GitHub\\News-Aggregator\\data\\output_twitter.json", 10);
            twitterScraper.scrapeArticlesList();

            WiredScraper wiredScraperNew = new WiredScraper("https://www.wired.com/search/?q=blockchain&sort=publishdate+desc", "C:\\Users\\MY LAPTOP\\OneDrive\\Documents\\GitHub\\News-Aggregator\\data\\output_wired.json", 10);
            wiredScraperNew.scrapeArticlesList();

            WiredScraper wiredScraperHot = new WiredScraper("https://www.wired.com/search/?q=blockchain&sort=score+desc", "C:\\Users\\MY LAPTOP\\OneDrive\\Documents\\GitHub\\News-Aggregator\\data\\output_wired.json", 10);        
            wiredScraperHot.scrapeArticlesList();

        }
}
