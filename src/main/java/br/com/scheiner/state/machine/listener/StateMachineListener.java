package br.com.scheiner.state.machine.listener;

import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import br.com.scheiner.state.machine.events.Events;
import br.com.scheiner.state.machine.state.States;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StateMachineListener extends StateMachineListenerAdapter<States, Events> {

	@Override
	public void stateChanged(State<States, Events> from, State<States, Events> to) {
		log.info("Order alterado de: " + (from == null ? "null" : from.getId()) + " Para "+ (to == null ? "null" : to.getId()));
	}
}
