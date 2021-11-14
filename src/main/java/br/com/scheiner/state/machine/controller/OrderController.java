package br.com.scheiner.state.machine.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.scheiner.state.machine.dto.OrderDto;
import br.com.scheiner.state.machine.events.Events;
import br.com.scheiner.state.machine.state.States;
import br.com.scheiner.state.machine.utils.UrlUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
@Slf4j
public class OrderController {

	private StateMachineFactory<States, Events> factory;

	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping("order")
	public ResponseEntity<String> createOrder(@RequestBody OrderDto orderDto) throws Exception {

		
		StateMachine<States, Events> stateMachine = factory.getStateMachine();


		Message<Events> message = MessageBuilder.withPayload(Events.ASSEMBLE).setHeader("order-id", orderDto).build();
		
		stateMachine
		.sendEvent(Mono.just(message))
		.subscribe();
		
		// stateMachine.stopReactively();

		return ResponseEntity.created(UrlUtils.createUrl(stateMachine.getUuid().toString())).build();
	}

	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping("state")
	public ResponseEntity<String> createState(@RequestBody OrderDto orderDto)  {

		
		StateMachine<States, Events> stateMachine = factory.getStateMachine();

		stateMachine.getStateMachineAccessor().doWithAllRegions(access -> access.resetStateMachineReactively(
				new DefaultStateMachineContext<>(States.valueOf(orderDto.getState()), null, null, null, null, stateMachine.getId()))
				.subscribe());

		log.info(String.format("After reset, state: %s, id: %s", stateMachine.getState().getId().toString(),
				stateMachine.getUuid()));
		
		Message<Events> message = MessageBuilder.withPayload(Events.valueOf(orderDto.getEvents())).setHeader("order-id", orderDto).build();

		stateMachine
		.sendEvent(Mono.just(message))
		.subscribe();
		

		return ResponseEntity.created(UrlUtils.createUrl(stateMachine.getUuid().toString())).build();

	}

}
