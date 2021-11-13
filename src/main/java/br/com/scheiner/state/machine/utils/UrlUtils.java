package br.com.scheiner.state.machine.utils;

import java.net.URI;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class UrlUtils {

	public static URI createUrl(String id) {
		 return ServletUriComponentsBuilder.fromCurrentRequest()
         .path("/{id}")
         .buildAndExpand(id)
         .toUri();
	}
	
	public static URI createUrl(Long id) {
		 return ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(id)
        .toUri();
	}
}
