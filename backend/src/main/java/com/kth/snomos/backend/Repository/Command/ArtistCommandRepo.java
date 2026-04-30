package com.kth.snomos.backend.Repository.Command;

import com.kth.snomos.backend.Entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArtistCommandRepo extends JpaRepository<Artist,String> {
    @Modifying
    @Query(value = "UPDATE artist SET age = :age WHERE artist_name = :name",nativeQuery = true)
    void updateArtistAge(@Param("name") String name, @Param("age") int age);

    @Modifying
    @Query(value = "DELETE FROM artist WHERE artist_name = :artistName",nativeQuery = true)
    void deleteArtistByName(@Param("artistName") String artistName);
}
