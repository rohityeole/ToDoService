package com.simple.system.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simple.system.todo.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
