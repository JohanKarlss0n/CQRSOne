package com.kth.snomos.backend.Repository.Query;

import com.kth.snomos.backend.Entity.Festival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FestivalQueryRepo extends JpaRepository<Festival,Long> {
    @Query(value = "SELECT * FROM festival WHERE festival_date >= CURRENT_DATE ORDER BY festival_date LIMIT 9", nativeQuery = true)
    List<Festival> getUpComingFestivals();

    @Query(value = "SELECT * FROM festival WHERE festival_date >= CURRENT_DATE ORDER BY festival_date", nativeQuery = true)
    List<Festival> getAllUpcomingFestivals();

    @Query(value = "SELECT * FROM festival WHERE LOWER(festival_name) LIKE LOWER(CONCAT('%', :name, '%')) "+
            "ORDER BY festival_date, festival_name", nativeQuery = true)
    List<Festival> findFestivalByName(@Param("name") String name);

    @Query(value = "SELECT * FROM festival WHERE festival_date = :date ORDER BY festival_name", nativeQuery = true)
    List<Festival> findFestivalByDate(@Param("date") LocalDate festival_date);

    @Query(value = "SELECT * FROM festival WHERE LOWER(festival_location) LIKE LOWER(CONCAT('%', :location, '%'))"+
            " ORDER BY festival_date, festival_name", nativeQuery = true)
    List<Festival> findFestivalByLocation(@Param("location") String location);

    @Query(value =  "SELECT DISTINCT f.* FROM festival f JOIN artist_festival af ON f.festival_id = af.festival_id " +
            "JOIN artist a ON a.artist_name = af.artist_name WHERE LOWER(a.artist_name) LIKE LOWER(CONCAT('%', :artist, '%'))" +
            "ORDER BY f.festival_date, f.festival_name", nativeQuery = true)
    List<Festival> findFestivalByArtist(@Param("artist") String artist);
}
