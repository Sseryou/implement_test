package org.koreait.tests;

import jakarta.servlet.ServletException;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.koreait.controllers.BoardForm;
import org.koreait.models.board.BoardDeleteService;
import org.koreait.models.board.BoardSaveService;
import org.koreait.models.board.BoardValidationException;
import org.koreait.rest.RestCommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    @Autowired
    private BoardSaveService saveService;

    @Autowired
    private BoardDeleteService deleteService;

    private BoardForm boardForm;

    @BeforeEach
    void init(){
        boardForm = new BoardForm();
        boardForm.setSubject("제목1");
        boardForm.setContent("내용1");

    }

    @Test
    @DisplayName("게시글 작성 성공하면 200코드 출력")
    public void saveTest() throws Exception {
        String text = String.format("{ \"subject\":\"%s\", \"content\":\"%s\" }",boardForm.getSubject(), boardForm.getContent());
        mockMvc.perform(post("/api/board/save")
                .content(text)
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("올바르지 않은 정보 입력시 오류 출력")
    public void invalidSaveTest() throws Exception {
        assertThrows(Exception.class, () -> {
            String text = String.format("{ \"subject\":\"%s\", \"content\":\"\" }",boardForm.getSubject());
            mockMvc.perform(post("/api/board/save")
                            .content(text)
                            .contentType("application/json"))
                            .andDo(print())
                            .andExpect(status()
                            .isOk());
        });
    }

    @Test
    @DisplayName("list 접근시 200 코드 출력")
    public void listTest() throws Exception {
        mockMvc.perform(get("/api/board/list"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("info 접근시 200 코드 출력")
    public void infoTest() throws Exception {
        saveService.save(boardForm);
            mockMvc.perform(get("/api/board/info/"+boardForm.getId()))
                    .andExpect(status().isOk());
    }
    @Test
    @DisplayName("info 접근 후 올바른 내용이 있는지 확인")
    public void infoReadTest() throws Exception {
        saveService.save(boardForm);
        mockMvc.perform(get("/api/board/info/"+boardForm.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .contains("{\"id\":1,\"subject\":\"제목1\",\"content\":\"내용1\"}");
        //{"id":1,"subject":"제목1","content":"내용1"}

        //String text = String.format("{ \"id\":\"%d\",\"subject\":\"%s\", \"content\":\"%s\" }",boardForm.getId(),boardForm.getSubject(), boardForm.getContent());
    }

    @Test
    @DisplayName("delete 접근시 200 코드 출력")
    public void deleteTest() throws Exception {
        saveService.save(boardForm);
        mockMvc.perform(get("/api/board/delete/"+boardForm.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("delete 접근후 삭제 확인")
    public void deleteAfterTest() throws Exception {
        saveService.save(boardForm);
        Long beforeId = boardForm.getId();
        mockMvc.perform(get("/api/board/delete/"+boardForm.getId()))
                .andExpect(status().isOk());

        assertThrows(ServletException.class, () -> {
            mockMvc.perform(get("/api/board/info/"+beforeId))
                    .andExpect(status().isOk());
        });

    }







}
