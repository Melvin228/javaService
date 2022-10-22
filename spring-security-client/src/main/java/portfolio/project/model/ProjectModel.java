package portfolio.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import portfolio.project.entity.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectModel {
    private Long id;
    private Long userId;
    private String url;

}
