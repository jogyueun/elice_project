package com.main.my_project.api;


import com.main.my_project.dto.CommentDto;
import com.main.my_project.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class CommentControllerApi {

    @Autowired
    private CommentService commentService;

    // 1. 댓글 조회
    @GetMapping("/api/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable(name = "postId") Long postId,
                                                     @RequestParam(name = "sort",defaultValue = "ASC") String sort,
                                                     Model model) {
        // 서비스에 위임
        List<CommentDto> dtos = commentService.comments(postId,sort);

        if(sort.equals("ASC")){
            model.addAttribute("sortStatus", true);
        }
        else {
            model.addAttribute("sortStatus", false);
        }


        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    // 2. 댓글 생성
    @PostMapping("/api/posts/{postId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable(name = "postId") Long postId,
                                             @RequestBody CommentDto dto) throws IllegalAccessException {
        // 서비스에 위임
        CommentDto createdDto = commentService.create(postId, dto);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
    }

    // 3. 댓글 수정
    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable(name = "id") Long id,
                                             @RequestBody CommentDto dto) {
        // 서비스에 위임
        CommentDto updatedDto = commentService.update(id, dto);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }

    // 4. 댓글 삭제
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable(name = "id") Long id) {
        // 서비스에 위임
        CommentDto deletedDto = commentService.delete(id);
        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(deletedDto);
    }
}
