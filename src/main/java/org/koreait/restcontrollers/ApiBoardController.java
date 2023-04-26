package org.koreait.restcontrollers;

import org.koreait.controllers.BoardForm;
import org.koreait.entities.BoardData;
import org.koreait.models.board.BoardDeleteService;
import org.koreait.models.board.BoardInfoService;
import org.koreait.models.board.BoardListService;
import org.koreait.models.board.BoardSaveService;
import org.koreait.rest.JSONResult;
import org.koreait.rest.RestCommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.lang.module.ResolutionException;
import java.util.List;

@RestController
@RequestMapping("/api/board")
public class ApiBoardController {

    private BoardData boardData;
    @Autowired
    private BoardSaveService saveService;
    @Autowired
    private BoardListService listService;
    @Autowired
    private BoardInfoService infoService;
    @Autowired
    private BoardDeleteService deleteService;

    @PostMapping("/save")
    public ResponseEntity<BoardData> boardsave(@RequestBody BoardForm boardForm){
       if(boardForm.getSubject() == null){
            throw new RestCommonException("제목을 추가해 주세요.", HttpStatus.BAD_REQUEST);
        }
        if(boardForm.getContent() == null){
            throw new RestCommonException("내용을 추가해 주세요.", HttpStatus.BAD_REQUEST);
        }
        saveService.save(boardForm);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<BoardData>> boardlist(){
        List<BoardData> boardlist = listService.gets();
       return ResponseEntity.ok().body(boardlist);
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<BoardData> boardinfo(@PathVariable long id){
        BoardData boardinfo = infoService.get(id);
        return ResponseEntity.ok().body(boardinfo);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<BoardData> boarddelete(@PathVariable long id){
        deleteService.delete(id);
        return ResponseEntity.ok().build();
    }


}
