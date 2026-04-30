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
    private CommandService commandService;
    @Autowired
    private QueryService queryService;

    /**
     * Part of the REST controller that handles endpoints for User.
     * Provides APIs to create, retrieve, update and delete user entities in the system.
     */
    ////////////////////////////////////User//////////////////
    @PostMapping("/user/save")
    public String postUser(@RequestBody User user) {
        if(queryService.userExists(user.getUsername())) {
            return "Username-Taken";
        }
        try {
            return commandService.saveUser(user);
        } catch (IllegalArgumentException e) {
            return "Error-Email";
        }
    }

    @PutMapping("/user/changeEmail/{userid}/{email}")
    public String changeEmail(@PathVariable("userid") int userid, @PathVariable("email") String email) {
        try {
            Email emailObj = new Email(email);
            commandService.changeUserEmail(emailObj.getEmail(), userid);
            return "Updated";
        } catch (IllegalArgumentException e) {
            return "Error";
        }
    }

    @GetMapping("/user/check/{username}")
    public boolean userExists(@PathVariable String username) {
        return queryService.userExists(username);
    }

    @GetMapping("/user/findall")
    public List<User> getAllUsers() {
        return queryService.findAllUsers();
    }

    @GetMapping("/user/login/{username}/{password}")
    public long findByName(@PathVariable String username, @PathVariable String password) {
        return queryService.login(username, password);
    }

    @GetMapping("/user/getEmail/{userId}")
    public String getEmail(@PathVariable int userId) {
        return queryService.getUserEmail(userId);
    }

    @DeleteMapping("/user/delete/{userid}")
    public void deleteUser(@PathVariable int userid) {
        commandService.deleteUser(userid);
    }


    /**
     * Part of the controller that handles endpoints for Festival.
     * Provides APIs to create, retrieve, update and delete festival entities in the system.
     */
    ////////////////////////////////////Festival//////////////
    @PostMapping("/festival/save")
    public void postFestival(@RequestBody Festival festival) {
        commandService.saveFestival(festival);
    }

    @PostMapping("/festival/update/{festivalId}")
    public void addArtistsToFestival(@PathVariable long festivalId, @RequestBody Festival festival) {
        commandService.addArtistsToFestival(festivalId, festival.getArtists());
        commandService.updateFestivalDescription(festivalId, festival.getFestivalDescription());
    }

    @PutMapping("/festival/update/description/{festivalId}")
    public void updateFestivalDescription(@PathVariable long festivalId, @RequestBody String description) {
        commandService.updateFestivalDescription(festivalId, description);
    }

    @PutMapping("/festival/updateUrl/{festivalId}")
    public void updateFestivalUrl(@PathVariable long festivalId, @RequestBody String url) {
        commandService.updateFestivalURL(festivalId, url);
    }

    @GetMapping("/festival/findbyid/{festivalId}")
    public Festival getFestivalById(@PathVariable long festivalId) {
        return queryService.findFestivalById(festivalId);
    }

    @GetMapping("/festival/getartists/{festivalId}")
    public List<Artist> getArtistsByFestivalId(@PathVariable long festivalId) {
        return queryService.findFestivalById(festivalId).getArtists();
    }

    @GetMapping("/festival/findbyname/{name}")
    public List<Festival> findFestivalByName(@PathVariable String name) {
        return queryService.findFestivalByName(name);
    }

    @GetMapping("/festival/findbydate/{date}")
    public List<Festival> findFestivalByDate(@PathVariable LocalDate date) {
        return queryService.findFestivalByDate(date);
    }

    @GetMapping("/festival/findbylocation/{location}")
    public List<Festival> findFestivalByLocation(@PathVariable String location) {
        return queryService.findFestivalByLocation(location);
    }

    @GetMapping("/festival/findbyartist/{artist}")
    public List<Festival> findFestivalByArtist(@PathVariable String artist) {
        return queryService.findFestivalByArtist(artist);
    }

    @GetMapping("/festival/findall")
    public List<Festival> getAllFestivals() {
        return queryService.findAllFestivals();
    }

    @GetMapping("/festival/upcoming")
    public List<Festival> getUpcomingFestivals() {
        return queryService.getUpcomingFestivals();
    }

    @DeleteMapping("/festival/delete/{festivalId}")
    public void deleteFestival(@PathVariable long festivalId) {
        commandService.deleteFestival(festivalId);
    }

    /**
     * Part of the REST controller that handles endpoints for Booking.
     * Provides APIs to create and retrieve booking entities in the system.
     */
    ////////////////////////////////////Booking///////////////
    @PostMapping("/booking/{festivalID}/{userID}")
    public String postBooking(@PathVariable long festivalID, @PathVariable long userID) {
        User user = queryService.findUserById(userID);
        Festival festival = queryService.findFestivalById(festivalID);
        if (user == null) {
            return "Null-User";
        } else if (festival == null) {
            return "Null-Festival";
        }
        return commandService.saveBooking(new Booking(user, festival));
    }

    @GetMapping("/booking/{userId}")
    public List<Festival> getBookings(@PathVariable long userId) {
        return queryService.getBookingsByUser(userId);
    }

    /**
     * Part of the REST controller that handles endpoints for Artist.
     * Provides APIs to create, retrieve, update and delete artist entities in the system.
     */
    ////////////////////////////////////Artist////////////////
    @PostMapping("/artist/save")
    public String postArtist(@RequestBody Artist artist) {
        if (queryService.artistExists(artist.getArtist_name())) {
            return "Artist-Exists";
        }
        commandService.saveArtist(artist);
        return "Artist-Saved";
    }

    @PutMapping("/artist/updateage")
    public void updateArtistAge(@RequestBody Artist artist) {
        commandService.updateArtistAge(artist.getArtist_name(),artist.getAge());
    }

    @GetMapping("/artist/findall")
    public List<Artist> findAllArtists() {
        return queryService.findAllArtists();
    }

    @GetMapping("/artist/getbyname/{name}")
    public Artist getArtistByName(@PathVariable String name) {
        return queryService.findArtistByName(name);
    }

    @GetMapping("/artist/exist/{name}")
    public boolean existArtist(@PathVariable String name) {
        return queryService.artistExists(name);
    }

    @DeleteMapping("/artist/delete/{artistName}")
    public void deleteArtist(@PathVariable String artistName) {
        commandService.deleteArtist(artistName);
    }

    /**
     * Part of the REST controller that handles endpoints for Admin.
     * Provides APIs to create, retrieve and delete admin entities in the system.
     */
    /// //////////////////////Admin/////////////////
    @PostMapping("/admin/save")
    public String addAdmin(@RequestBody Admin admin) {
        try {
            return commandService.saveAdmin(admin);
        } catch (IllegalArgumentException e) {
            return "Error-Email";
        }
    }

    @GetMapping("/admin/findByID/{adminID}")
    public Admin findAdminById(@PathVariable long adminID) {
        return queryService.findAdminById(adminID);
    }

    @DeleteMapping("/admin/delete/{adminId}")
    public void deleteAdmin(@PathVariable long adminId) {
        commandService.deleteAdmin(adminId);
    }
}