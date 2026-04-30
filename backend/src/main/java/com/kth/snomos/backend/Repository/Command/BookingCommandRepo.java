package com.kth.snomos.backend.Repository.Command;

import com.kth.snomos.backend.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingCommandRepo extends JpaRepository<Booking,Long> {
}
