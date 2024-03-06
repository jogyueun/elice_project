package com.main.my_project.repository;


import com.main.my_project.dto.BoardDto;
import com.main.my_project.entity.Board;
import com.main.my_project.entity.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepository {
    private final JdbcTemplate jdbcTemplate;

    public BoardRepository(DataSource dataSource) {

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 게시글 전체 조회하기
    public List<Board> findAll() {
        String sql = "select * from board";
        return jdbcTemplate.query(sql, boardRowMapper());
    }

    // 게시글 한 개 조회하기
    public Optional<Board> findById(Long id) {
        String sql = "select * from board where id = ?";
        return jdbcTemplate.query(sql, boardRowMapper(), id).stream().findAny();
    }

    // 게시글 생성하기
    public Board save(Board board) {
        String sql = "insert into board(title, content, nickname, password) values (?,?,?,?)";

        int boardId = jdbcTemplate.update(
                sql,
                board.getTitle(),
                board.getContent(),
                board.getNickname(),
                board.getPassword());

        board.setId((long) boardId);

        return board;
    }

    // 게시글 수정하기
    public int update(Board board) {
        String sql = "update board set title = ?, content = ?";
        return jdbcTemplate.update(sql, board.getTitle(),board.getContent());
    }

    // 게시글 삭제하기
    public void deleteById(Long id) {
        String sql = "delete from board where id = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Board> boardRowMapper() {
        return (rs, rowNum) -> {
            Board board = new Board();
            board.setId(rs.getLong("id"));
            board.setTitle(rs.getString("title"));
            board.setContent(rs.getString("content"));
            board.setPassword(rs.getString("password"));
            board.setNickname((rs.getString("nickname")));
            return board;
        };
    }


}
