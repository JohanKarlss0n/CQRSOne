package com.kth.snomos.backend.Repository.Query;

import com.kth.snomos.backend.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingQueryRepo extends JpaRepository<Booking, Long> {
}
