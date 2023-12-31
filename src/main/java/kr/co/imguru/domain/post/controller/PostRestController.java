package kr.co.imguru.domain.post.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kr.co.imguru.domain.post.dto.PostCreateDto;
import kr.co.imguru.domain.post.dto.PostReadDto;
import kr.co.imguru.domain.post.dto.PostUpdateDto;
import kr.co.imguru.domain.post.service.PostService;
import kr.co.imguru.global.auth.CustomUserDetails;
import kr.co.imguru.global.model.ResponseFormat;
import kr.co.imguru.global.model.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostRestController {

    private final PostService postService;

    @PostMapping(value = "/post")
    public ResponseFormat<Long> createPost(@AuthenticationPrincipal CustomUserDetails userDetails,
                                           @RequestBody @Valid PostCreateDto createDto) {

        Long postId = postService.createPost(userDetails.getUsername(), createDto);

        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postId);
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

    @GetMapping("/post/writer/{memberNickname}")
    public ResponseFormat<List<PostReadDto>> readPostsByMember(@PathVariable String memberNickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postService.getPostsByMember(memberNickname));
    }

    @GetMapping("/post/like/{memberNickname}")
    public ResponseFormat<List<PostReadDto>> readLikePostsByMember(@PathVariable String memberNickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postService.getLikePostsByMember(memberNickname));
    }

    @PatchMapping(value = "/post/{postId}")
    public ResponseFormat<PostReadDto> updatePost(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                  @PathVariable Long postId,
                                                  @RequestBody @Valid PostUpdateDto updateDto) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postService.updatePost(userDetails.getUsername(), postId, updateDto));
    }

    @DeleteMapping("/post/{postId}")
    public ResponseFormat<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);

        return ResponseFormat.success(ResponseStatus.SUCCESS_OK);
    }

    /**
     * Search List Post API + Paging default page=0, size=10
     * http://localhost:8080/api/v1/posts?page=0&size=10 -> 이런식으로 지정해서 사용도 가능
     *
     * @param pageable
     * @return
     */
    @GetMapping("/posts")
    public ResponseFormat<Page<PostReadDto>> getPagedPosts(@PageableDefault(page = 0, size = 10) Pageable pageable
            , @RequestParam(required = false) String postCategory, @RequestParam(required = false) String skill
            , @RequestParam(required = false) String role
            , @RequestParam(required = false) String searchType, @RequestParam(required = false) String searchText) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postService.searchPostWithPaging(pageable, postCategory, skill, role, searchType, searchText));
    }


    @PostMapping("/post/like/{postId}")
    public ResponseFormat<PostReadDto> addPostLike(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                   @PathVariable Long postId) {

        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postService.addPostLike(userDetails.getUsername(), postId));
    }


    @PostMapping("/posts/myWrite")
    public ResponseFormat<List<PostReadDto>> getPostsByLoginMember(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postService.getPostsByLoginMember(userDetails.getUsername()));
    }

    @PostMapping("/posts/myLike")
    public ResponseFormat<List<PostReadDto>> getLikePostsByLoginMember(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postService.getLikePostsByLoginMember(userDetails.getUsername()));
    }

    @PostMapping("/posts/member/{memberNickname}")
    public ResponseFormat<List<PostReadDto>> getPostsByMemberNickname(@PathVariable String memberNickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postService.getPostsByMemberNickname(memberNickname));
    }

    @PostMapping("/posts/like/member/{memberNickname}")
    public ResponseFormat<List<PostReadDto>> getLikePostsByMemberNickname(@PathVariable String memberNickname) {
        return ResponseFormat.successWithData(ResponseStatus.SUCCESS_OK, postService.getLikePostsByMemberNickname(memberNickname));
    }

}
