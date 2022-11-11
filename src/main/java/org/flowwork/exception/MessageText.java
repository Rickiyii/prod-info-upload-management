package org.flowwork.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * 
 * International utils
 * 
 * @author wyx
 *
 */
@Component
public class MessageText {

	@Autowired
	MessageSource messageSource;

	public String getMessage(int key, Locale local, Object... params) {
		if (local == null) {
			local = new Locale("zh", "CN");
		}
		return messageSource.getMessage(String.valueOf(key), params, local);
	}

	public String getMessage(int key, Locale local) {
		if (local == null) {
			local = new Locale("zh", "CN");
		}
		return messageSource.getMessage(String.valueOf(key), null, local);
	}

}
