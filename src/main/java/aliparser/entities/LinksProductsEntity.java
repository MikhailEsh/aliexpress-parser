package aliparser.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "links_products")
public class LinksProductsEntity {

    public LinksProductsEntity( String urlGroup, String urlProduct) {
        this.urlGroup = urlGroup;
        this.urlProduct = urlProduct;
    }


    @Basic
    @Column(name = "url_group")
    private String urlGroup;

    @Basic
    @Column(name = "url_product")
    private String urlProduct;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
