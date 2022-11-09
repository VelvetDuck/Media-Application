package net.velvetduck.quotesapp.post;

import net.velvetduck.quotesapp.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Modifying
    @Query("UPDATE Post post set post.postTitle = :title WHERE post.id = :id")
    void updatePostTitle(@Param("title") String title, @Param("id") int id);

    @Modifying
    @Query("UPDATE Post post set post.postDescription = :description WHERE post.id = :id")
    void updatePostDescription(@Param("description") String description, @Param("id") int id);

    @Query("SELECT post FROM Post post WHERE post.postName = :name")
    Post findPostByName(@Param("name") String name);

    @Modifying
    @Query("DELETE Post post WHERE post.id = :id ")
    void deletePort(@Param("id") int id);
}
