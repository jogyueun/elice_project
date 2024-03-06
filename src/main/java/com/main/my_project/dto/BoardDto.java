package com.main.my_project.dto;

import com.main.my_project.entity.Board;
import com.main.my_project.entity.Post;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BoardDto {

    private Long id;

    private String title;

    private String content;

    private String nickname;

    private String password;

    private LocalDateTime boardUpdate;

    public static BoardDto createBoardDto(Board board) {
        return new BoardDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getNickname(),
                board.getPassword(),
                board.getBoardUpdate()
        );
    }
}
