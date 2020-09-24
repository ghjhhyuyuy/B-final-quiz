package com.example.demo.vo;

import lombok.Data;

import java.util.Map;

/**
 * Created by wzw on 2020/9/24.
 */
@Data
public class Error {
    String message;
    Map<String,String> details;
}
