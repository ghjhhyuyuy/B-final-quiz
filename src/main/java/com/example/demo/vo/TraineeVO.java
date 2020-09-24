package com.example.demo.vo;

import com.example.demo.domain.Trainee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by wzw on 2020/9/24.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TraineeVO {
    private long id;
    @NotBlank
    private String name;
    @NotBlank
    private String office;
    @NotBlank
    private String github;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String zoomId;

    public Trainee toTrainee() {
        Trainee trainee = new Trainee();
        trainee.setId(this.id);
        trainee.setName(this.name);
        trainee.setGithub(this.github);
        trainee.setOffice(this.office);
        trainee.setEmail(this.email);
        trainee.setZoomId(this.zoomId);
        return trainee;
    }
}
