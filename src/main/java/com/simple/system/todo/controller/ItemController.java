package com.simple.system.todo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/item")
public class ItemController {

	@GetMapping
	@RequestMapping(path = "/{name}")
	public ResponseEntity<String> getItem(@PathVariable(value = "name") String name) {
		return new ResponseEntity<String>("Hello Service From ItemController", HttpStatus.OK);
	}

}
