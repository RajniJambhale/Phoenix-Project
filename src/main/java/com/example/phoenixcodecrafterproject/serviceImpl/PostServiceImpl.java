package com.example.phoenixcodecrafterproject.serviceImpl;
import com.example.phoenixcodecrafterproject.dto.request.CreatePostRequest;
import com.example.phoenixcodecrafterproject.dto.request.UpdatePostRequest;
import com.example.phoenixcodecrafterproject.dto.response.PostDTO;
import com.example.phoenixcodecrafterproject.exception.BadRequestException;
import com.example.phoenixcodecrafterproject.exception.ResourceNotFoundException;
import com.example.phoenixcodecrafterproject.mapper.PostMapper;
import com.example.phoenixcodecrafterproject.model.Post;
import com.example.phoenixcodecrafterproject.model.User;
import com.example.phoenixcodecrafterproject.repository.PostRepository;
import com.example.phoenixcodecrafterproject.repository.UserRepository;
import com.example.phoenixcodecrafterproject.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {


    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    @Override
    public PostDTO createPost(CreatePostRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return PostMapper.toPostDTO(
                postRepository.save(PostMapper.toEntity(request, user)));
    }

    @Override
    public List<PostDTO> getAllPost() {
            List<Post> posts = postRepository.findAll();
            return posts.stream()
                    .map(PostMapper::toPostDTO)
                    .toList();
    }

    @Override
    public PostDTO getPostById(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post not found with id: " + id));
        return PostMapper.toPostDTO(post);
    }


    @Override
    public PostDTO updatePost(long id, UpdatePostRequest request) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post not found with id: " + id));
        String loggedInEmail =
                SecurityContextHolder.getContext().getAuthentication().getName();

        if (!existingPost.getUser().getEmail().equals(loggedInEmail)) {
            throw new AccessDeniedException("You can edit only your own post");
        }
        existingPost.setTitle(request.title());
        existingPost.setContent(request.content());
        PostMapper.updateEntity(existingPost, request);
        Post updatedPost = postRepository.save(existingPost);
        return PostMapper.toPostDTO(updatedPost);
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post not found with id: " + id));
        postRepository.delete(post);
    }

    @Override
    public List<PostDTO> getPostsByUser(Long userId) {
        List<Post> posts = postRepository.findPostsByUserId(userId);
        return posts.stream()
                .map(PostMapper::toPostDTO)
                .toList();
    }

    @Override
    public List<PostDTO> searchPosts(String keyword) {
        List<Post> posts = postRepository.searchByKeyword(keyword);
        return posts.stream()
                .map(PostMapper::toPostDTO)
                .toList();
    }

    @Override
    public List<PostDTO> postsLast7Days() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        List<Post> posts =  postRepository.findPostsCreatedAfter(sevenDaysAgo);
        return posts.stream()
                .map(PostMapper::toPostDTO)
                .toList();
    }

    @Override
    public Page<PostDTO> getAllPosts(Pageable pageable) {
        if (pageable.getPageNumber() < 0) {
            throw new BadRequestException("Page number cannot found");}
        return postRepository.findAll(pageable)
                .map(PostMapper::toPostDTO);
    }

}
