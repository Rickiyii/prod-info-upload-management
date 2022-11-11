package org.flowwork.exception;

import java.util.Collection;

/**
 * Service warning exception
 * 
 * @author wyx
 *
 */
public class ServiceWaringException extends BaseException {
	private static final long serialVersionUID = 1L;

	public ServiceWaringException(String message) {
		super(message);
	}

	public ServiceWaringException(int code) {
		super(null, code);
	}

	public ServiceWaringException(int code, Object... params) {
		super(null, code, params);
	}

	public ServiceWaringException(int code, Collection<?> details) {
		super(null, code, details);
	}

	public ServiceWaringException(int code, Object[] params, Collection<?> details) {
		super(null, code, params, details);
	}

	public ServiceWaringException(String message, int code) {
		super(message, code);
	}

	public ServiceWaringException(String message, int code, Object[] params) {
		super(message, code, params);
	}

	public ServiceWaringException(String message, int code, Object[] params, Collection<?> details) {
		super(message, code, params, details);
	}
}
