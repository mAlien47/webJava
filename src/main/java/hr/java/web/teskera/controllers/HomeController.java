package hr.java.web.teskera.controllers;

import java.io.Serializable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2326851147704560552L;

	@GetMapping("/")
	public String showHome() {
		return "index";
	}
}
