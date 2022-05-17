package com.simple.system.todo.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.system.todo.entity.Item;
import com.simple.system.todo.repository.ItemRepository;
import com.simple.system.todo.util.STATUS;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ItemControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

	@MockBean
	ItemRepository itemRepository;

	Item item1 = Item.builder().id(new Long(1)).description("To Do Task 1").status(STATUS.NOT_DONE.toString())
			.dueAt(LocalDate.parse("2022-05-18")).build();
	Item item2 = Item.builder().id(new Long(2)).description("To Do Task 2").status(STATUS.DONE.toString())
			.dueAt(LocalDate.parse("2022-05-17")).build();
	Item item3 = Item.builder().id(new Long(3)).description("To Do Task 3").status(STATUS.NOT_DONE.toString())
			.dueAt(LocalDate.parse("2022-05-14")).build();
	Item item4 = Item.builder().id(new Long(4)).description("To Do Task 4").status(STATUS.NOT_DONE.toString())
			.dueAt(LocalDate.parse("2022-05-22")).build();
	Item item5 = Item.builder().id(new Long(5)).description("To Do Task 5").status(STATUS.PAST_DUE.toString())
			.dueAt(LocalDate.parse("2022-04-10")).build();

	@Test
	void testGetItemLong() throws Exception {
		Mockito.when(itemRepository.findById(item1.getId())).thenReturn(java.util.Optional.of(item1));

		mockMvc.perform(MockMvcRequestBuilders.get("/item/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.status", is("NOT_DONE")));
	}

	@Test
	void testGetAllItems() throws Exception {
		List<Item> items = new ArrayList<>();
		items.add(item1);
		items.add(item2);
		items.add(item3);
		items.add(item4);
		items.add(item5);

		Mockito.when(itemRepository.findAll()).thenReturn(items);

		mockMvc.perform(MockMvcRequestBuilders.get("/item/status")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[2].description", is("To Do Task 4")));

	}

	@Test
	void testAddItemItemDTO() throws Exception {
		Item item = Item.builder().description("To Dos Task Service Assignment").status(STATUS.NOT_DONE.toString())
				.dueAt(LocalDate.parse("2022-04-10")).build();

		Mockito.when(itemRepository.save(item)).thenReturn(item);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/item/add")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(item));

		mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.description", is("To Dos Task Service Assignment")));
	}

	@Test
	void testUpdateItem() throws Exception {
		Item updatedItem = Item.builder().id(1l).description("Update in the Description")
				.status(STATUS.NOT_DONE.toString()).dueAt(LocalDate.parse("2022-04-19")).build();

		Mockito.when(itemRepository.findById(item1.getId())).thenReturn(Optional.of(item1));
		Mockito.when(itemRepository.save(updatedItem)).thenReturn(updatedItem);

		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/item/update")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(updatedItem));

		mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.description", is("Update in the Description")));
	}

}
