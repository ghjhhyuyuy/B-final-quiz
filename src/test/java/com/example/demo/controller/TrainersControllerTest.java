package com.example.demo.controller;

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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by wzw on 2020/9/24.
 */
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@PropertySource(value = "classpath:application-test.yml",encoding = "UTF-8")
class TrainersControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<Trainer> userJson;
    private Trainer trainer;
    private Trainer inValidTrainer;
    @BeforeEach
    public void setup(){
        trainer = Trainer.builder()
                .name("Panda")
                .grouped("false")
                .build();
        inValidTrainer = Trainer.builder().build();
    }
    @Test
    @Order(1)
    void should_add_teacher_when_valid() throws Exception {
        mockMvc.perform(post("/trainers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson.write(trainer).getJson()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Panda")))
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    @Order(2)
    void should_return_400_when_inValid() throws Exception {
        mockMvc.perform(post("/trainers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson.write(inValidTrainer).getJson()))
                .andExpect(status().isBadRequest());
    }
    @Test
    @Order(3)
    void should_return_teacher_not_in_group() throws Exception {
        mockMvc.perform(get("/trainers?grouped=false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Panda")))
                .andExpect(jsonPath("$[0].id", notNullValue()));
    }

    @Test
    @Order(4)
    void should_delete_teacher_when_exists() throws Exception {
        mockMvc.perform(delete("/trainers/{trainer_id}", 1L))
                .andExpect(status().isNoContent());

    }
    @Test
    @Order(5)
    void should_return_404_when_id_not_exist() throws Exception {
        mockMvc.perform(delete("/trainers/{trainer_id}", 123L))
                .andExpect(status().isNotFound());
    }

}