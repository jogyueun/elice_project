package com.main.my_project.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EntityTest {
    // ==================     Board Test     ==============================
    @Test
    void boardAllArgsConstructor(){
        Board b = new Board(1L, "제목", "내용", "닉네임", "비밀번호", null, null);
        assertNotNull(b);
        assertEquals(1L,b.getId());
        assertEquals("제목",b.getTitle());
        assertEquals("내용",b.getContent());
        assertEquals("닉네임",b.getNickname());
        assertEquals("비밀번호",b.getPassword());
    }

    @Test
    void boardGetterSetter() {
        Board b = new Board();
        b.setId(1L);
        b.setTitle("제목");
        b.setContent("내용");
        b.setNickname("닉네임");
        b.setPassword("비밀번호");
        b.setBoardUpdate(null);
        b.setPosts(null);

        assertEquals(1L,b.getId());
        assertEquals("제목",b.getTitle());
        assertEquals("내용",b.getContent());
        assertEquals("닉네임",b.getNickname());
        assertEquals("비밀번호",b.getPassword());
    }

    // ==================     Post Test     ==============================
    @Test
    void postAllArgsConstructor(){
        Post p = new Post(1L,"제목","내용","닉네임","비밀번호",null,null,null);
        assertNotNull(p);
        assertEquals(1L,p.getId());
        assertEquals("제목",p.getTitle());
        assertEquals("내용",p.getContent());
        assertEquals("닉네임",p.getNickname());
        assertEquals("비밀번호",p.getPassword());
    }

    @Test
    void postGetterSetter() {
        Post p = new Post();
        p.setId(1L);
        p.setTitle("제목");
        p.setContent("내용");
        p.setNickname("닉네임");
        p.setPassword("비밀번호");
        p.setBoard(null);
        p.setComment(null);
        p.setPostUpdate(null);


        assertEquals(1L, p.getId());
        assertEquals("제목", p.getTitle());
        assertEquals("내용", p.getContent());
        assertEquals("닉네임", p.getNickname());
        assertEquals("비밀번호", p.getPassword());
    }

    // ==================     Comment Test     ==============================
    @Test
    void commentAllArgsConstructor(){
        Comment c = new Comment(1L, "내용", "닉네임", "비밀번호", null, null);
        assertNotNull(c);
        assertEquals(1L,c.getId());
        assertEquals("내용",c.getContent());
        assertEquals("닉네임",c.getNickname());
        assertEquals("비밀번호",c.getPassword());
    }

    @Test
    void commentGetterSetter() {
        Comment c = new Comment();
        c.setId(1L);
        c.setContent("내용");
        c.setNickname("닉네임");
        c.setPassword("비밀번호");
        c.setCommentUpdate(null);
        c.setPost(null);

        assertEquals(1L,c.getId());
        assertEquals("내용",c.getContent());
        assertEquals("닉네임",c.getNickname());
        assertEquals("비밀번호",c.getPassword());
    }
}
