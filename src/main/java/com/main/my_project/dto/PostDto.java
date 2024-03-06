package com.main.my_project.dto;

import com.main.my_project.entity.Board;
import com.main.my_project.entity.Post;
import java.sql.Timestamp;

import com.main.my_project.repository.BoardRepository;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostDto {

    private Long id;

    private Long boardId;

    private String title;

    private String content;

    private String nickname;

    private String password;

    private Timestamp postUpdate;


    public static PostDto createPostDto(Post post) {
        return new PostDto(
                post.getId(),
                post.getBoard().getId(),
                post.getTitle(),
                post.getContent(),
                post.getNickname(),
                post.getPassword(),
                post.getPostUpdate()
        );
    }


}
