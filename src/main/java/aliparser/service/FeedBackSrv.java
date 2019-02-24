package aliparser.service;


import aliparser.dao.ErrorsRepo;
import aliparser.dao.LinksProductsRepo;
import aliparser.entities.ErrorsEntity;
import aliparser.entities.LinksProductsEntity;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class FeedBackSrv implements Runnable {


    public static final String prefix = "https:";

    private LinksProductsRepo linksProductsRepo;

    private String urlGroup;

    private WebDriver driver;

    private ErrorsRepo errorsRepo;

    public FeedBackSrv(String urlGroup, LinksProductsRepo linksProductsRepo, ErrorsRepo errorsRepo) {
        this.urlGroup = urlGroup;
        this.linksProductsRepo = linksProductsRepo;
        this.errorsRepo = errorsRepo;
        System.setProperty("webdriver.chrome.driver", "/home/luckshery/IdeaProjects/aliexpress-parser/src/main/resources/chromedriver");
        this.driver =  new ChromeDriver();
    }

    @Override
    public void run() {
        boolean isRun = true;
        for (;isRun;) {
            int page = LinksProductsSrv.page.getAndIncrement();
            if (page > LinksProductsSrv.maxPage) {
                isRun = false;
            } else {
                try {
                    List<String> products = parseGroup(page);
                    for (String product: products) {
                        String id = extractId(product);
                        try {
                            countFeedBack(this.urlGroup, product, id);
                        } catch (Exception e) {
                            ErrorsEntity errorsEntity = new ErrorsEntity(product);
                            errorsRepo.save(errorsEntity);
                            log.error("Error url product" + product);
                            log.error(e);
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        this.driver.quit();
    }

//    public LinksProductsEntity countFeedBack(String id) {
//        Optional<LinksProductsEntity> entityOpt = linksProductsRepo.findById(id);
//        if (entityOpt.isPresent()) {
//            LinksProductsEntity entity = entityOpt.get();
//            return countFeedBack(entity.getUrlGroup(), entity.getUrlProduct(), id);
//        } else return null;
//    }

    public void countFeedBack(String urlGroup, String urlProduct, String id) {
        driver.get(urlProduct);
        LinksProductsEntity entity = parseFeedback(driver, urlGroup, urlProduct, id);
        log.info(id + " success computed ");
        linksProductsRepo.save(entity);
    }

    private LinksProductsEntity parseFeedback(WebDriver driver, String urlGroup, String urlProduct, String id) {
        WebElement element = driver.findElement(By.xpath("//*[@id=\"feedback\"]/iframe"));
        String urlFeedback = prefix + element.getAttribute("thesrc");
        driver.get(urlFeedback);
        int countFeedBack = countPages(driver);
        driver.findElement(By.xpath("//*[@id=\"cb-onlyFromMyCountry-filter\"]")).click();
        int countRUFeedBack = countPages(driver);;
        int ration = (int)((float)countRUFeedBack/ (float)countFeedBack * 100);
        LinksProductsEntity entity = new LinksProductsEntity(id, urlGroup, urlProduct, urlFeedback, countFeedBack, countRUFeedBack, ration);
        log.info(entity);
        return entity;
    }

    private Integer countPages(WebDriver driver) {
        WebElement count = driver.findElement(By.xpath("//*[@id=\"simple-pager\"]/div/label"));
        String text = count.getText();
        String denominator = text.split("/")[1];
        return Integer.valueOf(denominator);
    }

    private String extractId(String product) {
        String prefix = product.split(".html")[0];
        Integer inx = prefix.lastIndexOf("/");
        return prefix.substring(inx + 1);
    }

    private List<String> parseGroup(int page) throws IOException {
        String[] parts = urlGroup.split("1.html");
        String pageProducts = parts[0] + page + ".html";
        Document doc = Jsoup.connect(pageProducts).get();
        Element listItems = doc.getElementById("list-items");
        Elements tags = listItems.select(".product");
        List<String> links = tags.stream().map(tg -> {
            String href = tg.attr("href");
            return prefix + href;
        }).collect(Collectors.toList());
        return links;
    }

}
