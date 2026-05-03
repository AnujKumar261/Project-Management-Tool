package com.example.project_management_tool.web;

import com.example.project_management_tool.domain.Project;
import com.example.project_management_tool.services.MapValidationErrorService;
import com.example.project_management_tool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/project")
@CrossOrigin("*")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    // CREATE PROJECT
    @PostMapping("")
    public ResponseEntity<?> createNewProject(
            @Valid @RequestBody Project project,
            BindingResult result,
            Principal principal) {

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }

        if (principal == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        Project savedProject =
                projectService.saveOrUpdateProject(project, principal.getName());

        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }

    // GET BY ID
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(
            @PathVariable String projectId,
            Principal principal) {

        if (principal == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        Project project =
                projectService.findProjectByIdentifier(projectId, principal.getName());

        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    // GET ALL
    @GetMapping("/all")
    public ResponseEntity<?> getAllProjects(Principal principal) {

        if (principal == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(
                projectService.findAllProjects(principal.getName()),
                HttpStatus.OK
        );
    }

    // DELETE
    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(
            @PathVariable String projectId,
            Principal principal) {

        if (principal == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        projectService.deleteProjectByIdentifier(projectId, principal.getName());

        return new ResponseEntity<>("Project deleted", HttpStatus.OK);
    }
}