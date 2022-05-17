package com.simple.system.todo.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simple.system.todo.DTO.ItemDTO;
import com.simple.system.todo.entity.Item;
import com.simple.system.todo.repository.ItemRepository;
import com.simple.system.todo.util.STATUS;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ItemService {

	@Autowired
	ItemRepository itemRepository;

	public Item addItem(ItemDTO itemDTO) {
		Item item = new Item();

		log.info("" + STATUS.valueOf(itemDTO.getStatus().toUpperCase()));

		item.setStatus(STATUS.valueOf(itemDTO.getStatus().toUpperCase()).toString());
		item.setDescription(itemDTO.getDescription());
		Item item2 = itemRepository.save(item);
		return item2;
	}

	public Item getItem(Long i) {
		Item item = itemRepository.findById(i).get();
		return item;
	}

	public List<Item> getItemsNotDone() {
		List<Item> itemList = itemRepository.findAll().stream().filter(filter -> filter.getStatus() == "NOT_DONE")
				.collect(Collectors.toList());
		return itemList;
	}

	public List<Item> getAllItems() {
		List<Item> itemList = itemRepository.findAll();
		return itemList;
	}

}
