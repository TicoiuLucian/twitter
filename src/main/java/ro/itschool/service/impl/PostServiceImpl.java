package ro.itschool.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ro.itschool.entity.Post;
import ro.itschool.entity.SpringUser;
import ro.itschool.repository.PostRepository;
import ro.itschool.repository.SpringUserRepository;
import ro.itschool.service.PostService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final SpringUserRepository springUserRepository;

    @Override
    public List<Post> getPostsFromFollowedUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringUser loggedInUser = springUserRepository.findByUsername(authentication.getName());
        List<SpringUser> springUsers = springUserRepository.getFollowedUsers(loggedInUser.getId())
                .stream()
                .map(elem -> new SpringUser(
                        elem[0].toString(),
                        elem[1].toString(),
                        elem[2].toString(),
                        elem[3].toString(),
                        elem[4].toString()))
                .toList();
        return springUsers.stream()
                .map(user -> postRepository.findByUserId(user.getId()))
                .flatMap(Collection::stream)
                .toList();
    }

    @Override
    public void deleteById(Integer id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> getMyPosts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringUser loggedInUser = springUserRepository.findByUsername(authentication.getName());
        return postRepository.findByUserId(loggedInUser.getId());
    }

    @Override
    public void save(Post post) {
        post.setTimestamp(LocalDateTime.now());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringUser loggedInUser = springUserRepository.findByUsername(authentication.getName());
        post.setSpringUser(loggedInUser);
        postRepository.save(post);
    }

    @Override
    public void copyPost(Integer id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        optionalPost.ifPresent(p -> {
            Post post = new Post();
            post.setTimestamp(p.getTimestamp());
            post.setMessage(p.getMessage());
            save(post);
        });
    }

    @Override
    public List<Post> getPostsWithMention() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return postRepository.getPostsWithMention(authentication.getName());
    }
}
