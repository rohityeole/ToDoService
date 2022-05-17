package com.simple.system.todo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

	public Optional<Item> getItem(Long i) {
		Optional<Item> item = itemRepository.findById(i);
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

	public Item addItem(ItemDTO itemDTO) {
		var status = STATUS.NOT_DONE;
		Item item = new Item();

		// Set Item Description
		item.setDescription(itemDTO.getDescription().strip());

		// Map Status from DTO to Item Entity
		var temp = itemDTO.getStatus();
		for (STATUS val : STATUS.values()) {
			if (temp.equals(val))
				status = temp;
		}
		item.setStatus(status.toString());
		log.info("STATUS : " + status.toString());

		item.setDueAt(itemDTO.getDueAt());

		// Update CompletedAt if Status of Item is DONE
		if (STATUS.DONE == status) {
			item.setCompletedAt(LocalDate.now());
		}
		item = itemRepository.save(item);
		return item;
	}

	public Item updateItem(ItemDTO itemDTO) {
		Item item;
		// Fetch 'Item' Object from the Database
		if (null == itemDTO.getId()) {
			throw new IllegalArgumentException("Item Id is null");
		}

		Optional<Item> optionalItem = getItem(itemDTO.getId());
		if (optionalItem.isPresent())
			item = optionalItem.get();
		else
			throw new IllegalArgumentException();

		var status = STATUS.valueOf(item.getStatus());
		// Throw Exception if STATUS is PAST_DUE
		if (STATUS.PAST_DUE == status) {
			throw new IllegalArgumentException();
		}
		// Map Status from DTO to Item Entity
		var temp = itemDTO.getStatus();
		for (STATUS val : STATUS.values()) {
			if (temp.equals(val))
				status = temp;
		}
		item.setStatus(status.toString());

		// Set Item Description
		if (STATUS.DONE == status) {
			item.setCompletedAt(LocalDate.now());
		}

		// Update CompletedAt if Status of Item is DONE
		item.setDescription(itemDTO.getDescription().strip());

		item.setDueAt(itemDTO.getDueAt());

		Item item2 = itemRepository.save(item);
		return item2;
	}

}
