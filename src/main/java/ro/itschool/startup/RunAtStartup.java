package ro.itschool.startup;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ro.itschool.entity.Post;
import ro.itschool.entity.Role;
import ro.itschool.entity.SpringUser;
import ro.itschool.repository.PostRepository;
import ro.itschool.repository.RoleRepository;
import ro.itschool.service.SpringUserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RunAtStartup {


    private final RoleRepository roleRepository;

    private final SpringUserService springUserService;

    private final PostRepository postRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void insertRestaurantsIntoDB() {


        Role role = new Role("ROLE_USER");
        Role savedRole = roleRepository.save(role);

        role = new Role("ROLE_ADMIN");
        roleRepository.save(role);

        SpringUser springUser = new SpringUser();
        springUser.setPassword("pass");
        springUser.setEmail("user@email.com");
        springUser.setUsername("user");
        springUser.setFirstName("First name");
        springUser.setLastName("Last name");
        springUser.setRoles(Collections.singleton(savedRole));
        springUser.setEnabled(true);
        springUser.setAccountNonExpired(true);
        springUser.setAccountNonLocked(true);
        springUser.setCredentialsNonExpired(true);
        Set<Post> posts = Set.of(new Post(" user message post 1", LocalDateTime.now(), springUser),
                new Post("user message post 2", LocalDateTime.now(), springUser),
                new Post("user message post 3", LocalDateTime.now(), springUser));
        springUser.setPosts(posts);
        postRepository.saveAll(posts);
        springUserService.registerUser(springUser);

        SpringUser springUser2 = new SpringUser();
        springUser2.setPassword("pass");
        springUser2.setEmail("lticoiu@email.com");
        springUser2.setUsername("lticoiu");
        springUser2.setFirstName("Lucian");
        springUser2.setLastName("Ticoiu");
        springUser2.setRoles(Collections.singleton(savedRole));
        springUser2.setEnabled(true);
        springUser2.setAccountNonExpired(true);
        springUser2.setAccountNonLocked(true);
        springUser2.setCredentialsNonExpired(true);
        Set<Post> posts2 = Set.of(new Post(" lticoiu message post 1 user", LocalDateTime.now(), springUser2),
                new Post("lticoiu message post 2", LocalDateTime.now(), springUser2),
                new Post("lticoiu message post 3", LocalDateTime.now(), springUser2));
        springUser2.setPosts(posts2);
        postRepository.saveAll(posts2);
        springUserService.registerUser(springUser2);

        SpringUser springUser3 = new SpringUser();
        springUser3.setPassword("pass");
        springUser3.setEmail("pgirdea@email.com");
        springUser3.setUsername("pgirdea");
        springUser3.setFirstName("Paula");
        springUser3.setLastName("Girdea");
        springUser3.setRoles(Collections.singleton(savedRole));
        springUser3.setEnabled(true);
        springUser3.setAccountNonExpired(true);
        springUser3.setAccountNonLocked(true);
        springUser3.setCredentialsNonExpired(true);
        Set<Post> posts3 = Set.of(new Post(" pgirdea message post 1", LocalDateTime.now(), springUser3),
                new Post("pgirdea message post 2", LocalDateTime.now(), springUser3),
                new Post("pgirdea message post 3", LocalDateTime.now(), springUser3));
        springUser3.setPosts(posts3);
        postRepository.saveAll(posts3);
        springUserService.registerUser(springUser3);

    }
}
