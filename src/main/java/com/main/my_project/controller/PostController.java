package com.main.my_project.controller;

import com.main.my_project.dto.BoardDto;
import com.main.my_project.dto.CommentDto;
import com.main.my_project.dto.PostDto;
import com.main.my_project.entity.Board;
import com.main.my_project.entity.Comment;
import com.main.my_project.entity.Post;
import com.main.my_project.service.BoardService;
import com.main.my_project.service.CommentService;
import com.main.my_project.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    BoardService boardService;

    @Autowired
    CommentService commentService;


    // 게시글 키워드 검색
    @GetMapping("/posts/{id}/keyword")
    public String keyword(@RequestParam(name = "keyword") String keyword,@PathVariable(name = "id") Long id,Model model) {
        model.addAttribute("board", boardService.findById(postService.findById(id).getBoardId()));
        model.addAttribute("postList", postService.keyword(keyword, id));
        model.addAttribute("post", postService.findById(id));
        return "posts/keyword";
    }


    // 게시글 작성 화면 이동
    @GetMapping("/posts/{boardId}/create")
    public String showView(Model model,@PathVariable(name = "boardId") Long boardId) {
        model.addAttribute("board", boardService.findById(boardId));
        return "posts/create";
    }

    // 게시글 단일 조회
    @GetMapping("/posts/{id}")
    public String showPost(@PathVariable(name = "id") Long id,
                           @RequestParam(name = "sort",defaultValue = "ASC") String sort,
                           Model model) {

        //  모델에 데이터 등록하기
        model.addAttribute("post", postService.findById(id));
        model.addAttribute("commentList", commentService.comments(id,sort));

        if(sort.equals("ASC")){
            model.addAttribute("sortStatus", false);
        }
        else {
            model.addAttribute("sortStatus", true);
        }


        //  뷰 페이지 반환하기
        return "posts/show";
    }

    // 게시판 생성
    @PostMapping("/posts/{boardId}/create")
    public String createPost(@ModelAttribute PostDto dto) {

        // 서비스에 위임
        postService.create(dto);

        // 결과응답
        return "redirect:/boards/" + dto.getBoardId();
    }

    // 게시판 수정 화면 이동
    @GetMapping("/posts/{id}/edit")
    public String edit(@PathVariable(name = "id") Long id, Model model) {


        // 모델에 데이터 등록하기
        model.addAttribute("post", postService.findById(id));

        // 뷰페이지 반환하기
        return "posts/edit";
    }

    // 게시판 수정 하기
    @PostMapping("/posts/update")
    public String update(@ModelAttribute PostDto dto,RedirectAttributes rttr) {
        if (postService.update(dto)) {
            rttr.addFlashAttribute("msg", "게시글이 정상적으로 수정됐습니다!");
        }
        else {
            rttr.addFlashAttribute("errmsg", "수정실패!! 비밀번호가 다릅니다!!");
        }
        return "redirect:/boards/" + dto.getBoardId();
    }

    // 게시판 삭제하기
    @GetMapping("/posts/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        postService.delete(id);
        rttr.addFlashAttribute("msg", "삭제됐습니다!");
        return "redirect:/boards/" + postService.findById(id).getBoardId();
    }

}
