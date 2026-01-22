package com.example.phoenixcodecrafterproject.repository;
import com.example.phoenixcodecrafterproject.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Serializable> {

    //  Find posts by user (JPQL)
    @Query("SELECT p FROM Post p WHERE p.user.id = :userId")
    List<Post> findPostsByUserId(@Param("userId") Long userId);

    //  Search posts by keyword (JPQL)
    @Query("""
        SELECT p FROM Post p
        WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<Post> searchByKeyword(@Param("keyword") String keyword);

    //  Posts created in last 7 days (JPQL)
    @Query("SELECT p FROM Post p WHERE p.createdDate >= :date")
    List<Post> findPostsCreatedAfter(@Param("date") LocalDateTime date);

    @Query("SELECT p FROM Post p JOIN FETCH p.user")
    List<Post> findAllWithUser();


}