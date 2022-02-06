package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import web.model.User;
import web.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PageController {
	private UserService userService;
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
		this.userService.addUser(new User("Иван","Иванов",36));
		this.userService.addUser(new User("Сан","Саныч",50));
		this.userService.addUser(new User("Фёдор","Пупкин",42));
		this.userService.addUser(new User("Семён","Семёныч",23));

	}

	public Long checkValidId (String id) {
		Long result;
		try {
			result = Long.parseLong(id);
			if (!userService.hasId(result)) {
				result = (long) -1;
			}
		}
		catch (RuntimeException e) {
			return (long) -1;
		}
		return result;
	}

	@GetMapping(value = "/")
	public String printWelcome(ModelMap model) {
		List<String> messages = new ArrayList<>();
		messages.add("Hello!");
		messages.add("I'm Spring привет MVC application");
		messages.add("5.2.0 version by sep'19 ");
		model.addAttribute("messages", messages);
		model.addAttribute("userlist",userService.getAllUsers());
		return "index";
	}

	@GetMapping(value = "/delete")
	public String deletePage(Model model, @RequestParam(required = false) String uid) {
		Long id = checkValidId(uid);

		if (id == -1) {
			model.addAttribute("descr","Ничего не получится: Id = "+uid+" юзера для удаления некорректен");
			return "/wrong-id";
		}
		else {
			User user = userService.getById(id);
			userService.removeUser(user);
			model.addAttribute("userDeleted",user);
			model.addAttribute("descr"," удалён");
			return "/delete";
		}

	}

	// страница редактирования пользователя
	@GetMapping(value = "/edit", produces = "text/html; charset=utf-8")
	public String editPage(Model model, @RequestParam(required = false) String uid) {
		Long id = checkValidId(uid);

		if (id == -1) {
			model.addAttribute("descr","Id = "+uid+" юзера для редактирования некорректен");
			return "/wrong-id";
		}
		else {
			model.addAttribute("descr","Редактирование пользователя");
			model.addAttribute("user", userService.getById(id));
			return "/edit";
		}
	}

	// страница добавления нового пользователя
	@GetMapping(value = "/add", produces = "text/html; charset=utf-8")
	public String addPage(Model model) {
		User user = new User();
		user.setId((long) -1);
		model.addAttribute("descr","Добавление нового пользователя");
		model.addAttribute("user", user);
		return "/edit";
	}

	// приём данных редактирования юзера
	@PostMapping()
	public String editUser(@ModelAttribute("user") User user) {
		if (user.getId() != -1) {
			userService.editUser(user);
		}
		else {
			userService.addUser(user);
		}
		return "redirect:/";
	}
}