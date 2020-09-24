package com.example.demo.vo;

import com.example.demo.domain.Trainer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by wzw on 2020/9/24.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainerVO {
    private long id;
    @NotBlank
    private String name;

    public Trainer toTrainer() {
        Trainer trainer = new Trainer();
        trainer.setId(this.id);
        trainer.setName(this.name);
        return trainer;
    }
}
