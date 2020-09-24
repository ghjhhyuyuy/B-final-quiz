package com.example.demo.vo;

import com.example.demo.domain.Trainee;
import com.example.demo.domain.Trainer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import java.util.List;

/**
 * Created by wzw on 2020/9/24.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupVO {
    private long id;
    private String name;
    private List<Trainer> trainers;
    private List<Trainee> trainees;
}
