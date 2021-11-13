package br.com.scheiner.state.machine.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "br.com.scheiner" })
public class SpringStateMachineApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringStateMachineApplication.class);
	}

}
