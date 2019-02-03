package aliparser.dao;


import aliparser.entities.LinksProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinksProductsRepo extends JpaRepository<LinksProductsEntity,String> {
}
