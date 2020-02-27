package com.uniovi.controllers;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uniovi.entities.Mark;
import com.uniovi.entities.Professor;
import com.uniovi.entities.User;
import com.uniovi.services.MarksService;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;

@Controller
public class UsersController {
	@Autowired
	private UsersService usersService;

	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private MarksService marksService;
	
	@Autowired
	private SignUpFormValidator signUpFormValidator;
	
	@Autowired
	private RolesService rolesService;

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup( @Validated User user, BindingResult result) {
		signUpFormValidator.validate(user, result);
		if( result.hasErrors()) {
			return "signup";
		}
		user.setRole(rolesService.getRoles()[0]);
		usersService.addUser(user);
		securityService.autoLogin(user.getDni(), user.getPasswordConfirm());
		return "redirect:home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}

//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public String login(Model model, Pageable pageable) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String dni = auth.getName();
//		User user = usersService.getUserByDni(dni);
//		Page<Mark> marks = new PageImpl<Mark>(new LinkedList<Mark>());
//		marks = marksService.getMarksForUser(user, pageable);
//		model.addAttribute("markList", marks.getContent());
//		model.addAttribute("page", pageable );
//		return "redirect:home";
//	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String dni = auth.getName();
		User user = usersService.getUserByDni(dni);
		Iterable<Mark> marks = new PageImpl<Mark>(new LinkedList<Mark>());
		marks = marksService.getMarksForUser(user);
		model.addAttribute("markList", marks);
		return "home";
	}

	@RequestMapping("/user/list")
	public String getListado(Model model, Pageable pageable, String searchText) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		if (searchText != null && !searchText.isEmpty()) {
			users = usersService.searchMarksByDescriptionAndNameForUser(pageable,searchText);
		} else {
			users = usersService.getUsers(pageable);
		}
		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);
		
		return "user/list";
	}
	
	@RequestMapping("/user/list/update")
	public String updateList(Model model, Pageable pageable) {
		Page<User> users = new PageImpl<User>(new LinkedList<User>());
		users =  usersService.getUsers(pageable);
		model.addAttribute("usersList", users.getContent());
		model.addAttribute("page", users);
		return "user/list :: tableUsers";
	}

	@RequestMapping(value = "/user/add")
	public String getUser(Model model) {
		model.addAttribute("rolesList", rolesService.getRoles());
		return "user/add";
	}

	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public String setUser(@ModelAttribute User user) {
		usersService.addUser(user);
		return "redirect:/user/list";
	}

	@RequestMapping("/user/details/{id}")
	public String getDetail(Model model, @PathVariable Long id) {
		model.addAttribute("user", usersService.getUser(id));
		return "user/details";
	}

	@RequestMapping("/user/delete/{id}")
	public String delete(@PathVariable Long id) {
		usersService.deleteUser(id);
		return "redirect:/user/list";
	}

	@RequestMapping(value = "/user/edit/{id}")
	public String getEdit(Model model, @PathVariable Long id, Pageable pageable) {
		model.addAttribute("user", usersService.getUser(id));
		model.addAttribute("usersList", usersService.getUsers(pageable));
		return "user/edit";
	}

	@RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
	public String setEdit(Model model, @PathVariable Long id, @ModelAttribute User user) {
		User original = usersService.getUser(id);
		original.setDni(user.getDni());
		original.setName(user.getName());
		original.setLastName(user.getLastName());
		usersService.addUser(original);
		return "redirect:/user/details/" + id;
	}
}