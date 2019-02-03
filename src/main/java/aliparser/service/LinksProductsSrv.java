package aliparser.service;


import aliparser.dao.LinksGroupRepo;
import aliparser.dao.LinksProductsRepo;
import aliparser.entities.LinksGroupEntity;
import aliparser.entities.LinksProductsEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LinksProductsSrv {

    private final String prefix = "https:";

    private final Integer maxPage = 100;

    @Autowired
    private LinksGroupRepo linksGroupRepo;

    @Autowired
    private LinksProductsRepo linksProductsRepo;


    public void parsesGroups() {
        List<LinksGroupEntity> groups = linksGroupRepo.findAll();
        for(LinksGroupEntity group: groups) {
            String url = group.getUrlGroup();
            String[] parts = url.split("1.html");
            for (int i = 0; i < maxPage; i++) {
                String pageURL = parts[0] + i + ".html";
                priceOnePage(pageURL);
            }

        }
    }

    private void priceOnePage(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            List<String> products = parseGroup(doc);
            List<LinksProductsEntity> productsEntities = products
                    .stream()
                    .map(pr -> new LinksProductsEntity(url, pr))
                    .collect(Collectors.toList());
            linksProductsRepo.saveAll(productsEntities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> parseGroup(Document doc) {
        Element listItems = doc.getElementById("list-items");
        Elements tags = listItems.select(".product");
        List<String> links = tags.stream().map(tg -> {
            String href = tg.attr("href");
            return prefix + href;
        }).collect(Collectors.toList());
        return links;
    }
}
