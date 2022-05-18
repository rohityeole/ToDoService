package com.simple.system.todo.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.simple.system.todo.entity.Item;
import com.simple.system.todo.repository.ItemRepository;

@Component
public class CustomItemWriter implements ItemWriter<Item> {

	@Autowired
	ItemRepository itemRepository;

	@Override
	public void write(List<? extends Item> items) throws Exception {
		for (Item item : items) {
			itemRepository.save(item);
		}

	}

}
