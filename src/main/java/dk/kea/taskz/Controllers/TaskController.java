package dk.kea.taskz.Controllers;

import dk.kea.taskz.Models.Enums.Complexity;
import dk.kea.taskz.Models.Enums.Priority;
import dk.kea.taskz.Models.Enums.Status;
import dk.kea.taskz.Models.Project;
import dk.kea.taskz.Models.Subproject;
import dk.kea.taskz.Models.Task;
import dk.kea.taskz.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {

	@Autowired
	SubprojectService subprojectService;

	@Autowired
	ProjectService projectService;

	@Autowired
	TaskService taskService;

	@Autowired
	SkillService skillService;

	@Autowired
	MemberService memberService;

	@Autowired
	TimeService timeService;

	private int activeProjectIDToTest = 1; // This one is only for the header fragment rendering.
	private int subprojectsID = -1;
	private int activeProjectID = 1;
	private int parentProject = -1;

	private List<Subproject> subprojectList = new ArrayList<>();
	private List<Project> projectList = new ArrayList<>();

	/**
	 * - OVO
	 * Gets both the parent project Id, and the subproject Id, and sets them globally.
	 *
	 * @param data
	 * @return
	 */
	@PostMapping("/newTask")
	public String newTaskPost(WebRequest data)
	{
		subprojectsID = Integer.parseInt(data.getParameter("subprojectsID"));
		parentProject = subprojectService.getParentId(subprojectsID);
		return "redirect:/newTask";
	}
	
	/**
	 *  - OVO
	 * Activates the pop up for new task.
	 * @param model
	 * @return subprojects
	 */
	@GetMapping("/newTask")
	public String taskPopUp(Model model)
	{
		if(parentProject == -1)
			return "redirect:/projects";

		model.addAttribute("members", memberService.getAllMembers());
		model.addAttribute("subprojectsID", subprojectsID);
		model.addAttribute("popup", false);
		model.addAttribute("TaskPopUp", true);
		model.addAttribute("stopScroll", true);
		model.addAttribute("activeProjectIDToTest", activeProjectIDToTest);
		model.addAttribute("subprojectList",subprojectService.getAllAssociatedSubprojectsAndAssociatedTasks(parentProject));
		model.addAttribute("project",projectService.getProjectByProjectId(parentProject));
		model.addAttribute("skills", skillService.getListOfSkills());

		return "subprojects";
	}

	/**
	 *  - OVO + FMP
	 *  Send from data to the database. To create an task.
	 *  Updates Workload_Per_Day based of the new changes introduced by the newly created task
	 * @param data
	 * @return
	 */
	@PostMapping("/newTaskData")
	public String newTaskData(WebRequest data) {

		String subprojectId = data.getParameter("subProjectId");
		String taskName = data.getParameter("taskName");
		String estimatedTime = data.getParameter("estimatedTime");
		String deadline = data.getParameter("deadline");
		String teamMember = data.getParameter("TeamMembers");
		String skill = data.getParameter("skills");

		int priority = Integer.parseInt(data.getParameter("priority"));
		int complexity = Integer.parseInt(data.getParameter("complexity"));
		LocalDate convertDeadlineToLocalDate = LocalDate.parse(deadline);

		if(timeService.isTaskDeadlineBetweenSubprojectStartDateAndDeadline(subprojectService.getSubprojectStartDateDeadline(subprojectsID), convertDeadlineToLocalDate) == false){

			return "redirect:/newTask";
		}
		else
		{
			Task task = new Task(Integer.valueOf(subprojectId), taskName, Priority.values()[priority], Complexity.values()[complexity], LocalDate.parse(deadline), Double.valueOf(estimatedTime), Status.ACTIVE, teamMember, skill);

			taskService.insertTask(task);
			subprojectService.updateSubprojectTotalEstimatedTime(Integer.valueOf(subprojectId));
			subprojectService.updateSubprojectCompletedTime(Integer.valueOf(subprojectId));

			projectService.updateProjectEstimatedTime(parentProject);
			projectService.updateProjectCompletedTime(activeProjectID);
			subprojectService.updateWorkloadPerDay(subprojectService.getAllSubprojects());
			projectService.updateAllProjectsWorkloadPerDay();

			return "redirect:/subprojects";
		}
	}

	/**
	 *  - OVO
	 *  Deletes a task from the database.
	 *  - FMP
	 *  Updates Workload_Per_Day based of the new changes introduced by the newly deleted task
	 * @param data
	 * @return
	 */
	@PostMapping("/deleteTask")
	public String deleteTask(WebRequest data)
	{
		taskService.deleteTask(Integer.parseInt(data.getParameter("deleteTask")));

		subprojectService.updateSubprojectTotalEstimatedTime(subprojectsID);
		projectService.updateProjectEstimatedTime(parentProject);
		subprojectService.updateSubprojectCompletedTime(parentProject);
		projectService.updateProjectCompletedTime(parentProject);

		subprojectService.updateWorkloadPerDay(subprojectService.getAllSubprojects());
		projectService.updateAllProjectsWorkloadPerDay();

		return "redirect:/subprojects";
	}
}
