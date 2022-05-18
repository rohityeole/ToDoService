package com.simple.system.todo.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.simple.system.todo.entity.Item;
import com.simple.system.todo.util.STATUS;

@Component
public class CustomProcessor implements ItemProcessor<Item, Item> {

	@Override
	public Item process(Item item) throws Exception {
		item.setStatus(STATUS.PAST_DUE.toString());
		return item;
	}

}
