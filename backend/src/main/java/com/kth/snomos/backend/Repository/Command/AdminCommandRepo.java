package com.kth.snomos.backend.Repository.Command;

import com.kth.snomos.backend.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminCommandRepo extends JpaRepository<Admin, Long> {
}
