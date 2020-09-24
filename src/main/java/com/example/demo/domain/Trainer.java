package com.example.demo.domain;

import com.example.demo.vo.TrainerVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by wzw on 2020/9/24.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Trainer {
    @Id
    private long id;
    @NotNull
    private String name;
    private String grouped;
    public TrainerVO toTrainerVO(){
        TrainerVO trainerVO = new TrainerVO();
        trainerVO.setId(this.id);
        trainerVO.setName(this.name);
        return trainerVO;
    }
}
