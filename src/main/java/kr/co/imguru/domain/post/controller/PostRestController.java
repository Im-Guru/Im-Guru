package kr.co.imguru.domain.post.controller;

import jakarta.validation.Valid;
import kr.co.imguru.domain.post.dto.PostCreateDto;
import kr.co.imguru.domain.post.dto.PostReadDto;
import kr.co.imguru.domain.post.dto.PostUpdateDto;
import kr.co.imguru.domain.post.service.PostService;
import kr.co.imguru.global.model.ResponseFormat;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostRestController {

    private final PostService postService;

    @PostMapping("/post")
    public ResponseFormat<Void> createPost(@RequestBody @Valid PostCreateDto createDto) {
        postService.createPost(createDto);

        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    @GetMapping("/post/{postId}")
    public ResponseFormat<PostReadDto> readPost(@PathVariable Long postId) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postService.getPost(postId));
    }

    @GetMapping("/post/guru")
    public ResponseFormat<List<PostReadDto>> readAllGuruPosts() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postService.getAllGuruPosts());
    }

    @GetMapping("/post/all")
    public ResponseFormat<List<PostReadDto>> readAllPosts() {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postService.getAllPosts());
    }

    @GetMapping("/post/wrtier/{memberNickname}")
    public ResponseFormat<List<PostReadDto>> readPostsByMember(@PathVariable String memberNickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postService.getPostsByMember(memberNickname));
    }

    @PutMapping("/post/{postId}")
    public ResponseFormat<PostReadDto> updatePost(@PathVariable Long postId,
                                                  @RequestBody @Valid PostUpdateDto updateDto) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postService.updatePost(postId, updateDto));
    }

    @DeleteMapping("/post/{postId}")
    public ResponseFormat<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);

        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }
}
