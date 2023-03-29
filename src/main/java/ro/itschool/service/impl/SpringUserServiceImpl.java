package ro.itschool.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ro.itschool.entity.Role;
import ro.itschool.entity.SpringUser;
import ro.itschool.repository.PostRepository;
import ro.itschool.repository.RoleRepository;
import ro.itschool.repository.SpringUserRepository;
import ro.itschool.service.SpringUserService;

import java.util.*;


@Service
public class SpringUserServiceImpl implements SpringUserService, UserDetailsService {

    @Autowired
    private SpringUserRepository springUserRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PostRepository postRepository;

    @Override
    public SpringUser registerUser(SpringUser receivedUser) { //received User from FE
        SpringUser springUser = new SpringUser(receivedUser);
        springUser.setPassword(new BCryptPasswordEncoder().encode(receivedUser.getPassword()));
        springUser.setPosts(receivedUser.getPosts());

        springUser.getRoles().forEach(role -> {
            final Role roleByName = roleRepository.findByName(role.getName());
            role.setId(roleByName.getId());
        });

        return springUserRepository.save(springUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SpringUser springUser = springUserRepository.findByUsername(username);
        List<GrantedAuthority> authorities = getUserAuthority(springUser.getRoles());
        return new SpringUser(
                springUser.getUsername(),
                springUser.getPassword(),
                springUser.isEnabled(),
                springUser.isAccountNonExpired(),
                springUser.isAccountNonLocked(),
                springUser.isCredentialsNonExpired(),
                authorities);

    }

    @Override
    public List<SpringUser> getAllUsers() {
        return springUserRepository.findAll();
    }

    @Override
    public List<SpringUser> searchUser(String keyword) {
        if (keyword == null)
            keyword = "";
        return springUserRepository.searchUser(keyword);
    }

    @Override
    public void followUser(Integer id) {
        Optional<SpringUser> toBeFollowed = springUserRepository.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringUser loggedInUser = springUserRepository.findByUsername(authentication.getName());
        toBeFollowed.ifPresent(user -> springUserRepository.insertIntoFollowTable(loggedInUser.getId(), user.getId()));
    }

    @Override
    public List<SpringUser> getFollowedUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringUser loggedInUser = springUserRepository.findByUsername(authentication.getName());
        return springUserRepository.getFollowedUsers(loggedInUser.getId())
                .stream()
                .map(elem -> new SpringUser(elem[0].toString(), elem[1].toString(), elem[2].toString(), elem[3].toString(), elem[4].toString()))
                .toList();
    }

    @Override
    public void deleteById(Integer id) {
        Optional<SpringUser> springUser = springUserRepository.findById(id);
        springUser.ifPresent(user -> {
            springUserRepository.deleteFollower(id);
            postRepository.deleteByUserId(user.getId());
            springUserRepository.deleteById(id);
        });
    }

    @Override
    public void unfollowUser(Integer id) {
        Optional<SpringUser> toBeFollowed = springUserRepository.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringUser loggedInUser = springUserRepository.findByUsername(authentication.getName());
        toBeFollowed.ifPresent(user -> springUserRepository.deleteFromFollowTable(loggedInUser.getId(), user.getId()));
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new ArrayList<>(roles);
    }
}
