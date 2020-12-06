package dk.kea.taskz.Controllers;

import dk.kea.taskz.Models.Enums.Complexity;
import dk.kea.taskz.Models.Enums.Priority;
import dk.kea.taskz.Models.Enums.Status;
import dk.kea.taskz.Models.Task;
import dk.kea.taskz.Services.CompetenceService;
import dk.kea.taskz.Services.MemberService;
import dk.kea.taskz.Services.ProjectService;
import dk.kea.taskz.Services.SubprojectService;
import dk.kea.taskz.Services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;

@Controller
public class TaskController {

	@Autowired
	SubprojectService subprojectService;

	@Autowired
	ProjectService projectService;

	@Autowired
	TaskService taskService;

	@Autowired
	CompetenceService competenceService;

	@Autowired
	MemberService memberService;

	int activeProjectIDToTest = 1; // This one is only for the header fragment rendering.
	int subprojectsID = -1;
	int activeProjectID = 1;
	int parentProject = -1;


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
		model.addAttribute("subprojectList",subprojectService.getAllAssociatedSubprojects(parentProject));
		model.addAttribute("project",projectService.getProjectByProjectId(parentProject));
		model.addAttribute("competences", competenceService.getAllCompetences());
		return "subprojects";
	}

	/**
	 *  - OVO
	 *  Send from data to the database. To create an task.
	 * @param data
	 * @return
	 */
	@PostMapping("/newTaskData")
	public String newTaskData(WebRequest data) {
		String subprojectId = data.getParameter("subProjectId");
		String taskName = data.getParameter("taskName");
		int priority = Integer.parseInt(data.getParameter("priority"));
		int complexity = Integer.parseInt(data.getParameter("complexity"));
		String estimatedTime = data.getParameter("estimatedTime");
		String deadline = data.getParameter("deadline");
		String member = data.getParameter("TeamMembers");
		String tag = data.getParameter("tags");




		Task task = new Task(Integer.valueOf(subprojectId), taskName, Priority.values()[priority], Complexity.values()[complexity], LocalDate.parse(deadline),  Double.valueOf(estimatedTime), Status.ACTIVE, member, tag);
		
		taskService.insertTask(task);
		
		return "redirect:/subprojects";
	}

	/**
	 *  - OVO
	 *  Sletter en task fra databasen.
	 * @param data
	 * @return
	 */
	@PostMapping("/deleteTask")
	public String deleteTask(WebRequest data) {
		int idTask = Integer.parseInt(data.getParameter("deleteTask"));
		taskService.deleteTask(idTask);
		return "redirect:/subprojects";
	}

	/**
	 * - FMP
	 * Updates the status of a specific task, changing the display values of that element
	 * Redirects to subprojects
	 * @param data
	 * @return
	 */
	@PostMapping("/postChangeStatus")
	public String postChangeStatus(WebRequest data) {
		int idTask = Integer.parseInt(data.getParameter("changeStatus"));

		taskService.updateTaskStatus(idTask);

		return "redirect:/subprojects";
	}
}
