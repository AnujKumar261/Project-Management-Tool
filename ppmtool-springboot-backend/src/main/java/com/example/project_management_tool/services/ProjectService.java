package com.example.project_management_tool.services;

import com.example.project_management_tool.domain.Backlog;
import com.example.project_management_tool.domain.Project;
import com.example.project_management_tool.domain.User;
import com.example.project_management_tool.repositories.BacklogRepository;
import com.example.project_management_tool.repositories.ProjectRepository;
import com.example.project_management_tool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdateProject(Project project, String username) {

        project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        project.setUser(user);
        project.setProjectLeader(username);

        if (project.getId() == null) {
            Backlog backlog = new Backlog();
            project.setBacklog(backlog);
            backlog.setProject(project);
            backlog.setProjectIdentifier(project.getProjectIdentifier());
        } else {
            project.setBacklog(
                    backlogRepository.findByProjectIdentifier(
                            project.getProjectIdentifier()
                    )
            );
        }

        return projectRepository.save(project);
    }

    public Project findProjectByIdentifier(String id, String username) {

        Project project = projectRepository.findByProjectIdentifier(id.toUpperCase());

        if (project == null) {
            throw new RuntimeException("Project not found");
        }

        if (!project.getProjectLeader().equals(username)) {
            throw new RuntimeException("Not authorized");
        }

        return project;
    }

    public Iterable<Project> findAllProjects(String username) {
        return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteProjectByIdentifier(String id, String username) {
        Project project = findProjectByIdentifier(id, username);
        projectRepository.delete(project);
    }
}