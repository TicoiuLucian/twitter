package ro.itschool.startup;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ro.itschool.entity.Post;
import ro.itschool.entity.Role;
import ro.itschool.entity.SpringUser;
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
        Set<Post> posts = Set.of(new Post(" user message post 1", LocalDateTime.now()),
                new Post("user message post 2", LocalDateTime.now()),
                new Post("user message post 3", LocalDateTime.now()));
        springUser.setPosts(posts);
        springUserService.registerUser(springUser);

        springUser.setPassword("pass");
        springUser.setEmail("lticoiu@email.com");
        springUser.setUsername("lticoiu");
        springUser.setFirstName("Lucian");
        springUser.setLastName("Ticoiu");
        springUser.setRoles(Collections.singleton(savedRole));
        springUser.setEnabled(true);
        springUser.setAccountNonExpired(true);
        springUser.setAccountNonLocked(true);
        springUser.setCredentialsNonExpired(true);
        posts = Set.of(new Post(" lticoiu message post 1", LocalDateTime.now()),
                new Post("lticoiu message post 2", LocalDateTime.now()),
                new Post("lticoiu message post 3", LocalDateTime.now()));
        springUser.setPosts(posts);
        springUserService.registerUser(springUser);

        springUser.setPassword("pass");
        springUser.setEmail("pgirdea@email.com");
        springUser.setUsername("pgirdea");
        springUser.setFirstName("Paula");
        springUser.setLastName("Girdea");
        springUser.setRoles(Collections.singleton(savedRole));
        springUser.setEnabled(true);
        springUser.setAccountNonExpired(true);
        springUser.setAccountNonLocked(true);
        springUser.setCredentialsNonExpired(true);
        posts = Set.of(new Post(" pgirdea message post 1", LocalDateTime.now()),
                new Post("pgirdea message post 2", LocalDateTime.now()),
                new Post("pgirdea message post 3", LocalDateTime.now()));
        springUser.setPosts(posts);
        springUserService.registerUser(springUser);

    }
}
