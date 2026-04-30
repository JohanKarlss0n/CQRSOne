package com.kth.snomos.backend.Repository.Interface;

import com.kth.snomos.backend.Entity.Festival;
import com.kth.snomos.backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM festival_user" , nativeQuery = true)
    List<User> getAllUsers();

    @Query(value = "SELECT * FROM festival_user where username = :val1 AND password = :val2", nativeQuery = true)
    User rightPassword(@Param("val1") String name, @Param("val2") String password);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM festival_user WHERE username = :val)" , nativeQuery = true)
    boolean userExists(@Param("val") String username);

    @Query(value = "SELECT * FROM festival f JOIN booking b ON f.festival_id = b.festivalid " +
            "WHERE b.userid = :userid ORDER BY festival_date, festival_name", nativeQuery = true)
    List<Festival> findBookingsByUser (@Param("userid") long userid);

    @Query(value = "SELECT email FROM festival_user WHERE userid = :val", nativeQuery = true)
    String getEmail(@Param("val") int userid);

    @Modifying
    @Query(value = "UPDATE festival_user SET email = :val1 WHERE userid = :val2",nativeQuery = true)
    void updateEmail(@Param("val1") String email, @Param("val2") int id);

    @Modifying
    @Query(value = "DELETE FROM festival_user WHERE userid = :val", nativeQuery = true)
    void deleteUser(@Param("val") int id);
}