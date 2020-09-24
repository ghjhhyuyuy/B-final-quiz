package com.example.demo.controller;

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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
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
class TraineeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<Trainee> userJson;
    private Trainee trainee;
    private Trainee inValidTrainer;
    @BeforeEach
    public void setup(){
        trainee = Trainee.builder()
                .name("Panda")
                .email("798@thoughtworks.com")
                .github("ghjhhyuyuy")
                .office("成都")
                .zoomId("67889")
                .grouped("false")
                .build();
        inValidTrainer = Trainee.builder().build();
    }
    @Test
    @Order(1)
    void should_add_trainee_when_valid() throws Exception {
        mockMvc.perform(post("/trainees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson.write(trainee).getJson()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Panda")))
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    @Order(2)
    void should_return_400_when_inValid() throws Exception {
        mockMvc.perform(post("/trainees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson.write(inValidTrainer).getJson()))
                .andExpect(status().isBadRequest());
    }
    @Test
    @Order(3)
    void should_return_trainee_not_in_group() throws Exception {
        mockMvc.perform(get("/trainees?grouped=false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Panda")))
                .andExpect(jsonPath("$[0].id", notNullValue()));
    }

    @Test
    @Order(4)
    void should_delete_trainee_when_exists() throws Exception {
        mockMvc.perform(delete("/trainees/{trainee_id}", 1L))
                .andExpect(status().isNoContent());

    }
    @Test
    @Order(5)
    void should_return_404_when_id_not_exist() throws Exception {
        mockMvc.perform(delete("/trainees/{trainee_id}", 123L))
                .andExpect(status().isNotFound());
    }

}