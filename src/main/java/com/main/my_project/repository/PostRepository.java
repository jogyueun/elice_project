package com.main.my_project.repository;

import com.main.my_project.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
public interface PostRepository extends JpaRepository<Post,Long> {
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% AND p.board.id = :id")
    List<Post> findByKeyword(@Param("keyword") String keyword,@Param("id") Long id);

    @Query("SELECT p FROM Post p WHERE p.board.id = :boardId ORDER BY p.postUpdate ASC")
    List<Post> findAllByBoardIdOrderByPostUpdateAsc(@Param("boardId") Long boardId);

    @Query("SELECT p FROM Post p WHERE p.board.id = :boardId ORDER BY p.postUpdate DESC")
    List<Post> findAllByBoardIdOrderByPostUpdateDesc(@Param("boardId") Long boardId);

    @Query("SELECT p FROM Post p WHERE p.board.id = :boardId")
    List<Post> findAllByBoardId(@Param("boardId") Long boardId);
}
