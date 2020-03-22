package com.stevenstier.validation.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stevenstier.validation.model.Login;
import com.stevenstier.validation.model.Registration;

@Controller
public class UserController {

	// view the homepage
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String showhomepage() {
		return "homePage";
	}

	// Return the login view when its invoked with a Get
	@RequestMapping(path = "/login", method = RequestMethod.GET)
	public String showLoginForm(ModelMap mm) {

		if (mm.containsAttribute("loginData") == false) {
			Login empty = new Login();
			mm.put("loginData", empty);
		}
		return "login";
	}

	// Return the login view when its invoked with a post
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	public String processLoginForm(@Valid @ModelAttribute Login login, BindingResult result, RedirectAttributes ra) {

		if (result.hasErrors()) {
			ra.addFlashAttribute("loginData", login);
			ra.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "loginData", result);
			return "redirect:/login";
		} else {
			// This is where things happen for login
			// mailingListDao.save(signUp);
			ra.addFlashAttribute("message", "You have sucessfully logged in.");
			return "redirect:/confirmation";
		}
	}

	@RequestMapping(path = "/register", method = RequestMethod.GET)
	public String showRegisterForm(ModelMap mm) {

		if (mm.containsAttribute("registerData") == false) {
			Registration empty = new Registration();
			mm.put("registerData", empty);
		}
		return "register";
	}

	@RequestMapping(path = "/register", method = RequestMethod.POST)
	public String processRegistrationForm(@Valid @ModelAttribute Registration registration, BindingResult result,
			RedirectAttributes ra) {

		if (result.hasErrors()) {
			ra.addFlashAttribute("registerData", registration);
			ra.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "registerData", result);
			return "redirect:/register";
		} else {
			// This is where things happen for login
			// mailingListDao.save(signUp);
			ra.addFlashAttribute("message", "You have sucessfully registered.");
			return "redirect:/confirmation";
		}
	}

	// Return the confirmation view
	@RequestMapping(path = "/confirmation", method = RequestMethod.GET)
	public String showConfirmationPage(ModelMap mm) {
		return "confirmation";
	}

}
