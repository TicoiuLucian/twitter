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

import java.util.Collection;
import java.util.List;

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
}
