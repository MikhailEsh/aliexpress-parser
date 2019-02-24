package aliparser.service;


import aliparser.dao.ErrorsRepo;
import aliparser.dao.LinksGroupRepo;
import aliparser.dao.LinksProductsRepo;
import aliparser.entities.LinksGroupEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Log4j2
public class LinksProductsSrv {

    static public AtomicInteger page = new AtomicInteger(0);

    static public final Integer maxPage = 100;

    private final Integer countThread = 10;

    @Autowired
    LinksProductsRepo linksProductsRepo;

    @Autowired
    private LinksGroupRepo linksGroupRepo;

    @Autowired
    private ErrorsRepo errorsRepo;


    public void parsesGroups() {
        List<LinksGroupEntity> groups = linksGroupRepo.findAll();
        ExecutorService es = Executors.newFixedThreadPool(countThread);
        LinksGroupEntity group = groups.get(0);
        for (int i = 0; i < countThread; i++) {
            FeedBackSrv feedBackSrv = new FeedBackSrv(group, linksProductsRepo, errorsRepo);
            es.execute(feedBackSrv);
        }
    }

    public void parsesGroup(Integer id) {
        Optional<LinksGroupEntity> group = linksGroupRepo.findById(id);
        if (group.isPresent()) {
            ExecutorService es = Executors.newFixedThreadPool(countThread);
            for (int i = 0; i < countThread; i++) {
                FeedBackSrv feedBackSrv = new FeedBackSrv(group.get(), linksProductsRepo, errorsRepo);
                es.execute(feedBackSrv);
            }
        }
    }
}
