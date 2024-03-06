package com.main.my_project.service;

import com.main.my_project.dto.CommentDto;
import com.main.my_project.entity.Comment;
import com.main.my_project.entity.Post;
import com.main.my_project.repository.CommentRepository;
import com.main.my_project.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostService PostService;

    @Autowired
    PostRepository postRepository;


    // 댓글 목록 조회
    public List<CommentDto> comments(Long postId,String sort) {
        List<CommentDto> commentDtos = (sort.equals("ASC")) ?
                // 최신순 오름차순
                commentRepository.findAllByPostIdOrderByCommentUpdateAsc(postId)
                        .stream()
                        .map(comment -> CommentDto.createCommentDto(comment))
                        .collect(Collectors.toList()) :
                // 최신순 내림차순
                commentRepository.findAllByPostIdOrderByCommentUpdateDesc(postId)
                        .stream()
                        .map(comment -> CommentDto.createCommentDto(comment))
                        .collect(Collectors.toList());
        return commentDtos;
    }

    // 댓글 생성
    @Transactional
    public CommentDto create(Long postId, CommentDto dto) throws IllegalAccessException{
        // 게시글 조회 및 예외 발생
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalAccessException("댓글 생성 실패! " + "대상 게시글이 없습니다."));
        // 댓글 엔티티 생성
        Comment comment = Comment.toEntity(dto, post);
        // 댓글 엔티티를 DB에 저장
        Comment created = commentRepository.save(comment);
        // DTO로 변환해 반환
        return CommentDto.createCommentDto(created);
    }

    // 댓글 수정
    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        // 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 대상 댓글이 없습니다."));
        // 댓글 수정
        target.patch(dto);
        // DB로 갱신
        Comment updated = commentRepository.save(target);
        // 엔티티를 DTO로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }

    // 댓글 삭제
    public CommentDto delete(Long id) {
        // 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! " +
                        "대상이 없습니다."));
        // 댓글 삭제
        commentRepository.delete(target);
        // 삭제 댓글을 DTO로 변환 및 반환
        return CommentDto.createCommentDto(target);
    }
}
