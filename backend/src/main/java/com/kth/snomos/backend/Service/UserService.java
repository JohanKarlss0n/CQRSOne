package com.kth.snomos.backend.Service;

import com.kth.snomos.backend.Entity.Admin;
import com.kth.snomos.backend.Entity.Festival;
import com.kth.snomos.backend.Entity.User;
import com.kth.snomos.backend.Repository.Interface.AdminRepo;
import com.kth.snomos.backend.Repository.Interface.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service class responsible for handling user and admin operations.
 * It acts as a bridge between the controller layer and the data repository layer.
 */
@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AdminRepo adminRepo;

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