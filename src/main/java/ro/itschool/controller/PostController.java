package ro.itschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.itschool.service.PostService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping(value = "/all")
    public String getAllPosts(Model model) {
        model.addAttribute("posts", postService.getPostsFromFollowedUsers());
        return "post";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deletePost(@PathVariable Integer id) {
        postService.deleteById(id);
        return "redirect:/post/all";
    }
}
