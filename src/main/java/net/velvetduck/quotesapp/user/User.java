package net.velvetduck.quotesapp.user;

import lombok.Data;
import net.velvetduck.quotesapp.post.Post;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    public User(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
     private Long id;

    private String displayName;
    private String username;
    private String password;
    private String emailAddress;

    private String imageProfilePhotoUrl;

    private boolean enabled;
    private boolean nonExpired;
    private boolean credentialsNonExpired;
    private boolean accountNonLocked;


    private String roles;

    private boolean showGeneratedPostsOrNot;
}
