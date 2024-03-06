package com.main.my_project.service;

import com.main.my_project.dto.PostDto;
import com.main.my_project.entity.Board;
import com.main.my_project.entity.Comment;
import com.main.my_project.entity.Post;
import com.main.my_project.repository.CommentRepository;
import com.main.my_project.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    BoardService boardService;

    @Autowired
    CommentRepository commentRepository;

    // 게시글 단일 조회
    public PostDto findById(Long id) {
        //  id를 조회해 데이터 가져오기
        Post post = postRepository.findById(id).orElse(null);
        PostDto postDto = PostDto.createPostDto(post);
        return postDto;
    }


    // 게시글 생성
    @Transactional
    public void create(PostDto dto) {
        Board board = new Board();
        board.setId(dto.getBoardId());

        List<Comment> comments = commentRepository.findAllByPostId(dto.getId());

        // DTO를 엔티티로 변환
        Post post = Post.toEntity(dto,board,comments);

        // 리파지터리로 엔티티를 DB에 저장
        postRepository.save(post);
    }

    // 게시글 수정
    @Transactional
    public boolean update(PostDto dto) {
        Post target = postRepository.findById(dto.getId()).orElse(null);
        // 비밀 번호 비교하기
        if (target.getPassword().equals(dto.getPassword())) {
            target.setTitle(dto.getTitle());
            target.setContent(dto.getContent());
            postRepository.save(target);
            return true;
        }
        else {
            return false;
        }
    }

    // 키워드 검색 기능
    public List<PostDto> keyword(String keyword, Long id) {
        List<PostDto> postList = new ArrayList<>();
        List<Post> posts = postRepository.findByKeyword(keyword, id);
        for (int i = 0; i < posts.size(); i++) {
            Post post = posts.get(i);
            PostDto dto = PostDto.createPostDto(post);
            postList.add(dto);
        }

        return postList;
    }

    // 게시글 삭제
    @Transactional
    public void delete(Long id) {
        // 삭제할 대상 가져오기
        Post target = postRepository.findById(id).orElse(null);
        // 대상 엔티티 삭제하기
        if (target != null) {
            postRepository.delete(target);
        }
    }

// -------------------------------------------------------------------------------------------------------------- //
// ----------------------------------------  페이지 네이션  ------------------------------------------------------- //
// -------------------------------------------------------------------------------------------------------------- //

    // 게시글 조회 ( 페이지 네이션 )
    public Page<PostDto> postPaging(int page, String sort, Long boardId) {
        // 엔티티를 DTO 리스트로 변환
        List<PostDto> postDtos = new ArrayList<>();
        List<Post> posts =
                (sort.equals("ASC")) ?
                        postPagingAsc(boardId) :
                        postPagingDesc(boardId);
        for (int i = 0; i < posts.size(); i++) {
            PostDto dtos = posts.get(i);
            postDtos.add(dtos);
        }

        // 리스트를 페이지로 변환
        PageRequest pageRequest = PageRequest.of(page-1, 2);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), postDtos.size());
        Page<PostDto> postPage = new PageImpl<>(postDtos.subList(start, end), pageRequest, postDtos.size());

        // 총 페이지 수 (전체 컨텐츠 개수 / 한 페이지에 보여주고자 하는 컨텐츠의 개수)
        int total = (int) Math.ceil(postDtos.size() / 2);

        // 화면에 보여질 페이지 그룹 (현재 페이지 번호 / 한 화면에 보여질 페이지 개수)

        int showPageGroup = (int) Math.ceil(page / 5);

        // 화면에 보여질 페이지의 첫번째 페이지 번호 (((페이지 그룹 - 1) * 한 화면에 보여질 페이지 개수) + 1)
        int firstPageNumber = ((showPageGroup-1) * 5) + 1;

        // 화면에 보여질 페이지의 마지막 번호 (페이지 그룹 * 한 화면에 보여질 페이지 개수 )
        int lastPageNumber = showPageGroup * 5;

        // 단, 페이지 그룹 번호 * 한 화면에 보여줄 페이지의 개수가 전체 페이지 개수보다 크다면 전체 페이지가 된다

        return postPage;
    }

    public List<Post> postPagingAsc(Long boardId) {
        return postRepository.findAllByBoardIdOrderByPostUpdateAsc(boardId);
    }

    public List<Post> postPagingDesc(Long boardId) {
        return postRepository.findAllByBoardIdOrderByPostUpdateDesc(boardId);

    }


}
