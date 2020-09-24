package com.example.demo.controller;

import com.example.demo.domain.Trainer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by wzw on 2020/9/24.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DataJpaTest
@AutoConfigureJsonTesters
class TrainersControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private JacksonTester<Trainer> userJson;
    private Trainer trainer;
    private Trainer inValidTrainer;
    @BeforeAll
    public void setup(){
        trainer = Trainer.builder()
                .name("Panda")
                .build();
        inValidTrainer = Trainer.builder().build();
        entityManager.persistAndFlush(trainer);
    }
    @Test
    void should_return_teacher_not_in_group() throws Exception {
        mockMvc.perform(get("/trainers?grouped=false"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Panda")))
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    void should_delete_teacher_when_exists() throws Exception {
        mockMvc.perform(delete("/trainers/{trainer_id}", 1L))
                .andExpect(status().isNoContent());
        assertThat(entityManager.getId(trainer)).isEqualTo(null);

    }
    @Test
    void should_return_404_when_id_not_exist() throws Exception {
        mockMvc.perform(delete("/trainers/{trainer_id}", 123L))
                .andExpect(status().isNotFound());
    }
    @Test
    void should_add_teacher_when_valid() throws Exception {
        mockMvc.perform(post("/trainers")
                .content(userJson.write(trainer).getJson()))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Panda")))
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    void should_return_400_when_inValid() throws Exception {
        mockMvc.perform(post("/trainers")
                .content(userJson.write(inValidTrainer).getJson()))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}