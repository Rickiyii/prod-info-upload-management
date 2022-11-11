package org.flowwork.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.flowwork.exception.*;
import org.flowwork.exception.MessageText;
import org.flowwork.util.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;


@Slf4j
@RestControllerAdvice
public class BaseController {
	@Autowired
	MessageText messageText;


	/***
	 * { "code": 204, "message": "No Content", "data": null }
	 *
	 * @param exception
	 * @param request
	 * @return 204
	 * @throws IOException
	 */
	@ExceptionHandler({ NullContentException.class })
	@ResponseBody
	public ResponseWrapper<ExceptionData> nullContentExceptionHandler(BaseException exception,
																	  HttpServletRequest request) throws IOException {
		ResponseWrapper<ExceptionData> dataWrapper = new ResponseWrapper<ExceptionData>(HttpStatus.NO_CONTENT);
		String message = exception.getLocalizedMessage();
		dataWrapper.setData(null);
		log.warn("Query content is null. Url is {}; message is {}", request.getRequestURI(), message);
		return dataWrapper;
	}

	/***
	 * { "code": 412, "message": "Precondition Failed", "data": { "errorCode":
	 * 100001, "errorMessage": "No user found for conditions.", "details": [
	 * "Required field userid not found." ] } }
	 *
	 * @param exception
	 * @param request
	 * @return 412
	 * @throws IOException
	 */
	@ExceptionHandler({ ServiceWaringException.class })
	@ResponseBody
	public ResponseWrapper<ExceptionData> serviceWarningExceptionHandler(BaseException exception,
																		 HttpServletRequest request) throws IOException {
		ResponseWrapper<ExceptionData> dataWrapper = new ResponseWrapper<ExceptionData>(HttpStatus.PRECONDITION_FAILED);
		String message = exception.getLocalizedMessage();
		Locale local = request.getLocale();
		if (exception.getCode() != 0) {
			try {
				message = messageText.getMessage(exception.getCode(), local, exception.getParams());
			} catch (Exception e) {
				log.warn("exception localize fail, raw message is: {}", message);
			}
		}

		List<String> details = new ArrayList<String>();
		Collection<?> exceptionDetails = exception.getDetails();
		if (!exceptionDetails.isEmpty()) {
			for (Object object : exceptionDetails) {
				if (object instanceof Integer) {
					int errorCode = (Integer) object;
					details.add(messageText.getMessage(errorCode, local));
				} else if (object instanceof ServiceWaringException) {
					ServiceWaringException se = (ServiceWaringException) object;
					details.add(messageText.getMessage(se.getCode(), local, se.getParams()));
				}
			}
		}
		dataWrapper.setData(new ExceptionData(exception.getCode(), message, details));
		log.warn("request failed, url is {}; message is {}", request.getRequestURI(), message);
		return dataWrapper;
	}

	/**
	 * { "code": 500, "message": "Internal Server Error", "data": { "cause":
	 * "Exception in thread main java.lang.NullPointerException at
	 * se.citerus.dddsample.Main.main(Main.java: 6)" } }
	 *
	 * @param exception
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseWrapper<ExceptionData> exceptionHandler(Exception exception, HttpServletRequest request) {
		ResponseWrapper<ExceptionData> dataWrapper = new ResponseWrapper<ExceptionData>(
				HttpStatus.INTERNAL_SERVER_ERROR);
		String message = exception.getLocalizedMessage();
		String cause = ExceptionUtils.getStackTrace(exception);
		dataWrapper.setData(new ExceptionData(null, message, cause));
		log.error("server error url is {}; message is {}", request.getRequestURI(), message, exception);
		return dataWrapper;
	}

	/**
	 * { "code": 500, "message": "Internal Server Error", "data": { "cause":
	 * "Exception in thread main java.lang.NullPointerException at
	 * se.citerus.dddsample.Main.main(Main.java: 6)" } }
	 *
	 * @param exception
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ExceptionHandler({ ClientForbiddenException.class })
	@ResponseBody
	public ResponseWrapper<ExceptionData> RequestRejectionHandler(Exception exception, HttpServletRequest request) {
		ResponseWrapper<ExceptionData> dataWrapper = new ResponseWrapper<ExceptionData>(HttpStatus.UNAUTHORIZED);
		String message = exception.getLocalizedMessage();
		dataWrapper.setData(new ExceptionData(null, message));
		return dataWrapper;
	}
}
