package com.simple.system.todo.batch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.simple.system.todo.entity.Item;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomItemReader extends JdbcCursorItemReader<Item> implements ItemReader<Item> {

	public CustomItemReader(@Autowired DataSource primaryDataSource) {
		setDataSource(primaryDataSource);
		setSql("SELECT id, description, status,due_at,completed_at,created_at,updated_at FROM Item where status = 'NOT_DONE' and due_at < CURRENT_DATE();");
		setFetchSize(100);
		setRowMapper(new ItemRowMapper());
	}

	public class ItemRowMapper implements RowMapper<Item> {
		@Override
		public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
			Item item = new Item();
			item.setId(rs.getLong("id"));
			item.setDescription(rs.getString("description"));
			item.setStatus(rs.getString("status"));
			item.setDueAt(LocalDate.parse(rs.getString("due_at")));
			item.setCompletedAt(LocalDate.parse(
					rs.getString("completed_at") == null ? LocalDate.now().toString() : rs.getString("completed_at")));
			item.setCreatedAt(LocalDate.parse(rs.getString("created_at")));
			item.setUpdatedAt(LocalDate.parse(rs.getString("updated_at")));
			log.info(
					"Item : " + item.toString() + " Status : " + item.getStatus() + " Desc : " + item.getDescription());
			return item;
		}
	}

}
