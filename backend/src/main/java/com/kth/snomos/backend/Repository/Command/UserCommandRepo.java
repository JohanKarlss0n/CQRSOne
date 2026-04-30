package com.kth.snomos.backend.Repository.Command;

import com.kth.snomos.backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserCommandRepo extends JpaRepository<User,Long> {

    @Modifying
    @Query(value = "UPDATE festival_user SET email = :val1 WHERE userid = :val2",nativeQuery = true)
    void updateEmail(@Param("val1") String email, @Param("val2") int id);

    @Modifying
    @Query(value = "DELETE FROM festival_user WHERE userid = :val", nativeQuery = true)
    void deleteUser(@Param("val") int id);
}
