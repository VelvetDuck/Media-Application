package net.velvetduck.quotesapp.post;

import lombok.Builder;
import lombok.Data;
import net.velvetduck.quotesapp.comment.PostComment;
import net.velvetduck.quotesapp.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "app_posts")
@Data
public class Post {

    public Post(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String postTitle;
    private String postDescription;
    private String postName;

    @Lob @Basic(fetch = FetchType.LAZY)
    private String postContext;

    private PostType postType;

    @ManyToOne
    private User user;

    @Lob
    private byte[] image;

    @OneToMany(targetEntity = PostComment.class)
    private List<PostComment> comments;
}
