package dk.kea.taskz.Controllers;

import dk.kea.taskz.Models.Project;
import dk.kea.taskz.Models.Subproject;
import dk.kea.taskz.Services.ProjectService;
import dk.kea.taskz.Services.SubprojectService;
import dk.kea.taskz.Services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DiagramController
{
    @Autowired
    ProjectService projectService;

    @Autowired
    SubprojectService subprojectService;

    @Autowired
    TaskService taskService;

    @GetMapping("/Gant")
    public String gant(Model model)
    {
        List<Project> allProjects = projectService.getAllProjects();
        Project project = projectService.getProjectByProjectId(44);
        project.setAssociatedSubprojects(subprojectService.getAllAssociatedSubprojects(44));

        for(Project p : allProjects)
        {
            p.setAssociatedSubprojects(subprojectService.getAllAssociatedSubprojects(p.getProjectId()));
        }

        model.addAttribute("projects",allProjects);
        model.addAttribute("project",projectService.getProjectByProjectId(44));

        return "/Gant";
    }

    @GetMapping("/Timeline")
    public String timeline(Model model)
    {
        List<Project> allProjects = projectService.getAllProjects();
        Project project = projectService.getProjectByProjectId(44);
        project.setAssociatedSubprojects(subprojectService.getAllAssociatedSubprojects(44));

        for(Project p : allProjects)
        {
            p.setAssociatedSubprojects(subprojectService.getAllAssociatedSubprojects(p.getProjectId()));
        }

        model.addAttribute("projects",allProjects);
        model.addAttribute("project",projectService.getProjectByProjectId(44));

        return "/Timeline";
    }
}