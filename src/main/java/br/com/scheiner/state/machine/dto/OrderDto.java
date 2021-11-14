package br.com.scheiner.state.machine.dto;

import lombok.Data;

@Data
public class OrderDto {

	private String id;
	private String name;

	private String state;
	private String events;

	public String getState() {
		return state == null ? null : state.toUpperCase();
	}

	public String getEvents() {
		return events == null ? null : events.toUpperCase();
	}

}
