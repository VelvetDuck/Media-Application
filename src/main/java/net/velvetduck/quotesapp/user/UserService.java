package net.velvetduck.quotesapp.user;

import net.velvetduck.quotesapp.post.Post;
import net.velvetduck.quotesapp.post.PostRepository;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.Validate;

import javax.xml.validation.Validator;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;


    private final Logger logger = Logger.getLogger("userServiceLogger");

    public void save(User user){
        String encodedPassword = new BCryptPasswordEncoder()
                .encode(user.getPassword());

        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public void addPost(User user, Post post){
        long userId = user.getId();

        post.setUser(user);

    }

    public List<User> getAllUsers(){
        return Collections.unmodifiableList(userRepository.findAll());
    }
}
