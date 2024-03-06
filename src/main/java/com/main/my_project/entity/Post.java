package com.main.my_project.entity;


import com.main.my_project.dto.PostDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "post")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Post extends PostDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 자동으로 1씩 증가
    private Long id;            // 대표키

    @Column(nullable = false)
    private String title;

    @Column(nullable = false,length = 1000)
    private String content;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column
    private Timestamp postUpdate;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comment;

    public static Post toEntity(PostDto dto,Board board, List<Comment> comments) {
        return new Post(
                dto.getId(),
                dto.getTitle(),
                dto.getContent(),
                dto.getNickname(),
                dto.getPassword(),
                null,
                board,
                null
        );
    }

}
