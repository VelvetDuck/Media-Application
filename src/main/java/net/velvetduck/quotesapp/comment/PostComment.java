package net.velvetduck.quotesapp.comment;

import lombok.Data;
import org.hibernate.annotations.Tables;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "comments")
@Data
public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int postId;

    private int authorId;
    private String postContext;
    private boolean modified;

    private Date postSentDate;
    private Date modifiedDate;

    private int seenCounter;
}
