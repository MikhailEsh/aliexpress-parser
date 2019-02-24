package aliparser.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "errors")
@Data
@NoArgsConstructor
public class ErrorsEntity {

    public ErrorsEntity(String urlGroup, String urlProduct) {
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
    private Integer id;


}
