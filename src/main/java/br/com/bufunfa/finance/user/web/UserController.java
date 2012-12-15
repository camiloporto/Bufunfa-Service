package br.com.bufunfa.finance.user.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {

	@RequestMapping(method = RequestMethod.GET)
	public String home() {
		return "user/index";
	}
}
