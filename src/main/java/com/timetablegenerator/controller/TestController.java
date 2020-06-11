package com.timetablegenerator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.timetablegenerator.entity.StreamStandard;
import com.timetablegenerator.entity.Subject;

@Controller
public class TestController {

	@RequestMapping("/")
	public String view(ModelAndView mav) {
		
		return "index";
	}
	
	@RequestMapping("/addCollege")
	public String addCollege(ModelAndView mav) {
		
		return "addCollege";
	}
}
                    