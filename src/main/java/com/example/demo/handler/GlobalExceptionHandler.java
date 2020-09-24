package com.example.demo.handler;

import com.example.demo.exception.NameRepeatException;
import com.example.demo.exception.NotFindException;
import com.example.demo.exception.TooLessTrainerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.example.demo.vo.Error;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wzw on 2020/9/24.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFindException.class)
    public ResponseEntity<Error> handle(NotFindException exception) {
        HttpStatus state = HttpStatus.NOT_FOUND;
        Error error = new Error();
        error.setMessage("请求的资源不存在");
        return ResponseEntity.status(state).body(error);
    }
    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<Error> handle(InvalidParameterException exception) {
        HttpStatus state = HttpStatus.BAD_REQUEST;
        Map<String,String> detailsMap = new HashMap<>();
        Error error = new Error();
        error.setMessage("参数检查出错");
        detailsMap.put(exception.toString(),exception.getMessage());
        error.setDetails(detailsMap);
        return ResponseEntity.status(state).body(error);
    }
    @ExceptionHandler(TooLessTrainerException.class)
    public ResponseEntity<Error> handle(TooLessTrainerException exception) {
        HttpStatus state = HttpStatus.BAD_REQUEST;
        Error error = new Error();
        error.setMessage("讲师太少不足与分组");
        return ResponseEntity.status(state).body(error);
    }
    @ExceptionHandler(NameRepeatException.class)
    public ResponseEntity<Error> handle(NameRepeatException exception) {
        HttpStatus state = HttpStatus.BAD_REQUEST;
        Error error = new Error();
        error.setMessage("命名重复");
        return ResponseEntity.status(state).body(error);
    }
}
