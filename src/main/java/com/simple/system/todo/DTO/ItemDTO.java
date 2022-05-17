package com.simple.system.todo.DTO;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.simple.system.todo.util.STATUS;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class ItemDTO implements Serializable {

	private Long id;

	@Size(min = 4, max = 250)
	@NotNull
	private String description;

	@NotNull
	@NotBlank
	@Enumerated
	private STATUS status;

	@NotNull
	private LocalDate dueAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}

	public LocalDate getDueAt() {
		return dueAt;
	}

	public void setDueAt(LocalDate dueAt) {
		this.dueAt = dueAt;
	}

}
