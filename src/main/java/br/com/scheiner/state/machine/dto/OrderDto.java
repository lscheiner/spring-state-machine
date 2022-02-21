package br.com.scheiner.state.machine.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDto {

	@Schema(description = "Id Order", name = "id", example = "35034de5-7060-4e53-97da-3d59a1322e9b")
	private String id;

	@Schema(description = "Id Order", name = "name", example = "Criar Order")
	private String name;

	@Schema(description = "Id Order", name = "state", example = "ORDERED")
	private String state;

	@Schema(description = "Event", name = "events", example = "ASSEMBLE")
	private String events;

	public String getState() {
		return state == null ? null : state.toUpperCase();
	}

	public String getEvents() {
		return events == null ? null : events.toUpperCase();
	}

}
