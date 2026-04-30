package com.kth.snomos.backend.Repository.Interface;

import com.kth.snomos.backend.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminRepo extends JpaRepository<Admin, Long> {
    @Query(value = "SELECT * FROM admin_user WHERE username = :val1 AND password = :val2", nativeQuery = true)
    Admin findAdminByUsernameAndPassword(@Param("val1") String username, @Param("val2") String password);
}
