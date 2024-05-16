package project.datacollecting;

import project.datacollecting.scraper.CointelegraphScraper;
import project.datacollecting.scraper.IScraper;
import project.datacollecting.scraper.MediumScraper;
import project.datacollecting.scraper.TwitterScraper;
import project.datacollecting.scraper.WiredScraper;
import project.datacollecting.seleniumhelper.ConfigManager;

public class Scraping {

        public static void main(String[] args) {

            ConfigManager configManager = new ConfigManager();
            
            IScraper mediumScraper = new MediumScraper("https://medium.com/tag/blockchain/recommended", "data\\output_medium.json", 2, configManager);
            mediumScraper.scrape();
        
            IScraper cointelegraphScraper = new CointelegraphScraper("https://cointelegraph.com/tags/blockchain", "data\\output_cointelegraph.json", 2);
            cointelegraphScraper.scrape();

            IScraper twitterScraper = new TwitterScraper("https://twitter.com/search?q=blockchain&src=recent_search_click", "data\\output_twitter.json", 1);
            twitterScraper.scrape();

            IScraper wiredScraperNew = new WiredScraper("https://www.wired.com/search/?q=blockchain&sort=publishdate+desc", "data\\output_wired.json", 2);
            wiredScraperNew.scrape();

            IScraper wiredScraperHot = new WiredScraper("https://www.wired.com/search/?q=blockchain&sort=score+desc", "data\\output_wired.json", 2);        
            wiredScraperHot.scrape();

        }
}
