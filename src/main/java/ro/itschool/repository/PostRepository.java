package ro.itschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.itschool.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "Select * from post where user_id = ?", nativeQuery = true)
    List<Post> findByUserId(Integer userId);


}
