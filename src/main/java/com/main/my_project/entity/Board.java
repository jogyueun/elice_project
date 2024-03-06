package com.main.my_project.entity;

import com.main.my_project.dto.BoardDto;
import com.main.my_project.service.PostService;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "board")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Board {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY) // DB가 자동으로 1씩 증가
    private Long id;            // 대표키

    @Column(nullable = false)
    private String title;

    @Column(nullable = false,length = 1000)
    private String content;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

//    @CreationTimestamp
//    @Column
//    private Timestamp boardUpdate;
    @CreationTimestamp
    @Column
    private LocalDateTime boardUpdate;



    @OnDelete(action = OnDeleteAction.CASCADE) // 연결된 모드 데이터 삭제
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;


    public static Board toEntity(BoardDto dto, List<Post> posts) {

        LocalDateTime time = LocalDateTime.now();

        Board board = Board.builder()
                .id(null)
                .title(dto.getTitle())
                .content(dto.getContent())
                .nickname(dto.getNickname())
                .password(dto.getPassword())
                .boardUpdate(time)
                .posts(posts)
                .build();

        return board;
    }



}
