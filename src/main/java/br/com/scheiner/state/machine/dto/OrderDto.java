package br.com.scheiner.state.machine.dto;

import br.com.scheiner.state.machine.events.Events;
import br.com.scheiner.state.machine.state.States;
import lombok.Data;

@Data
public class OrderDto {
	
	private String id;
	private String name;
	
	private States state;
	private Events events;


}
