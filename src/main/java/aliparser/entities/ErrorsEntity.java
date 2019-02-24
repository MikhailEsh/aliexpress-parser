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

    public ErrorsEntity(Integer urlGroup, String urlProduct) {
        this.urlGroup = urlGroup;
        this.urlProduct = urlProduct;
    }

    @Basic
    @Column(name = "url_group")
    private Integer urlGroup;

    @Basic
    @Column(name = "url_product")
    private String urlProduct;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


}
