package com.kth.snomos.backend.Repository.Query;

import com.kth.snomos.backend.Entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArtistQueryRepo extends JpaRepository<Artist,String> {
    @Query(value =  "SELECT * FROM artist WHERE LOWER(artist_name) LIKE LOWER(CONCAT(:val, '%'))",nativeQuery = true)
    Artist findArtistByName(@Param("val") String val);

    @Query(value =  "SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END FROM artist WHERE " +
                    "CHAR_LENGTH(:val) >= 3 AND LOWER(artist_name) LIKE LOWER(CONCAT(:val, '%'))",nativeQuery = true)
    boolean existsByNameLike(@Param("val") String val);

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END " +
            "FROM artist WHERE artist_name = :val",nativeQuery = true)
    boolean existsByName(@Param("val") String val);
}
