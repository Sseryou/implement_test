package org.koreait.tests;

import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.koreait.controllers.BoardForm;
import org.koreait.models.board.BoardSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;


@Log
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("게시글 저장 단위 및 통합 테스트")
@TestPropertySource(locations="classpath:application-test.properties")
public class BoardSaveTest {

    @Autowired
    private MockMvc mockMvc;

    private BoardForm boardForm;

    @BeforeEach
    void init(){
        boardForm.builder()
                .subject("제목1")
                .content("내용1")
                .build();
    }

    @Test
    @DisplayName("게시글 작성 성공하면 200코드 출력")
    public void saveLocationTest() throws Exception {
        mockMvc.perform(post("/api/board/save")
                .param("subject", "제목2")
                .param("content", "내용2"))
                .andExpect(status().isOk());

    }





}
