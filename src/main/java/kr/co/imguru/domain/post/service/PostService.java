package kr.co.imguru.domain.post.service;

import kr.co.imguru.domain.post.dto.PostCreateDto;
import kr.co.imguru.domain.post.dto.PostReadDto;
import kr.co.imguru.domain.post.dto.PostUpdateDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {

//    void createPost(PostCreateDto createDto);

    void createPost(PostCreateDto createDto, List<MultipartFile> files) throws IOException;

    PostReadDto getPost(Long postId);

    List<PostReadDto> getAllGuruPosts();

    List<PostReadDto> getAllPosts();

    List<PostReadDto> getPostsByMember(String memberNickname);

    PostReadDto addLikePostByMemberNickname(Long postId, String memberNickname);

    List<PostReadDto> getLikePostsByMember(String memberNickname);

    PostReadDto updatePost(Long postId, PostUpdateDto updateDto);

    void deletePost(Long postId);

    void updateCntToRedis(final Long postId, String hashKey);

}
