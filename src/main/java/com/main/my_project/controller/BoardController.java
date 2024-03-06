package com.main.my_project.controller;

import com.main.my_project.dto.BoardDto;
import com.main.my_project.service.BoardService;
import com.main.my_project.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class BoardController {
    @Autowired
    BoardService boardService;

    @Autowired
    PostService postService;



    // 게시판 단일 조회 (내림차순)
    @GetMapping("/boards/{id}")
    public String showBoard(
            @PathVariable(name = "id") Long boardId,
            @RequestParam(name = "sort",defaultValue = "ASC" ) String sort ,
            @RequestParam(name = "page",defaultValue = "1") int page ,
            Model model)
    {
        model.addAttribute("board", boardService.findById(boardId));

        model.addAttribute("postList", postService.postPaging(page, sort, boardId));

        model.addAttribute("page", page);

        model.addAttribute("sort", sort);

        String sortStatus = (sort.equals("ASC")) ?
                "ASC" :
                "DESC";

        model.addAttribute("sortStatus", sortStatus);


        return "boards/show";
    }


    // 게시판 목록 조회
    @GetMapping("/boards")
    public String showBoardList(Model model) {
        model.addAttribute("boardList", boardService.findAll());

        return "boards/main";
    }

    // 게시판 생성페이지 이동
    @GetMapping("/boards/create")
    public String abc() {
        return "boards/create";
    }

    // 게시판 생성
    @PostMapping("/boards/create")
    public String createBoard(@ModelAttribute BoardDto dto) {
        boardService.saveBoard(dto);
        return "redirect:/boards";
    }

    // 게시판 수정 화면 이동
    @GetMapping("/boards/{id}/edit")
    public String edit(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("board", boardService.findById(id));
        return "boards/edit";
    }

    // 게시판 수정 하기
    @PostMapping("/boards/update")
    public String update(@ModelAttribute BoardDto dto,RedirectAttributes rttr) {
        if (boardService.updateBoard(dto)) {
            rttr.addFlashAttribute("msg", "게시판이 정상적으로 수정됐습니다!");
        }
        else {
            rttr.addFlashAttribute("errmsg", "수정실패!! 비밀번호가 다릅니다!!");
        }
        return "redirect:/boards/" + dto.getId();
    }

    // 게시판 삭제하기
    @GetMapping("/boards/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id, RedirectAttributes rttr) {
        boardService.deleteBoard(id);
        rttr.addFlashAttribute("msg", "삭제됐습니다!");
        return "redirect:/boards";
    }
}
