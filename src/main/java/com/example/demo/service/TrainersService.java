package com.example.demo.service;

import com.example.demo.domain.Trainer;
import com.example.demo.exception.NotFindException;
import com.example.demo.repository.TrainersRepository;
import com.example.demo.vo.TrainerVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wzw on 2020/9/24.
 */
@Service
public class TrainersService {
    private final TrainersRepository trainersRepository;
    private static int index = 0;
    public TrainersService(TrainersRepository trainersRepository){
        this.trainersRepository = trainersRepository;
    }

    public List<TrainerVO> getUngroupTrainer(String grouped) {
        List<Trainer> trainerList = trainersRepository.findByGrouped(grouped);
        return trainerList.stream().map(Trainer::toTrainerVO).collect(Collectors.toList());
    }

    public TrainerVO addTrainer(TrainerVO trainerVO) {
        trainerVO.setId(++index);
        Trainer trainer = trainerVO.toTrainer();
        trainer.setGrouped("false");
        return trainersRepository.save(trainer).toTrainerVO();
    }

    public void deleteTrainer(long id) {
        try{
            trainersRepository.deleteById(id);
            index--;
        }catch (Exception e){
            throw new NotFindException();
        }

    }
}
