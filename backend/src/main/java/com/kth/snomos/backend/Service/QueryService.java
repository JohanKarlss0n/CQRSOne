package com.kth.snomos.backend.Service;

import com.kth.snomos.backend.Entity.Admin;
import com.kth.snomos.backend.Entity.Artist;
import com.kth.snomos.backend.Entity.Festival;
import com.kth.snomos.backend.Entity.User;
import com.kth.snomos.backend.Repository.Query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class QueryService {

    @Autowired
    private AdminQueryRepo adminQueryRepo;
    @Autowired
    private ArtistQueryRepo artistQueryRepo;
    @Autowired
    private BookingQueryRepo bookingQueryRepo;
    @Autowired
    private FestivalQueryRepo festivalQueryRepo;
    @Autowired
    private UserQueryRepo userQueryRepo;

    public List<Festival> findAllFestivals() {
        return festivalQueryRepo.getAllUpcomingFestivals();
    }

    public List<Festival> findFestivalByName(String name) {
        return festivalQueryRepo.findFestivalByName(name);
    }

    public List<Festival> findFestivalByDate(LocalDate date) {
        return festivalQueryRepo.findFestivalByDate(date);
    }

    public List<Festival> findFestivalByLocation(String location) {
        return festivalQueryRepo.findFestivalByLocation(location);
    }

    public List<Festival> findFestivalByArtist(String artist) {
        return festivalQueryRepo.findFestivalByArtist(artist);
    }

    public List<Festival> getUpcomingFestivals() {
        return festivalQueryRepo.getUpComingFestivals();
    }

    public List<Artist> findAllArtists(){
        return artistQueryRepo.findAll();
    }

    public Festival findFestivalById(Long id) {
        return festivalQueryRepo.findById(id).orElseThrow();
    }

    public Artist findArtistByName(String name) {
        return artistQueryRepo.existsByNameLike(name) ? artistQueryRepo.findArtistByName(name) : null;
    }

    public boolean artistExists(String name) {
        return artistQueryRepo.existsByName(name);
    }

    public List<User> findAllUsers() {
        return userQueryRepo.getAllUsers();
    }

    public User findUserById(Long id) {
        return userQueryRepo.findById(id).orElseThrow();
    }

    public Admin findAdminById(long id) {
        return adminQueryRepo.findById(id).orElseThrow();
    }

    public long login(String name, String password) {
        if(userQueryRepo.userExists(name)){
            User user = userQueryRepo.rightPassword(name,password);
            return user == null ? -1 : user.getUserId();
        }
        Admin admin = adminQueryRepo.findAdminByUsernameAndPassword(name,password);
        return admin == null ? -2 : 0;
    }

    public boolean userExists(String username) {
        return userQueryRepo.userExists(username);
    }

    public String getUserEmail(int userId){
        return userQueryRepo.getEmail(userId);
    }

    public List<Festival> getBookingsByUser (Long id) {
        return userQueryRepo.findBookingsByUser(id);
    }


}
