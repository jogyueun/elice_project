package com.main.my_project.service;

import com.main.my_project.dto.BoardDto;
import com.main.my_project.entity.Board;
import com.main.my_project.entity.Post;
import com.main.my_project.repository.BoardRepository;
import com.main.my_project.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.main.my_project.dto.BoardDto.createBoardDto;

@Slf4j
@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    PostRepository postRepository;

    // 게시판 전체 조회
    public List<BoardDto> findAll() {

        List<Board> boards = boardRepository.findAll();

        List<BoardDto> dtos = new ArrayList<>();
        for(int i = 0; i < boards.size(); i++){
            Board b = boards.get(i);
            BoardDto dto = BoardDto.createBoardDto(b);
            dtos.add(dto);
        }

        return dtos;

    }

    // 게시판 조회
    public BoardDto findById(Long id) {
        return BoardDto.createBoardDto(boardRepository.findById(id).orElse(null));
    }

    // 게시판 생성
    @Transactional
    public void saveBoard(BoardDto dto) {
        List<Post> posts = postRepository.findAllByBoardId(dto.getId());
        Board board = Board.toEntity(dto, posts);
        Board result = boardRepository.save(board);
    }

    // 게시판 수정
    @Transactional
    public boolean updateBoard(BoardDto dto) {
        Board target = boardRepository.findById(dto.getId()).orElse(null);

        // 비밀 번호 비교하기
        if (target.getPassword().equals(dto.getPassword())) {
            target.setTitle(dto.getTitle());
            target.setContent(dto.getContent());
            boardRepository.update(target);
            return true;
        }
        // 비밀 번호가 다를 경우
        else {
            return false;
        }

    }

    // 게시판 삭제
    @Transactional
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }


}

