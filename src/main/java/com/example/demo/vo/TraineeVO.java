package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by wzw on 2020/9/24.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TraineeVO {
    private long id;
    private String name;
    private String office;
    private String github;
    private String email;
    private String zoomId;
}
