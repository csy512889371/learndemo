package com.ctoedu.service.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginController {

	@RequestMapping("/login")
	public String login(){
		return "login";
	}
}
