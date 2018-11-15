package ch.heigvd.amt.projet.services;

import ch.heigvd.amt.projet.model.User;

import java.util.List;

public interface UserDAOLocal {

    public boolean addUser(User user);

    public User getUser(String email);

    public List<User> getPageUser(int pageNumber);

    public User checkPassword(String email, String password);

    public boolean changePermissions(String email, int newPermissionLevel);

    public boolean changePassword(String email, String token, String newPassword);

    public boolean changePasswordWithoutToken(String email, String oldPassword, String newPassword);

    public boolean changePasswordAdmin(String email);

    public int RetrieveSecretQuestion(String email);

    public boolean resetPassword(String email, String Response);



}
