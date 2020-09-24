package com.example.demo.controller;

import com.example.demo.domain.Group;
import com.example.demo.domain.Trainee;
import com.example.demo.domain.Trainer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by wzw on 2020/9/24.
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@PropertySource(value = "classpath:application-test.yml",encoding = "UTF-8")
public class GroupsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<Trainee> traineeJson;
    @Autowired
    private JacksonTester<Trainer> trainerJson;
    @BeforeEach
    public void setup() throws Exception {
        Trainee trainee = Trainee.builder()
                .name("Panda")
                .email("798@thoughtworks.com")
                .github("ghjhhyuyuy")
                .office("成都")
                .zoomId("67889")
                .grouped("false")
                .build();
        Trainer trainer = Trainer.builder()
                .name("Panda")
                .grouped("false")
                .build();
        mockMvc.perform(post("/trainees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(traineeJson.write(trainee).getJson()));
        mockMvc.perform(post("/trainees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(traineeJson.write(trainee).getJson()));
        mockMvc.perform(post("/trainers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(trainerJson.write(trainer).getJson()));
        mockMvc.perform(post("/trainers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(trainerJson.write(trainer).getJson()));
    }
    @Test
    @Order(1)
    void should_return_group_list_after_create() throws Exception {
        mockMvc.perform(post("/groups/auto-grouping"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("组1")));
    }

    @Test
    @Order(2)
    void should_return_group_list() throws Exception {
        mockMvc.perform(get("/groups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("组1")));
    }

    @Test
    @Order(3)
    void should_change_group_name() throws Exception {
        mockMvc.perform(patch("/groups/{group_id}",1L)
                .content("haha"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("haha")));
    }
}
