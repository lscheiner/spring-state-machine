package br.com.scheiner.state.machine.state;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.annotation.OnStateChanged;
import org.springframework.statemachine.annotation.WithStateMachine;

import br.com.scheiner.state.machine.events.Events;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@WithStateMachine(name = "TESTE_STATE")
public class StateService {
	
	@OnStateChanged(source = "ORDERED", target = "ASSEMBLED")
	public void eventAssemble(StateContext<States, Events> stateContext , Message<Events> message) {
		this.log(stateContext, message);
		Message<Events> msg = MessageBuilder.withPayload(Events.DELIVER).setHeader("order-id", message.getHeaders()).build();
		stateContext.getStateMachine().sendEvent(Mono.just(msg)).subscribe();
	}
	@OnStateChanged(source = "ASSEMBLED", target = "DELIVERED")
	public void eventDeliver(StateContext<States, Events> stateContext ,  Message<Events> message) {

		this.log(stateContext, message);
		Message<Events> msg = MessageBuilder.withPayload(Events.RELEASE_INVOICE).setHeader("order-id", message.getHeaders()).build();
		stateContext.getStateMachine().sendEvent(Mono.just(msg)).subscribe();

	}
	
	@OnStateChanged(source = "DELIVERED", target = "INVOICED")
	public void eventReleaseInvoice(StateContext<States, Events> stateContext ,  Message<Events> message) {

		this.log(stateContext, message);
		Message<Events> msg = MessageBuilder.withPayload(Events.PAYMENT_RECEIVED).setHeader("order-id", message.getHeaders()).build();
		stateContext.getStateMachine().sendEvent(Mono.just(msg)).subscribe();
	}
	
	@OnStateChanged(source = "INVOICED", target = "PAYED")
	public void eventPaymentReceived(StateContext<States, Events> stateContext ,  Message<Events> message) {
		
		stateContext.getStateMachine().stopReactively().subscribe();
		this.log(stateContext, message);
	}
	
	@OnStateChanged(source = "ORDERED", target = "CANCELLED")
	public void eventOrderedCancel(StateContext<States, Events> stateContext ,  Message<Events> message) {
		this.log(stateContext, message);
	}
	
	@OnStateChanged(source = "ASSEMBLED", target = "CANCELLED")
	public void eventAssembledCancel(StateContext<States, Events> stateContext ,  Message<Events> message) {
		this.log(stateContext, message);
	}
	
	@OnStateChanged(source = "DELIVERED", target = "RETURNED")
	public void eventDeliveRedClaim(StateContext<States, Events> stateContext ,  Message<Events> message) {
		this.log(stateContext, message);
	}
	
	@OnStateChanged(source = "INVOICED", target = "RETURNED")
	public void eventInvoicedClaim(StateContext<States, Events> stateContext ,  Message<Events> message) {
		this.log(stateContext, message);
	}
	
	@OnStateChanged(source = "RETURNED", target = "CANCELLED")
	public void eventReturnedClaim(StateContext<States, Events> stateContext ,  Message<Events> message) {
		this.log(stateContext, message);
	}
	
	@OnStateChanged(source = "RETURNED", target = "ASSEMBLED")
	public void eventReassemble(StateContext<States, Events> stateContext ,  Message<Events> message) {
		this.log(stateContext, message);
	}

	private void log(StateContext<States, Events> stateContext , Message<Events> message) {
		log.info("=== @OnStateChanged Order [" + message.getHeaders().get("order-id")+ "] Stage ["+ stateContext.getStage()+"] State ["+stateContext.getStateMachine().getState().getId()+"] ===");
	}
}
