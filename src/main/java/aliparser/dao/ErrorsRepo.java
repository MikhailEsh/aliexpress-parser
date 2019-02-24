package aliparser.dao;


import aliparser.entities.ErrorsEntity;
import aliparser.entities.LinksGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorsRepo extends JpaRepository<ErrorsEntity,Integer> {
}
