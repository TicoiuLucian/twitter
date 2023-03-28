package ro.itschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.itschool.service.SpringUserService;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class SpringUserController {

    private final SpringUserService myUserService;


    @GetMapping
    public String getAllUsers(Model model, String keyword) {
        model.addAttribute("users", myUserService.searchUser(keyword));
        return "users";
    }

    @RequestMapping(value = "/follow/{id}")
    public String followUser(@PathVariable Integer id) {
        myUserService.followUser(id);
        return "redirect:/users";
    }

    @GetMapping(value = "/followed")
    public String getFollowedUsers(Model model) {
        model.addAttribute("followedUsers", myUserService.getFollowedUsers());
        return "followed-users";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        myUserService.deleteById(id);
        return "redirect:/users";
    }

}

//TODO disable follow for logged in user
//TODO add more users to startUp class
//TODO new column with unfollow + method in controller
