package net.velvetduck.quotesapp.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>{

    @Query("SELECT user FROM User user WHERE user.id = :userId ")
    User findUserById(@Param("userId") long userId);

    @Query("SELECT user FROM User user WHERE user.username = :username")
    User findUserByUsername(@Param("username") String username);

    @Query("SELECT user FROM User user WHERE user.emailAddress = :email")
    User findUserByEmail(@Param("email") String email);


    @Modifying
    @Query("UPDATE User user set user.displayName = :displayName WHERE user.id = :userId")
    void updateUserDisplayName(@Param("displayName") String displayName, @Param("userId") long userId);

    @Modifying
    @Query("UPDATE User user set user.imageProfilePhotoUrl = :photoUrl WHERE user.id = :userId")
    void updateUserPicturePhoto(@Param("photoUrl") String photoUrl, @Param("userId") long userId);

    @Modifying
    @Query("DELETE User user WHERE user.id = :userId")
    void deleteUser(@Param("userId") long userId);


}
