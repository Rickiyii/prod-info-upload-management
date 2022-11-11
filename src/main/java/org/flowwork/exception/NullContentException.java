package org.flowwork.exception;

/**
 * Service error exception
 * 
 * @author wyx
 *
 */
public class NullContentException extends BaseException {
	private static final long serialVersionUID = 1L;

	public NullContentException(String message, int code, Object[] params) {
		super(message, code, params);
	}

	public NullContentException(String message, int code) {
		super(message, code);
	}
	
	public NullContentException(String message) {
		super(message);
	}
}
