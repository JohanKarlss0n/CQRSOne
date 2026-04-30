package com.kth.snomos.backend.Service;

import com.kth.snomos.backend.Entity.*;
import com.kth.snomos.backend.Repository.Command.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandService {

    @Autowired
    private AdminCommandRepo adminCommandRepo;
    @Autowired
    private ArtistCommandRepo artistCommandRepo;
    @Autowired
    private BookingCommandRepo bookingCommandRepo;
    @Autowired
    private FestivalCommandRepo festivalCommandRepo;
    @Autowired
    private UserCommandRepo userCommandRepo;

    public void saveFestival(Festival festival) {
        festivalCommandRepo.save(festival);
    }

    public void saveArtist(Artist artist) {
        artistCommandRepo.save(artist);
    }

    public void addArtistsToFestival(long festivalId, List<Artist> artistList) {
        Festival festival = festivalCommandRepo.findById(festivalId).orElseThrow();
        for (Artist specificArtist : artistList) {
            Artist currentArtist = artistCommandRepo.findById(specificArtist.getArtist_name()).orElseThrow();
            festival.getArtists().add(currentArtist);
            currentArtist.getFestivals().add(festival);
            festivalCommandRepo.save(festival);
        }
    }

    @Transactional
    public void deleteFestival(long festivalId) {
        festivalCommandRepo.deleteById(festivalId);
    }

    @Transactional
    public void deleteArtist(String artistName) {
        artistCommandRepo.deleteArtistByName(artistName);
    }

    @Transactional
    public void updateFestivalDescription(Long festivalId, String description) {
        festivalCommandRepo.updateFestivalDescription(festivalId, description);
    }

    @Transactional
    public void updateFestivalURL(Long festivalId, String url) {
        festivalCommandRepo.updateFestivalURL(festivalId, url);
    }

    @Transactional
    public void updateArtistAge(String artistName, int age){
        artistCommandRepo.updateArtistAge(artistName, age);
    }

    public String saveBooking (Booking booking) {
        if (booking.getFestival().getTicketsLeft() <= 0) {
            return "No tickets left";
        }
        booking.getFestival().setTicketsLeft(booking.getFestival().getTicketsLeft() - 1);
        saveFestival(booking.getFestival());
        bookingCommandRepo.save(booking);
        return "Booking saved";
    }

    public String saveUser(User user) {
        userCommandRepo.save(user);
        return "Success";
    }

    public String saveAdmin(Admin admin) {
        adminCommandRepo.save(admin);
        return "Success";
    }

    @Transactional
    public void changeUserEmail(String email, int id) {
        userCommandRepo.updateEmail(email, id);
    }

    @Transactional
    public void deleteUser(int id){
        userCommandRepo.deleteUser(id);
    }

    @Transactional
    public void deleteAdmin(long id){
        adminCommandRepo.deleteById(id);
    }
}
