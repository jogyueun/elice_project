package com.main.my_project.dto;

import com.main.my_project.entity.Post;
import com.main.my_project.entity.Comment;
import java.sql.Timestamp;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommentDto {

    private Long id;

    private Long postId;

    private String content;

    private String nickname;

    private String password;

    private Timestamp CommentUpdate;

    public static CommentDto createCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getPost().getId(),
                comment.getContent(),
                comment.getNickname(),
                comment.getPassword(),
                comment.getCommentUpdate()
        );
    }


}
