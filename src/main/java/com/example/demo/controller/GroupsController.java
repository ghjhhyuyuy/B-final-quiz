package com.example.demo.controller;

import com.example.demo.service.GroupsService;
import com.example.demo.vo.GroupVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by wzw on 2020/9/24.
 */
@RestController
@RequestMapping("/groups")
@CrossOrigin
public class GroupsController {
    private final GroupsService groupsService;
    public GroupsController(GroupsService groupsService){
        this.groupsService = groupsService;
    }
    @GetMapping
    public List<GroupVO> getAllGroups(){
        return groupsService.getAllGroups();
    }
    @PostMapping("/auto-grouping")
    public List<GroupVO> createGroups(){
        return groupsService.createGroups();
    }
    @PatchMapping("{group_id}")
    public String changeGroupName(@PathVariable("group_id") long id,@RequestBody String name){
        return groupsService.changeGroupName(id,name);
    }
}
