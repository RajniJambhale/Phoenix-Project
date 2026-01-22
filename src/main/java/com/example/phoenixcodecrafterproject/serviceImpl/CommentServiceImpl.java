package com.example.phoenixcodecrafterproject.serviceImpl;
import com.example.phoenixcodecrafterproject.model.Comment;
import com.example.phoenixcodecrafterproject.model.Post;
import com.example.phoenixcodecrafterproject.model.User;
import com.example.phoenixcodecrafterproject.repository.CommentRepository;
import com.example.phoenixcodecrafterproject.repository.PostRepository;
import com.example.phoenixcodecrafterproject.repository.UserRepository;
import com.example.phoenixcodecrafterproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public Comment addComment(Long postId, Long userId, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPost(post);
        comment.setUser(user);
        return commentRepository.save(comment);
    }
}
