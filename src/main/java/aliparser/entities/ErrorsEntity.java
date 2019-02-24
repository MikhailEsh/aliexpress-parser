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

    public ErrorsEntity(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "url")
    private String url;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


}
