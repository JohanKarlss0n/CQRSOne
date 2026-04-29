package com.kth.snomos.backend.Service;

import com.kth.snomos.backend.Entity.*;
import com.kth.snomos.backend.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

/**
 * Service class for handling business logic related to Festivals, Bookings, and Artists.
 * Provides methods to manage festival data, artist details, and user bookings.
 * @author Max Masuch
 * @author Ismail Mohammed
 * @author Johan Karlsson
 * @author Elias Almlöf
 * @author Milan Hatami
 */
@Service
public class FestivalService {

    @Autowired
    private FestivalRepo festivalRepo;
    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private ArtistRepo artistRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AdminRepo adminRepo;

    /**
     * Retrieves all upcoming festivals.
     *
     * @return List of upcoming festivals.
     */
    public List<Festival> findAllFestivals() {
        return festivalRepo.getAllUpcomingFestivals();
    }

    /**
     * Finds festivals by their name.
     *
     * @param name Festival name.
     * @return List of festivals matching the name.
     */
    public List<Festival> findFestivalByName(String name) {
        return festivalRepo.findFestivalByName(name);
    }

    /**
     * Finds festivals occurring on a specific date.
     *
     * @param date The date to search for.
     * @return List of festivals on that date.
     */
    public List<Festival> findFestivalByDate(LocalDate date) {
        return festivalRepo.findFestivalByDate(date);
    }

    /**
     * Finds festivals held at a specific location.
     *
     * @param location The location name.
     * @return List of festivals in that location.
     */
    public List<Festival> findFestivalByLocation(String location) {
        return festivalRepo.findFestivalByLocation(location);
    }

    /**
     * Finds festivals where a specific artist is performing.
     *
     * @param artist Artist name.
     * @return List of festivals that include the artist.
     */
    public List<Festival> findFestivalByArtist(String artist) {
        return festivalRepo.findFestivalByArtist(artist);
    }

    /**
     * Retrieves upcoming festivals sorted appropriately.
     *
     * @return List of upcoming festivals.
     */
    public List<Festival> getUpcomingFestivals() {
        return festivalRepo.getUpComingFestivals();
    }

    /**
     * Retrieves all artists in the system.
     *
     * @return List of all artists.
     */
    public List<Artist> findAllArtists(){
        return artistRepo.findAll();
    }

    /**
     * Finds a festival by its unique ID.
     *
     * @param id The festival ID.
     * @return The festival if found, otherwise throws exception.
     */
    public Festival findFestivalById(Long id) {
        return festivalRepo.findById(id).orElseThrow();
    }

    /**
     * Finds an artist by their name, if they exist.
     *
     * @param name Artist's name.
     * @return Artist object if found, otherwise null.
     */
    public Artist findArtistByName(String name) {
        return artistRepo.existsByNameLike(name) ? artistRepo.findArtistByName(name) : null;
    }

    /**
     * Checks if an artist with the given name exists.
     *
     * @param name Artist's name.
     * @return true if the artist exists, false otherwise.
     */
    public boolean artistExists(String name) {
        return artistRepo.existsByName(name);
    }

    /**
     * Saves a new festival to the repository.
     *
     * @param festival Festival object to be saved.
     */
    public void saveFestival(Festival festival) {
        festivalRepo.save(festival);
    }

    /**
     * Saves a new artist to the repository.
     *
     * @param artist Artist object to be saved.
     */
    public void saveArtist(Artist artist) {
        artistRepo.save(artist);
    }

    /**
     * Adds a list of artists to an existing festival.
     *
     * @param festivalId ID of the festival.
     * @param artistList List of artists to add.
     */
    public void addArtistsToFestival(long festivalId, List<Artist> artistList) {
        Festival festival = festivalRepo.findById(festivalId).orElseThrow();
        for (Artist specificArtist : artistList) {
            Artist currentArtist = artistRepo.findById(specificArtist.getArtist_name()).orElseThrow();
            festival.getArtists().add(currentArtist);
            currentArtist.getFestivals().add(festival);
            festivalRepo.save(festival);
        }
    }

    /**
     * Deletes a festival by its ID.
     *
     * @param festivalId ID of the festival to delete.
     */
    @Transactional
    public void deleteFestival(long festivalId) {
        festivalRepo.deleteById(festivalId);
    }

    /**
     * Deletes an artist by their name.
     *
     * @param artistName Name of the artist to delete.
     */
    @Transactional
    public void deleteArtist(String artistName) {
        artistRepo.deleteArtistByName(artistName);
    }

    /**
     * Updates the description of a festival.
     *
     * @param festivalId ID of the festival.
     * @param description New description to be set.
     */
    @Transactional
    public void updateFestivalDescription(Long festivalId, String description) {
        festivalRepo.updateFestivalDescription(festivalId, description);
    }

    /**
     * Updates the URL of a festival.
     *
     * @param festivalId ID of the festival.
     * @param url New URL to be set.
     */
    @Transactional
    public void updateFestivalURL(Long festivalId, String url) {
        festivalRepo.updateFestivalURL(festivalId, url);
    }

    /**
     * Updates the age of a specific artist.
     *
     * @param artistName Name of the artist.
     * @param age New age to be set.
     */
    @Transactional
    public void updateArtistAge(String artistName, int age){
        artistRepo.updateArtistAge(artistName, age);
    }

    /**
     * Saves a booking for a user to a festival if tickets are available.
     * Reduces ticket count accordingly.
     *
     * @param booking Booking object containing user and festival information.
     * @return "No tickets left" if unavailable, otherwise "Booking saved".
     */
    public String saveBooking (Booking booking) {
        if (booking.getFestival().getTicketsLeft() <= 0) {
            return "No tickets left";
        }
        booking.getFestival().setTicketsLeft(booking.getFestival().getTicketsLeft() - 1);
        saveFestival(booking.getFestival());
        bookingRepo.save(booking);
        return "Booking saved";
    }
    /**
     * Saves a new user to the repository.
     *
     * @param user The user object to be saved.
     * @return "Success" after saving.
     */
    public String saveUser(User user) {
        userRepo.save(user);
        return "Success";
    }

    /**
     * Saves a new admin to the repository.
     *
     * @param admin The admin object to be saved.
     */
    public String saveAdmin(Admin admin) {

        adminRepo.save(admin);
        return "Success";
    }

    /**
     * Retrieves a list of all registered users.
     *
     * @return List of all users.
     */
    public List<User> findAllUsers() {
        return userRepo.getAllUsers();
    }

    /**
     * Finds a user by their unique ID.
     *
     * @param id The user ID.
     * @return User object if found, otherwise throws an exception.
     */
    public User findUserById(Long id) {
        return userRepo.findById(id).orElseThrow();
    }

    /**
     * Finds an admin by their unique ID.
     *
     * @param id The admin ID.
     * @return Admin object if found, otherwise throws an exception.
     */
    public Admin findAdminById(long id) {
        return adminRepo.findById(id).orElseThrow();
    }

    /**
     * Authenticates a user or admin based on credentials.
     *
     * @param name Username.
     * @param password Password.
     * @return User ID if user login is successful, -1 if password is incorrect,
     *         0 if admin login is successful, -2 if credentials do not match any user or admin.
     */
    public long login(String name, String password) {
        if(userRepo.userExists(name)){
            User user = userRepo.rightPassword(name,password);
            return user == null ? -1 : user.getUserId();
        }
        Admin admin = adminRepo.findAdminByUsernameAndPassword(name,password);
        return admin == null ? -2 : 0;
    }

    /**
     * Checks if a user with the given username exists.
     *
     * @param username Username to check.
     * @return true if the user exists, false otherwise.
     */
    public boolean userExists(String username) {
        return userRepo.userExists(username);
    }

    /**
     * Retrieves the email address of a user by their ID.
     *
     * @param userId ID of the user.
     * @return Email address of the user.
     */
    public String getUserEmail(int userId){
        return userRepo.getEmail(userId);
    }

    /**
     * Retrieves all bookings associated with a given user.
     *
     * @param id ID of the user.
     * @return List of festivals the user is booked for.
     */
    public List<Festival> getBookingsByUser (Long id) {
        return userRepo.findBookingsByUser(id);
    }

    /**
     * Updates a user's email address.
     *
     * @param email New email address.
     * @param id ID of the user.
     */
    @Transactional
    public void changeUserEmail(String email, int id) {
        userRepo.updateEmail(email, id);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id ID of the user to delete.
     */
    @Transactional
    public void deleteUser(int id){
        userRepo.deleteUser(id);
    }

    /**
     * Deletes an admin by their ID.
     *
     * @param id ID of the admin to delete.
     */
    @Transactional
    public void deleteAdmin(long id){
        adminRepo.deleteById(id);
    }
}