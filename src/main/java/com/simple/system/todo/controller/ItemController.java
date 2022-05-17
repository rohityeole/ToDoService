package com.simple.system.todo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simple.system.todo.DTO.ItemDTO;
import com.simple.system.todo.entity.Item;
import com.simple.system.todo.service.ItemService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/item")
@Slf4j
public class ItemController {

	@Autowired
	private ItemService service;

	@GetMapping(path = "/{id}")
	public ResponseEntity<Item> getItem(@PathVariable(value = "id") Long id) {
		Item item = service.getItem(id);
		return new ResponseEntity<Item>(item, HttpStatus.OK);
	}

	@GetMapping(path = "/status")
	public ResponseEntity<List<Item>> getAllItems(@RequestParam(value = "all") Optional<String> status) {
		List<Item> items;
		if (!status.isPresent()) {
			items = service.getItemsNotDone();
		} else {
			items = service.getAllItems();
		}
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
	}

	@PostMapping(path = "/add")
	public ResponseEntity<Item> getItem(@Valid @RequestBody ItemDTO item) {
		Item itemEntity = service.addItem(item);
		return new ResponseEntity<Item>(itemEntity, HttpStatus.OK);
	}

}
