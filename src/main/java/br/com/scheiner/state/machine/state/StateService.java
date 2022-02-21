package br.com.scheiner.state.machine.state;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.annotation.OnStateChanged;
import org.springframework.statemachine.annotation.WithStateMachine;

import br.com.scheiner.state.machine.dto.OrderDto;
import br.com.scheiner.state.machine.events.Events;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@WithStateMachine(name = "TESTE_STATE")
public class StateService {
	
	private static final String ID_CANCEL="35034de5-7060-4e53-97da-3d59a1322e9b";
	
	@OnStateChanged(source = "ORDERED", target = "ASSEMBLED")
	public void eventAssemble(StateContext<States, Events> stateContext , Message<Events> message) {
		this.log(stateContext, message);
		this.deliver(stateContext, message);
	}
	
	@OnStateChanged(source = "ASSEMBLED", target = "DELIVERED")
	public void eventDeliver(StateContext<States, Events> stateContext ,  Message<Events> message) {
		this.log(stateContext, message);
		this.releaseInvoice(stateContext, message);
	}
	
	@OnStateChanged(source = "DELIVERED", target = "INVOICED")
	public void eventReleaseInvoice(StateContext<States, Events> stateContext ,  Message<Events> message) {
		this.log(stateContext, message);
		this.paymentReceived(stateContext, message);
	}
	
	@OnStateChanged(source = "INVOICED", target = "PAYED")
	public void eventPaymentReceived(StateContext<States, Events> stateContext ,  Message<Events> message) {
		
		stateContext.getStateMachine().stopReactively().subscribe();
		this.log(stateContext, message);
	}
	
	@OnStateChanged(source = "ORDERED", target = "CANCELLED")
	public void eventOrderedCancel(StateContext<States, Events> stateContext ,  Message<Events> message) {
		stateContext.getStateMachine().stopReactively().subscribe();
		this.log(stateContext, message);
	}
	
	@OnStateChanged(source = "ASSEMBLED", target = "CANCELLED")
	public void eventAssembledCancel(StateContext<States, Events> stateContext ,  Message<Events> message) {
		stateContext.getStateMachine().stopReactively().subscribe();
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

	
	private void deliver(StateContext<States, Events> stateContext , Message<Events> message) {
		
		OrderDto order= (OrderDto) message.getHeaders().get("order-id");
		
		if (ID_CANCEL.equals(order.getId())) { // simula erro e vai pra cancelamento
			Message<Events> msg = MessageBuilder.withPayload(Events.CANCEL).setHeader("order-id", order).build();
			stateContext.getStateMachine().sendEvent(Mono.just(msg)).subscribe();
		}else {
			Message<Events> msg = MessageBuilder.withPayload(Events.DELIVER).setHeader("order-id", order).build();
			stateContext.getStateMachine().sendEvent(Mono.just(msg)).subscribe();
		}
		
	}
	
	private void releaseInvoice(StateContext<States, Events> stateContext , Message<Events> message) {
		
		Message<Events> msg = MessageBuilder.withPayload(Events.RELEASE_INVOICE).setHeader("order-id", message.getHeaders().get("order-id")).build();
		stateContext.getStateMachine().sendEvent(Mono.just(msg)).subscribe();
	}
		
	private void paymentReceived(StateContext<States, Events> stateContext , Message<Events> message) {
		
		Message<Events> msg = MessageBuilder.withPayload(Events.PAYMENT_RECEIVED).setHeader("order-id", message.getHeaders().get("order-id")).build();
		stateContext.getStateMachine().sendEvent(Mono.just(msg)).subscribe();
	}
	
	
	private void log(StateContext<States, Events> stateContext , Message<Events> message) {
		log.info("================================= @OnStateChanged Order [" + message.getHeaders().get("order-id")+ "] Stage ["+ stateContext.getStage()+"] State ["+stateContext.getStateMachine().getState().getId()+"] =================================");
	}
}
