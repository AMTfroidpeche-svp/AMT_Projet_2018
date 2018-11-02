package ch.heigvd.amt.projet.services;

import ch.heigvd.amt.projet.model.User;

import java.util.UUID;

public interface UserDAOLocal {

    public boolean addUser(User user);

}
