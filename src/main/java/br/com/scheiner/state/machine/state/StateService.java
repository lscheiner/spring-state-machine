package br.com.scheiner.state.machine.state;

import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

import br.com.scheiner.state.machine.dto.OrderDto;
import br.com.scheiner.state.machine.events.Events;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WithStateMachine(name = "TESTE_STATE")
public class StateService {

	@OnTransition(source = "ORDERED", target = "ASSEMBLED")
	public void eventAssemble(StateContext<States, Events> stateContext , Message<Events> message) {
		log.info("Buscando ID de ORDERED para  ASSEMBLED ID"+ message.getHeaders().get("order-id"));
		System.out.println(stateContext.getTarget().getId());
	}

	
	@OnTransition(source = "ASSEMBLED", target = "DELIVERED")
	public void blabla(StateContext<States, Events> stateContext ,  Message<Events> message) {
		
		OrderDto o = (OrderDto) message.getHeaders().get("order-id");
		o.setId(Math.random() + "");
		
		log.info("Buscando ID de ASSEMBLED para  DELIVERED ID"+ message.getHeaders().get("order-id") + "State machine UUID "+stateContext.getStateMachine().getUuid());
		
	}
	
	
	
	
}
