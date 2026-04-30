package com.kth.snomos.backend.Repository.Command;

import com.kth.snomos.backend.Entity.Festival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FestivalCommandRepo extends JpaRepository<Festival,Long> {
    @Modifying
    @Query(value = "UPDATE festival SET festival_description = :description WHERE festival_id = :festivalId", nativeQuery = true)
    void updateFestivalDescription(@Param("festivalId") long festivalId, @Param("description") String description);

    @Modifying
    @Query(value = "UPDATE festival SET imageURL = :url WHERE festival_id = :festivalID", nativeQuery = true)
    void updateFestivalURL(@Param("festivalID") long festivalID, @Param("url") String url);
}
