package kr.co.imguru.domain.post.service;

import kr.co.imguru.domain.post.dto.PostCreateDto;
import kr.co.imguru.domain.post.dto.PostReadDto;
import kr.co.imguru.domain.post.dto.PostUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {

//    void createPost(PostCreateDto createDto);

    Long createPost(String email, PostCreateDto createDto, List<MultipartFile> files) throws IOException;

    PostReadDto getPost(Long postId);

    List<PostReadDto> getAllGuruPosts();

    List<PostReadDto> getAllPosts();

    List<PostReadDto> getPostsByMember(String memberNickname);

    List<PostReadDto> getLikePostsByMember(String memberNickname);

    PostReadDto updatePost(String email, Long postId, PostUpdateDto updateDto, List<MultipartFile> files) throws IOException;

    void deletePost(Long postId);

    void updateCntToRedis(final Long postId, String hashKey);

    Page<PostReadDto> searchPostWithPaging(Pageable pageable, String postCategory, String skill, String role, String searchType, String searchText);

    PostReadDto addPostLike(String email, Long postId);

    List<PostReadDto> getPostsByLoginMember(String email);

    List<PostReadDto> getLikePostsByLoginMember(String email);

    List<PostReadDto> getPostsByMemberNickname(String memberNickname);

    List<PostReadDto> getLikePostsByMemberNickname(String memberNickname);

}
