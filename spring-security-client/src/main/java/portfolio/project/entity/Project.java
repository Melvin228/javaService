package portfolio.project.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name="url")
    private String Url;

    @ManyToOne
    @JoinColumn(
            name="user_id",
            referencedColumnName ="Id"
    )
    private User userId;

}
