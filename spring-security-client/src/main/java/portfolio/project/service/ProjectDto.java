package portfolio.project.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectDto {
    private Long id;
    private String firstName;
    private String url;
}