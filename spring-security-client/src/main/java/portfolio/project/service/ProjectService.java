package portfolio.project.service;

import portfolio.project.entity.Project;
import portfolio.project.model.ProjectModel;

import java.util.List;

public interface ProjectService {
    List<ProjectDto> getProjects();

    Project getProject(Long id);
    Project createProject(ProjectModel projectModel);

    Project updateProject(ProjectModel projectModel, Long id);

    void deleteProject(Long id);

}
