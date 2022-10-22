package portfolio.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portfolio.project.entity.Project;
import portfolio.project.model.ProjectModel;
import portfolio.project.service.ProjectDto;
import portfolio.project.service.ProjectServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    private final ProjectServiceImpl projectService;
    public ProjectController(ProjectServiceImpl projectServiceImpl){
        this.projectService = projectServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<ProjectDto>> getProjects() {
        return new ResponseEntity<List<ProjectDto>>(projectService.getProjects(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        return new ResponseEntity<Project>(projectService.getProject(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody ProjectModel projectModel) {
        return new ResponseEntity<Project>(projectService.createProject(projectModel), HttpStatus.CREATED);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Project> updateProject(@RequestBody ProjectModel projectModel, @PathVariable Long id) {
        return new ResponseEntity<Project>(projectService.updateProject(projectModel, id), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
