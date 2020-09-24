package com.example.demo.controller;

import com.example.demo.domain.Trainee;
import com.example.demo.domain.Trainer;
import com.example.demo.service.TraineesService;
import com.example.demo.service.TrainersService;
import com.example.demo.vo.TraineeVO;
import com.example.demo.vo.TrainerVO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by wzw on 2020/9/24.
 */
@RestController
@RequestMapping("/trainees")
@CrossOrigin
public class TraineesController {
    private final TraineesService traineesService;
    public TraineesController(TraineesService traineesService){
        this.traineesService = traineesService;
    }
    @GetMapping
    public List<TraineeVO> getUngroupTrainer(String grouped){
        return traineesService.getUngroupTrainee(grouped);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TraineeVO addTrainer(@RequestBody @Valid TraineeVO trainee){
        return traineesService.addTrainee(trainee);
    }
    @DeleteMapping("{trainee_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrainer(@PathVariable("trainee_id") long id){
        traineesService.deleteTrainee(id);
    }
}
