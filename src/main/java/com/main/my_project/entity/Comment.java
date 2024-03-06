package com.main.my_project.entity;


import com.main.my_project.dto.CommentDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 자동으로 1씩 증가
    private Long id;            // 대표키

    @Column(nullable = false,length = 1000)
    private String content;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column
    private Timestamp CommentUpdate;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public static Comment toEntity(CommentDto dto, Post post) {
        // 예외발생
        if (dto.getId() != null) {
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다");
        }
        if (dto.getPostId() != post.getId()) {
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id와 매칭되지 않습니다.");
        }

        // 엔티티 생성 및 반환
        return new Comment(
                dto.getId(),
                dto.getContent(),
                dto.getNickname(),
                dto.getPassword(),
                null,
                post
        );
    }

    public void patch(CommentDto dto) {
        // 예외 발생
        if (this.id != dto.getId())
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력됐습니다.");
        // 객체 갱신
        if (dto.getNickname() != null)              // 수정할 닉네임 데이터가 있다면
            this.nickname = dto.getNickname();      // 내용 반영
        if (dto.getContent() != null)                  // 수정할 본문 데이터가 있다면
            this.content = dto.getContent();              // 내용 반영
    }

}
