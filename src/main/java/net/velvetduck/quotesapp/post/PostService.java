package net.velvetduck.quotesapp.post;

import net.velvetduck.quotesapp.user.User;
import net.velvetduck.quotesapp.user.UserRepository;
import net.velvetduck.quotesapp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class PostService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    /**
     *
     * @param userReferenceId a user id to the reference.
     * @param otherwiseResult another result, when list will be empty, or null.
     * @return a list of posts that were made by {@link User}
     */
    public List<Post> getAllPostByUserReferenceId(long userReferenceId, List<Post> otherwiseResult) {
        User findUserById = userRepository.findUserById(userReferenceId);

        return postRepository.findAll()
                .stream()
                .filter(post -> post.getUser().equals(findUserById))
                .filter(post -> post.getUser() != null)
                .collect(Collectors.toUnmodifiableList());
    }
}
