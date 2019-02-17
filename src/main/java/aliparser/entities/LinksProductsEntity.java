package aliparser.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "links_products")
public class LinksProductsEntity {


    @Id
    @Column(name = "id")
    private String id;

    @Basic
    @Column(name = "url_product")
    private String urlProduct;

    @Basic
    @Column(name = "url_group")
    private String urlGroup;




}
