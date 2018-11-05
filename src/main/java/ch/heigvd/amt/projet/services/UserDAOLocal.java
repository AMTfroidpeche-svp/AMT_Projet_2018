package ch.heigvd.amt.projet.services;

import ch.heigvd.amt.projet.model.User;

import java.util.UUID;

public interface UserDAOLocal {

    public boolean addUser(User user);

    public User getUser();

    public boolean checkPassword(String email, String password);

    public boolean changePermissions(String email, int newPermissionLevel);

    public boolean changePassword(String email, String newPassword);

    public boolean resetPassword(String email);



}
