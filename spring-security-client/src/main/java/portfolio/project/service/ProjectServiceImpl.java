package portfolio.project.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import portfolio.project.entity.Project;
import portfolio.project.entity.User;
import portfolio.project.model.ProjectModel;
import portfolio.project.repository.ProjectRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserService userService;



    public ProjectServiceImpl(ProjectRepository projectRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;

    }


    public List<ProjectDto> getProjects() {
          List<Project> projects = projectRepository.findAll();
          List<ProjectDto> projectDtos = new ArrayList();
          for(Project project:projects) {
              projectDtos.add(createFromDomain(project));
          }
          return projectDtos;

    }

    @Override
    public Project getProject(Long id) {
        return projectRepository.findById(id).orElseThrow(IllegalStateException::new);
    }

    public Project createProject(ProjectModel projectModel) {
        User user = userService.getUser(projectModel.getUserId());


        Project project = new Project();
        project.setUserId(user);
        project.setUrl(projectModel.getUrl());
        projectRepository.save(project);
        return project;
    }

    @Override
    public Project updateProject(ProjectModel projectModel, Long id) {
        User user = userService.getUser(projectModel.getUserId());
        Project project = projectRepository.findById(id).orElseThrow(IllegalStateException::new);

        project.setUserId(user);
        project.setUrl(projectModel.getUrl());
        projectRepository.save(project);

        return project;

    }

    @Override
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(IllegalStateException::new);
        projectRepository.delete(project);
    }



    private ProjectDto createFromDomain(Project project) {
        return new ProjectDto(project.getId(),project.getUserId().getFirstName(),project.getUrl());
    }

}
