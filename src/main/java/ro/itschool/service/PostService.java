package ro.itschool.service;

import org.springframework.stereotype.Service;
import ro.itschool.entity.Post;

import java.util.List;

@Service
public interface PostService {


    List<Post> getPostsFromFollowedUsers();

    void deleteById(Integer id);
}
