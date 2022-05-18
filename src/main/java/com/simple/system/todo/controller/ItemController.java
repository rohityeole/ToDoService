package com.simple.system.todo.controller;

import java.util.List;
import java.util.Optional;

import javax.naming.directory.InvalidAttributesException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class ItemController {

	@Autowired
	private ItemService service;

	/**
	 * GET API Method to Get One Item from To Do list by ID
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/{id}")
	public ResponseEntity<Item> getItem(@PathVariable(value = "id") Long id) {
		Item item;
		Optional<Item> optionalItem = service.getItem(id);
		if (optionalItem.isPresent())
			item = optionalItem.get();
		else
			throw new IllegalArgumentException();
		return new ResponseEntity<Item>(item, HttpStatus.OK);
	}

	/**
	 * 
	 * GET API Method to Return list of All items having Status as `NOT_DONE`
	 * Additionally, It returns all items from without any filter for STATUS if called with `?param+true`
	 * 
	 * @param param
	 * @return List<Item>
	 */
	@GetMapping(path = "/status")
	public ResponseEntity<List<Item>> getAllItems(@RequestParam Optional<String> param) {
		List<Item> items;
		if (!param.isPresent()) {
			items = service.getItemsNotDone();
		} else {
			items = service.getAllItems();
		}
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
	}

	/**
	 * POST API method to Add Item 
	 * 
	 * @param item
	 * @return Item
	 */
	@PostMapping(path = "/add")
	public ResponseEntity<Item> addItem(@Valid @RequestBody ItemDTO item) {
		Item itemEntity = service.addItem(item);
		return new ResponseEntity<Item>(itemEntity, HttpStatus.OK);
	}

	/**
	 * 
	 * 
	 * @param item
	 * @return
	 */
	@PostMapping(path = "/update")
	public ResponseEntity<Item> updateItem(@Valid @RequestBody ItemDTO item) throws InvalidAttributesException {

		Item itemEntity = service.updateItem(item);
		return new ResponseEntity<Item>(itemEntity, HttpStatus.OK);
	}

}
