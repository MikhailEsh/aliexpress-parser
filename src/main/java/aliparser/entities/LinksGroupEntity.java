package aliparser.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "links_group")
public class LinksGroupEntity {

    @Basic
    @Column(name = "url_group")
    private String urlGroup;

    @Id
    @Column(name = "id")
    private int id;

}
