package br.com.scheiner.state.machine.config;

import java.util.EnumSet;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import br.com.scheiner.state.machine.events.Events;
import br.com.scheiner.state.machine.listener.StateMachineListener;
import br.com.scheiner.state.machine.state.States;

@Configuration
//@EnableStateMachine
@EnableStateMachineFactory
public class StateMachineConfiguration extends EnumStateMachineConfigurerAdapter <States, Events> {
	
	
	@Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config
          .withConfiguration()
          .autoStartup(true)
          .listener(new StateMachineListener())
          .machineId("TESTE_STATE");
    }
	
	@Override 
	public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
	  states
	    .withStates() 
	    .initial(States.ORDERED)
	    .states(EnumSet.allOf(States.class))
	    .end(States.PAYED);

	}
	
	public Action<States, Events> executeAction() {
	    return context -> {
	    	
	    	System.out.println("Entrei na action" );
	    };
	    
	}
	
	public Action<States, Events> executeAction2() {
	    return context -> {
	    	
	    	System.out.println("Entrei na action2" );
	    };
	    
	}
	
	
	
	@Override
	public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
	  transitions
	   .withExternal()
	   .source(States.ORDERED)
	   .target(States.ASSEMBLED)
	   .event(Events.ASSEMBLE)
	   //.action(executeAction())
	   .and()
	   .withExternal()
	   .source(States.ASSEMBLED)
	   .target(States.DELIVERED)
	   .event(Events.DELIVER)
	   //.action(executeAction2())
	   .and()
	   .withExternal()
	   .source(States.DELIVERED)
	   .target(States.INVOICED)
	   .event(Events.RELEASE_INVOICE)
	   .and()
	   .withExternal()
	   .source(States.INVOICED)
	   .target(States.PAYED)
	   .event(Events.PAYMENT_RECEIVED)
	   .and()
	   .withExternal()
	   .source(States.ORDERED)
	   .target(States.CANCELLED)
	   .event(Events.CANCEL)
	   .and() 
	   .withExternal()
	   .source(States.ASSEMBLED)
	   .target(States.CANCELLED)
	   .event(Events.CANCEL)
	   .and() 
	   .withExternal()
	   .source(States.DELIVERED)
	   .target(States.RETURNED)
	   .event(Events.CLAIM)
	   .and() 
	   .withExternal()
	   .source(States.INVOICED)
	   .target(States.RETURNED)
	   .event(Events.CLAIM)
	   .and() 
	   .withExternal()
	   .source(States.RETURNED)
	   .target(States.CANCELLED)
	   .event(Events.CANCEL)
	   .and() 
	   .withExternal()
	   .source(States.RETURNED)
	   .target(States.ASSEMBLED)
	   .event(Events.REASSEMBLE);
	}
}
