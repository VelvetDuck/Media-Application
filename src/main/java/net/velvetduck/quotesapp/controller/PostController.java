package net.velvetduck.quotesapp.controller;

import net.velvetduck.quotesapp.post.Post;
import net.velvetduck.quotesapp.post.PostRepository;
import net.velvetduck.quotesapp.post.PostService;
import net.velvetduck.quotesapp.post.PostType;
import net.velvetduck.quotesapp.user.SpringUserDetails;
import net.velvetduck.quotesapp.user.User;
import net.velvetduck.quotesapp.user.UserRepository;
import net.velvetduck.quotesapp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.Validate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    private static final Logger postControllerLogger = Logger.getLogger("PostController_Logger");

    @GetMapping("/createPost")
    public String viewCreatePostPage(@AuthenticationPrincipal SpringUserDetails springUserDetails, Model model){
        model.addAttribute("user", springUserDetails.getUser());

        Validate.notNull(springUserDetails.getUser(), "User cannot be null");

        return "editor/PostCreate";
    }

    @GetMapping("/uploadPost")
    public String thenUploadView(@AuthenticationPrincipal SpringUserDetails springUserDetails, Model model){
        model.addAttribute("post", new Post());
        model.addAttribute("user", springUserDetails.getUser());

        Validate.notNull(springUserDetails.getUser(), "User cannot be null");

        return "editor/PostLibrary";
    }

    @GetMapping("/viewPosts")
    public String viewAllPostsPage(
            @AuthenticationPrincipal SpringUserDetails springUserDetails,
            Model model){

        long userId = springUserDetails.getUser().getId();
        model.addAttribute("posts", postService.getAllPostByUserReferenceId(userId, new ArrayList<>()));
        model.addAttribute("user", springUserDetails.getUser());


        return "editor/PostLibrary";
    }

    @PostMapping("/uploadPost")
    public String uploadPost(
            @RequestParam("inputContextPost") String postContext,
            @RequestParam("inputDescriptionPost") String postDescription,
            @RequestParam("inputNamePost") String postName,
            @ModelAttribute Post post,
            @AuthenticationPrincipal SpringUserDetails springUserDetails,
            Model model) throws IOException {

        post.setPostName(postName);
        post.setPostTitle("");
        post.setPostType(PostType.OTHER);
        post.setPostDescription(postDescription);
        post.setComments(new ArrayList<>());
        post.setPostContext(postContext);



        Optional.ofNullable(postRepository.findPostByName(post.getPostName()))
                .ifPresentOrElse((p) -> postControllerLogger.info("Post" + p.getPostName() +  " is already found in database!"),
                        () -> {
                            userService.addPost(springUserDetails.getUser(), post);
                            postRepository.save(post);
                        });

        model.addAttribute("user", springUserDetails.getUser());

        long userId = springUserDetails.getUser().getId();
        model.addAttribute("posts", postService.getAllPostByUserReferenceId(userId, new ArrayList<>()));
        return "editor/PostLibrary";
    }
}
