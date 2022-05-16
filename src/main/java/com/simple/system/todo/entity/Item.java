package com.simple.system.todo.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "item")
public class Item {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String description;

	@Column
	private String status;

	@Column
	@CreationTimestamp
	private LocalDate createdAt;

	@Column
	@UpdateTimestamp
	private LocalDate updatedAt;

	@Column
	private LocalDate completedAt;

}
