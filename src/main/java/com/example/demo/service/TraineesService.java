package com.example.demo.service;

import com.example.demo.domain.Trainee;
import com.example.demo.domain.Trainer;
import com.example.demo.exception.NotFindException;
import com.example.demo.vo.TraineeVO;
import com.example.demo.repository.TraineesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wzw on 2020/9/24.
 */
@Service
public class TraineesService {
    private final TraineesRepository traineesRepository;
    private static int index = 0;
    public TraineesService(TraineesRepository traineesRepository){
        this.traineesRepository = traineesRepository;
    }
    public List<TraineeVO> getUngroupTrainee(String grouped) {
        List<Trainee> trainerList = traineesRepository.findByGrouped(grouped);
        return trainerList.stream().map(Trainee::toTraineeVO).collect(Collectors.toList());
    }

    public TraineeVO addTrainee(TraineeVO traineeVO) {
        traineeVO.setId(++index);
        Trainee trainee = traineeVO.toTrainee();
        trainee.setGrouped("false");
        return traineesRepository.save(trainee).toTraineeVO();
    }

    public void deleteTrainee(long id) {
        try{
            traineesRepository.deleteById(id);
            index--;
        }catch (Exception e){
            throw new NotFindException();
        }
    }
}
