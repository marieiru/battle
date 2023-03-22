package com.example.battle.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class SlgmController {

	@GetMapping("/")
	public String menu() {
		
		return "menu";
	}
	


	
}
