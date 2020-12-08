package dk.kea.taskz.Controllers;

import dk.kea.taskz.Services.MemberService;
import dk.kea.taskz.Services.SubprojectService;
import dk.kea.taskz.Services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
	
	@Autowired
	MemberService memberService;

	@Autowired
	TaskService taskService;

	@Autowired
	SubprojectService subprojectService;

	int activeUserId = -1;

	@GetMapping("/profile")
	public String yourProfile(Model model, HttpServletRequest request) {
		
		Cookie[] ck = request.getCookies();
		for (Cookie cookie : ck) {
			if (cookie.getName().equals("id")) {
				activeUserId = Integer.parseInt(cookie.getValue());
			}
		}
		
		model.addAttribute("member", memberService.getSingleMember(activeUserId));
		model.addAttribute("tasks", taskService.getAllTasks(activeUserId));
		model.addAttribute("earliestDeadLine", taskService.getEarlistDeadline(activeUserId));

		return "yourProfile";
	}


}