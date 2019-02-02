import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

    private static final String url = "https://www.aliexpress.com/premium/category/200003575.html?d=n&catName=led-lamps&CatId=200003575&spm=2114.10010108.100004.4.1da31b62oS4x3b&isViewCP=y";

    public static void main(String[] args) {
        try {
            Document doc = Jsoup.connect(url).get();
            Element links = doc.getElementById("list-items");
            for (Element link: links.children()) {

            }
            Integer as = 9;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void selenium() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ASUS\\IdeaProjects\\selenium\\src\\main\\resources\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        String baseUrl = "https://www.aliexpress.com/premium/category/200003575.html?d=n&catName=led-lamps&CatId=200003575&spm=2114.10010108.100004.4.1da31b62oS4x3b&isViewCP=y";
        driver.get(baseUrl);
        String text = driver.getPageSource();
        try {
            PrintWriter out = new PrintWriter("files.html");
            out.println(text);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Integer as = 0;
    }
}
