package org.flowwork.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Collection;

/**
 * Response data
 *
 * @author wyx
 */
@Getter
@Setter
@ToString
public class ResponseWrapper<T> {

  private int code;
  private String message;

  private T data;

  public ResponseWrapper() {
    this.code = HttpStatus.OK.value();
    this.message = HttpStatus.OK.getReasonPhrase();
  }

  public ResponseWrapper(HttpStatus status) {
    this.code = status.value();
    this.message = status.getReasonPhrase();
  }

  public ResponseWrapper(T data) {

//    if (isEmpty(data)) {
//      this.code = HttpStatus.NO_CONTENT.value();
//      this.message = HttpStatus.NO_CONTENT.getReasonPhrase();
//    } else
    {
      this.code = HttpStatus.OK.value();
      this.message = HttpStatus.OK.getReasonPhrase();
      this.data = data;
    }
  }

  @SuppressWarnings("rawtypes")
  public boolean isEmpty(T data) {
    return data == null || data instanceof Collection && ((Collection) data).isEmpty();
  }

  public ResponseWrapper(HttpStatus status, T data) {
    this.code = status.value();
    this.message = status.getReasonPhrase();
    this.data = data;
  }


  public  T result(){
    return ResponseUtils.result(this);
  }

  public <E extends Object> E result(Class<E> clazz){
    return JsonConvert.objectMapper.convertValue(ResponseUtils.result(this),clazz);
  }

  public <E extends Object> E result(JavaType type){
    return JsonConvert.objectMapper.convertValue(ResponseUtils.result(this),type);
  }

  public <E> E result(TypeReference<?> type){
    return (E) JsonConvert.objectMapper.convertValue(ResponseUtils.result(this),type);
  }

}
