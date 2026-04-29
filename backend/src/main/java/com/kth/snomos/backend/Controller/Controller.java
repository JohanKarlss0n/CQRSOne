package com.kth.snomos.backend.Controller;

import com.kth.snomos.backend.Entity.*;
import com.kth.snomos.backend.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

/**
 * REST Controller that handles endpoints for User, Festival, Booking, Artist and Admin operations.
 * Provides APIs to create, retrieve, update and delete related entities in the system.
 * @author Max Masuch
 * @author Ismail Mohammed
 * @author Johan Karlsson
 * @author Elias Almlöf
 * @author Milan Hatami
 */

@CrossOrigin
@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private FestivalService festivalService;

    /**
     * Part of the REST controller that handles endpoints for User.
     * Provides APIs to create, retrieve, update and delete user entities in the system.
     */
    ////////////////////////////////////User//////////////////
    @PostMapping("/user/save")
    public String postUser(@RequestBody User user) {
        if(festivalService.userExists(user.getUsername())) {
            return "Username-Taken";
        }
        try {
            return festivalService.saveUser(user);
        } catch (IllegalArgumentException e) {
            return "Error-Email";
        }
    }

    @PutMapping("/user/changeEmail/{userid}/{email}")
    public String changeEmail(@PathVariable("userid") int userid, @PathVariable("email") String email) {
        try {
            Email emailObj = new Email(email);
            festivalService.changeUserEmail(emailObj.getEmail(), userid);
            return "Updated";
        } catch (IllegalArgumentException e) {
            return "Error";
        }
    }

    @GetMapping("/user/check/{username}")
    public boolean userExists(@PathVariable String username) {
        return festivalService.userExists(username);
    }

    @GetMapping("/user/findall")
    public List<User> getAllUsers() {
        return festivalService.findAllUsers();
    }

    @GetMapping("/user/login/{username}/{password}")
    public long findByName(@PathVariable String username, @PathVariable String password) {
        return festivalService.login(username, password);
    }

    @GetMapping("/user/getEmail/{userId}")
    public String getEmail(@PathVariable int userId) {
        return festivalService.getUserEmail(userId);
    }

    @DeleteMapping("/user/delete/{userid}")
    public void deleteUser(@PathVariable int userid) {
        festivalService.deleteUser(userid);
    }


    /**
     * Part of the controller that handles endpoints for Festival.
     * Provides APIs to create, retrieve, update and delete festival entities in the system.
     */
    ////////////////////////////////////Festival//////////////
    @PostMapping("/festival/save")
    public void postFestival(@RequestBody Festival festival) {
        festivalService.saveFestival(festival);
    }

    @PostMapping("/festival/update/{festivalId}")
    public void addArtistsToFestival(@PathVariable long festivalId, @RequestBody Festival festival) {
        festivalService.addArtistsToFestival(festivalId, festival.getArtists());
        festivalService.updateFestivalDescription(festivalId, festival.getFestivalDescription());
    }

    @PutMapping("/festival/update/description/{festivalId}")
    public void updateFestivalDescription(@PathVariable long festivalId, @RequestBody String description) {
        festivalService.updateFestivalDescription(festivalId, description);
    }

    @PutMapping("/festival/updateUrl/{festivalId}")
    public void updateFestivalUrl(@PathVariable long festivalId, @RequestBody String url) {
        festivalService.updateFestivalURL(festivalId, url);
    }

    @GetMapping("/festival/findbyid/{festivalId}")
    public Festival getFestivalById(@PathVariable long festivalId) {
        return festivalService.findFestivalById(festivalId);
    }

    @GetMapping("/festival/getartists/{festivalId}")
    public List<Artist> getArtistsByFestivalId(@PathVariable long festivalId) {
        return festivalService.findFestivalById(festivalId).getArtists();
    }

    @GetMapping("/festival/findbyname/{name}")
    public List<Festival> findFestivalByName(@PathVariable String name) {
        return festivalService.findFestivalByName(name);
    }

    @GetMapping("/festival/findbydate/{date}")
    public List<Festival> findFestivalByDate(@PathVariable LocalDate date) {
        return festivalService.findFestivalByDate(date);
    }

    @GetMapping("/festival/findbylocation/{location}")
    public List<Festival> findFestivalByLocation(@PathVariable String location) {
        return festivalService.findFestivalByLocation(location);
    }

    @GetMapping("/festival/findbyartist/{artist}")
    public List<Festival> findFestivalByArtist(@PathVariable String artist) {
        return festivalService.findFestivalByArtist(artist);
    }

    @GetMapping("/festival/findall")
    public List<Festival> getAllFestivals() {
        return festivalService.findAllFestivals();
    }

    @GetMapping("/festival/upcoming")
    public List<Festival> getUpcomingFestivals() {
        return festivalService.getUpcomingFestivals();
    }

    @DeleteMapping("/festival/delete/{festivalId}")
    public void deleteFestival(@PathVariable long festivalId) {
        festivalService.deleteFestival(festivalId);
    }

    /**
     * Part of the REST controller that handles endpoints for Booking.
     * Provides APIs to create and retrieve booking entities in the system.
     */
    ////////////////////////////////////Booking///////////////
    @PostMapping("/booking/{festivalID}/{userID}")
    public String postBooking(@PathVariable long festivalID, @PathVariable long userID) {
        User user = festivalService.findUserById(userID);
        Festival festival = festivalService.findFestivalById(festivalID);
        if (user == null) {
            return "Null-User";
        } else if (festival == null) {
            return "Null-Festival";
        }
        return festivalService.saveBooking(new Booking(user, festival));
    }

    @GetMapping("/booking/{userId}")
    public List<Festival> getBookings(@PathVariable long userId) {
        return festivalService.getBookingsByUser(userId);
    }

    /**
     * Part of the REST controller that handles endpoints for Artist.
     * Provides APIs to create, retrieve, update and delete artist entities in the system.
     */
    ////////////////////////////////////Artist////////////////
    @PostMapping("/artist/save")
    public String postArtist(@RequestBody Artist artist) {
        if (festivalService.artistExists(artist.getArtist_name())) {
            return "Artist-Exists";
        }
        festivalService.saveArtist(artist);
        return "Artist-Saved";
    }

    @PutMapping("/artist/updateage")
    public void updateArtistAge(@RequestBody Artist artist) {
        festivalService.updateArtistAge(artist.getArtist_name(),artist.getAge());
    }

    @GetMapping("/artist/findall")
    public List<Artist> findAllArtists() {
        return festivalService.findAllArtists();
    }

    @GetMapping("/artist/getbyname/{name}")
    public Artist getArtistByName(@PathVariable String name) {
        return festivalService.findArtistByName(name);
    }

    @GetMapping("/artist/exist/{name}")
    public boolean existArtist(@PathVariable String name) {
        return festivalService.artistExists(name);
    }

    @DeleteMapping("/artist/delete/{artistName}")
    public void deleteArtist(@PathVariable String artistName) {
        festivalService.deleteArtist(artistName);
    }

    /**
     * Part of the REST controller that handles endpoints for Admin.
     * Provides APIs to create, retrieve and delete admin entities in the system.
     */
    /// //////////////////////Admin/////////////////
    @PostMapping("/admin/save")
    public String addAdmin(@RequestBody Admin admin) {
        try {
            return festivalService.saveAdmin(admin);
        } catch (IllegalArgumentException e) {
            return "Error-Email";
        }
    }

    @GetMapping("/admin/findByID/{adminID}")
    public Admin findAdminById(@PathVariable long adminID) {
        return festivalService.findAdminById(adminID);
    }

    @DeleteMapping("/admin/delete/{adminId}")
    public void deleteAdmin(@PathVariable long adminId) {
        festivalService.deleteAdmin(adminId);
    }
}