package br.com.scheiner.state.machine.state;

import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;

import br.com.scheiner.state.machine.events.Events;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WithStateMachine(name = "TESTE_STATE")
public class EventService {

	@OnTransition(source = "ORDERED", target = "ASSEMBLED")
	public void eventAssemble(StateContext<States, Events> stateContext , Message<Events> message) {
		this.log(stateContext, message);
	}
	@OnTransition(source = "ASSEMBLED", target = "DELIVERED")
	public void eventDeliver(StateContext<States, Events> stateContext ,  Message<Events> message) {
		this.log(stateContext, message);
	}
	
	@OnTransition(source = "DELIVERED", target = "INVOICED")
	public void eventReleaseInvoice(StateContext<States, Events> stateContext ,  Message<Events> message) {
		this.log(stateContext, message);
	}
	
	@OnTransition(source = "INVOICED", target = "PAYED")
	public void eventPaymentReceived(StateContext<States, Events> stateContext ,  Message<Events> message) {
		this.log(stateContext, message);
	}
	
	@OnTransition(source = "ORDERED", target = "CANCELLED")
	public void eventOrderedCancel(StateContext<States, Events> stateContext ,  Message<Events> message) {
		this.log(stateContext, message);
	}
	
	@OnTransition(source = "ASSEMBLED", target = "CANCELLED")
	public void eventAssembledCancel(StateContext<States, Events> stateContext ,  Message<Events> message) {
		this.log(stateContext, message);
	}
	
	@OnTransition(source = "DELIVERED", target = "RETURNED")
	public void eventDeliveRedClaim(StateContext<States, Events> stateContext ,  Message<Events> message) {
		this.log(stateContext, message);
	}
	
	@OnTransition(source = "INVOICED", target = "RETURNED")
	public void eventInvoicedClaim(StateContext<States, Events> stateContext ,  Message<Events> message) {
		this.log(stateContext, message);
	}
	
	@OnTransition(source = "RETURNED", target = "CANCELLED")
	public void eventReturnedClaim(StateContext<States, Events> stateContext ,  Message<Events> message) {
		this.log(stateContext, message);
	}
	
	@OnTransition(source = "RETURNED", target = "ASSEMBLED")
	public void eventReassemble(StateContext<States, Events> stateContext ,  Message<Events> message) {
		this.log(stateContext, message);
	}

	private void log(StateContext<States, Events> stateContext , Message<Events> message) {
		log.info("@OnTransition Order [" + message.getHeaders().get("order-id")+ "] Stage ["+ stateContext.getStage()+"] State ["+stateContext.getStateMachine().getState().getId()+"]");
	}
}
