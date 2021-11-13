package br.com.scheiner.state.machine.controller;

import java.util.UUID;

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

import br.com.scheiner.state.machine.config.StateMachineConfiguration;
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
	
	private StateMachineConfiguration stateMachineConfiguration;

	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping
	public ResponseEntity<String> createOrder(@RequestBody OrderDto orderDto) throws Exception {

		
		/*
		 * StateMachineBuilder.Builder<States, Events> builder =
		 * StateMachineBuilder.builder();
		 * 
		 * stateMachineConfiguration.configure(builder.configureConfiguration());
		 * stateMachineConfiguration.configure(builder.configureStates());
		 * stateMachineConfiguration.configure(builder.configureTransitions());
		 * 
		 * StateMachine<States, Events> stateMachine = builder.build();
		 */
		
		StateMachine<States, Events> stateMachine = factory.getStateMachine();
		
		
		stateMachine.getStateMachineAccessor().doWithAllRegions(access -> access
	          .resetStateMachineReactively(new DefaultStateMachineContext<>(States.ASSEMBLED,null, null,null, null, stateMachine.getId()))
	          .subscribe() );

		
		log.info(String.format("After reset, state: %s, id: %s", stateMachine.getState().getId().toString(), stateMachine.getUuid()));

		//DELIVER | ASSEMBLE
		//Message<Events> m = MessageBuilder.withPayload(Events.ASSEMBLE).setHeader("order-id", orderDto.getId()).build();

		//stateMachine.sendEvent(Mono.just(m)).subscribe();
		
		
		Message<Events> m2 = MessageBuilder.withPayload(Events.DELIVER).setHeader("order-id", orderDto).build();

		stateMachine.sendEvent(Mono.just(m2)).subscribe();
		
		
		
		
		Message<Events> m3 = MessageBuilder.withPayload(Events.RELEASE_INVOICE).setHeader("order-id", orderDto).build();

		stateMachine.sendEvent(Mono.just(m3)).subscribe();
		
		
		//StateMachine<States, Events> stateMachine = factory.getStateMachine();
		

		
		
		///stateMachine.startReactively();

//		Message<Events> m = MessageBuilder.withPayload(Events.DELIVER).setHeader("order-id", orderDto.getId()).build();

	//	stateMachine.sendEvent(Mono.just(m)).subscribe();
		
//		stateMachine.sendEvent(m);
		
		
		

	//	builder.build();
		
		
		//Message<Events> m2 = MessageBuilder.withPayload(Events.DELIVER).setHeader("order-id", "testesss").build();

		//stateMachine.sendEvent(m2);
		
		//stateMachine.stopReactively();

		return ResponseEntity.created(UrlUtils.createUrl(stateMachine.getUuid().toString())).build();
	}
	
	
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping
	public ResponseEntity<String> createState(@RequestBody OrderDto orderDto) throws Exception {
		
StateMachine<States, Events> stateMachine = factory.getStateMachine();
		
		
		stateMachine.getStateMachineAccessor().doWithAllRegions(access -> access
	          .resetStateMachineReactively(new DefaultStateMachineContext<>(States.ASSEMBLED,null, null,null, null, stateMachine.getId()))
	          .subscribe() );

		
		log.info(String.format("After reset, state: %s, id: %s", stateMachine.getState().getId().toString(), stateMachine.getUuid()));
		
	}

}
