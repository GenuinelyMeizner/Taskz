package dk.kea.taskz.Controllers;

import dk.kea.taskz.Services.CookieService;
import dk.kea.taskz.Services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
	@Autowired
	private MemberService memberService;

	@Autowired
	private CookieService cookieService;

	private Boolean loginFailed = false;

	/**
	 * - RBP
	 * The "root"-mapping which points to the login screen and shows login.html
	 *
	 * @return
	 */
	@GetMapping({"/", "/login"})
	public String login(Model model) {
		model.addAttribute("alert", loginFailed);

		return "login";
	}

	/**
     * - RBP + OVO
	 * The postmapping that will be used after we press the Login-button from login.html.
	 * Declares two variables, username and password which are sent to the service, which gets a list from the
	 * database of all the users.
	 * If the password and username do not match the information on the list, the user will be redirected back to the
	 * login page. If they match, the project-overview screen will be shown.
	 *
	 * Here we get an id from the input, and sets an cookie.
	 * @param data
	 * @return
	 */
    @PostMapping("/postLogin")
	public String postLogin(WebRequest data, HttpServletResponse response) {
		String username = data.getParameter("username");
		String password = data.getParameter("password");

		if (memberService.verifyLogin(username, password)) {
			Cookie ck = new Cookie("id", Integer.toString(memberService.getId(username, password)));
			response.addCookie(ck);
			return "redirect:/projects";
		} else {
			loginFailed = true;
		}

        return "redirect:/login";
    }
}
