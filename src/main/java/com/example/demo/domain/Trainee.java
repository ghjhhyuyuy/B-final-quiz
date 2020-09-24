package com.example.demo.domain;

import com.example.demo.vo.TraineeVO;
import com.example.demo.vo.TrainerVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by wzw on 2020/9/24.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Trainee {
    @Id
    private long id;
    private String name;
    private String office;
    private String github;
    private String email;
    private String zoomId;
    private String grouped;
    public TraineeVO toTraineeVO(){
        TraineeVO traineeVO = new TraineeVO();
        traineeVO.setId(this.id);
        traineeVO.setName(this.name);
        traineeVO.setEmail(this.email);
        traineeVO.setGithub(this.github);
        traineeVO.setOffice(this.office);
        traineeVO.setZoomId(this.zoomId);
        return traineeVO;
    }
}
