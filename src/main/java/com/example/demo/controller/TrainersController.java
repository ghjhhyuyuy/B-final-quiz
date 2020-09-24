package com.example.demo.controller;

import com.example.demo.domain.Trainer;
import com.example.demo.service.TrainersService;
import com.example.demo.vo.TrainerVO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by wzw on 2020/9/24.
 */
@RestController
@RequestMapping("/trainers")
public class TrainersController {
    private final TrainersService trainersService;
    public TrainersController(TrainersService trainersService){
        this.trainersService = trainersService;
    }
    @GetMapping
    public List<TrainerVO> getUngroupTrainer(String grouped){
        return trainersService.getUngroupTrainer(grouped);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainerVO addTrainer(@RequestBody @Valid Trainer trainer){
        return trainersService.addTrainer(trainer);
    }
    @DeleteMapping("{trainee_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrainer(@PathVariable("trainee_id") long id){
        trainersService.deleteTrainer(id);
    }
}
