package com.example.demo.service;

import com.example.demo.domain.Group;
import com.example.demo.domain.Trainee;
import com.example.demo.domain.Trainer;
import com.example.demo.exception.NameRepeatException;
import com.example.demo.exception.NotFindException;
import com.example.demo.exception.TooLessTrainerException;
import com.example.demo.repository.GroupsRepository;
import com.example.demo.repository.TraineesRepository;
import com.example.demo.repository.TrainersRepository;
import com.example.demo.vo.GroupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by wzw on 2020/9/24.
 */
@Service
public class GroupsService {
    private final GroupsRepository groupsRepository;
    private final TrainersRepository trainersRepository;
    private final TraineesRepository traineesRepository;

    public GroupsService(GroupsRepository groupsRepository, TrainersRepository trainersRepository, TraineesRepository traineesRepository) {
        this.groupsRepository = groupsRepository;
        this.trainersRepository = trainersRepository;
        this.traineesRepository = traineesRepository;
    }


    public List<GroupVO> getAllGroups() {
        List<GroupVO> groupVOList = new ArrayList<>();
        List<Group> groupList = groupsRepository.findAll();
        for (int i = 0; i < groupList.size(); i++) {
            GroupVO groupVO = transformToGroupVO(groupList.get(i));
            groupVOList.add(groupVO);
        }
        return groupVOList;
    }

    private GroupVO transformToGroupVO(Group group) {
        GroupVO groupVO = new GroupVO();
        groupVO.setId(group.getId());
        groupVO.setName(group.getName());
        String[] stringTrainerList = group.getTrainers().split(",");
        String[] stringTraineeList = group.getTrainees().split(",");
        List<Trainer> trainerList = new ArrayList<>();
        List<Trainee> traineeList = new ArrayList<>();
        for (int i = 0; i < stringTrainerList.length; i++) {
            Optional<Trainer> trainer = trainersRepository.findById(Long.parseLong(stringTrainerList[i]));
            trainer.ifPresent(trainerList::add);
        }
        for (int i = 0; i < stringTraineeList.length; i++) {
            Optional<Trainee> trainer = traineesRepository.findById(Long.parseLong(stringTraineeList[i]));
            trainer.ifPresent(traineeList::add);
        }
        groupVO.setTrainees(traineeList);
        groupVO.setTrainers(trainerList);
        return groupVO;
    }

    public List<GroupVO> createGroups() {
        List<Trainer> trainerList = trainersRepository.findAll();
        if(trainerList.size()<2){
            throw new TooLessTrainerException();
        }
        int groupNum = trainerList.size()/2;
        List<Trainee> traineeList = traineesRepository.findAll();
        int numberOfLine = traineeList.size() / groupNum;
        int moreInLine = traineeList.size() % groupNum;
        List<Group> groupList = groupsRepository.findAll();
        randomlySortedList(traineeList);
        randomlySortedList(trainerList);
        if (groupList.isEmpty()) {
            createNewGroup(trainerList,traineeList,moreInLine,numberOfLine,groupNum);
        } else {
            keepGroupNameGetNewGroup(trainerList,traineeList,moreInLine,numberOfLine,groupNum);
        }
        return getAllGroups();
    }
    private void keepGroupNameGetNewGroup(List<Trainer> trainerList,List<Trainee> newStudentList,int moreInLine,int numberOfLine,int groupNum) {
        int index = 0;
        for (int i = 0; i < moreInLine; i++) {
            List<Trainee> groupTrainee = newStudentList.subList(index, index + numberOfLine + 1);
            List<Trainer> groupTrainer = trainerList.subList(2*i,2*i+1);
            Group group = groupsRepository.findById((long) (i+1)).get();
            String traineeString = "";
            String trainerString = "";
            for (int i1 = 0; i1 < groupTrainee.size(); i1++) {
                traineeString = traineeString +"," +groupTrainee.get(i1).getId();
            }
            for (int i1 = 0; i1 < groupTrainer.size(); i1++) {
                trainerString = trainerString + "," +groupTrainer.get(i1).getId();
            }
            group.setTrainees(traineeString.substring(1));
            group.setTrainers(trainerString.substring(1));
            index += numberOfLine + 1;
            groupsRepository.save(group);
        }
        for (int i = 0; i < groupNum - moreInLine; i++) {
            List<Trainee> groupTrainee = newStudentList.subList(index, index + numberOfLine);
            List<Trainer> groupTrainer = trainerList.subList(2*i,2*i+1);
            Group group = groupsRepository.findById((long) (i+1)).get();
            String traineeString = "";
            String trainerString = "";
            for (int i1 = 0; i1 < groupTrainee.size(); i1++) {
                traineeString = traineeString + "," +groupTrainee.get(i1).getId();
            }
            for (int i1 = 0; i1 < groupTrainer.size(); i1++) {
                trainerString = trainerString + "," +groupTrainer.get(i1).getId();
            }
            group.setTrainees(traineeString.substring(1));
            group.setTrainers(trainerString.substring(1));
            index += numberOfLine;
            groupsRepository.save(group);
        }
    }

    private void createNewGroup(List<Trainer> trainerList,List<Trainee> traineeList,int moreInLine,int numberOfLine,int groupNum) {
        int index = 0;
        for (int i = 0; i < moreInLine; i++) {
            List<Trainee> groupTrainee = traineeList.subList(index, index + numberOfLine + 1);
            List<Trainer> groupTrainer = trainerList.subList(2*i,2*i+2);
            String traineeString = "";
            String trainerString = "";
            for (int i1 = 0; i1 < groupTrainee.size(); i1++) {
                traineeString = traineeString +","+ groupTrainee.get(i1).getId();
            }
            for (int i1 = 0; i1 < groupTrainer.size(); i1++) {
                trainerString = trainerString +","+ groupTrainer.get(i1).getId();
            }
            Group group = new Group(i,"组" + (i + 1),trainerString.substring(1), traineeString.substring(1));
            index += numberOfLine + 1;
            groupsRepository.save(group);
        }
        for (int i = 0; i < groupNum - moreInLine; i++) {
            List<Trainee> groupTrainee = traineeList.subList(index, index + numberOfLine);
            List<Trainer> groupTrainer = trainerList.subList(2*i,2*i+2);
            String traineeString = "";
            String trainerString = "";
            for (int i1 = 0; i1 < groupTrainee.size(); i1++) {
                traineeString = traineeString +","+ groupTrainee.get(i1).getId();
            }
            for (int i1 = 0; i1 < groupTrainer.size(); i1++) {
                trainerString = trainerString +","+ groupTrainer.get(i1).getId();
            }
            Group group = new Group(i + moreInLine,"组" + (i + moreInLine + 1), trainerString.substring(1),traineeString.substring(1));
            index += numberOfLine;
            groupsRepository.save(group);
        }
    }
    private void randomlySortedList(List list) {
        Collections.shuffle(list);
    }
    public String changeGroupName(long id, String name) {
        List<Group> groups = groupsRepository.findAll();
        List<Group> resultList = groups.stream().filter(group -> group.getName().equals(name)).collect(Collectors.toList());
        if(resultList.size() != 0){
            throw new NameRepeatException();
        }
        Optional<Group> optionalGroup = groupsRepository.findById(id);
        if(optionalGroup.isPresent()){
            Group group = optionalGroup.get();
            group.setName(name);
            groupsRepository.save(group);
            return name;
        }
        throw new NotFindException();
    }
}
