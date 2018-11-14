package ch.heigvd.amt.projet.business;
import ch.heigvd.amt.projet.model.User;

public class InfoChecker {

    public boolean checkinfo(User u){
        return !u.getFirstName().isEmpty() && !u.getLastName().isEmpty() && !u.getPassword().isEmpty() && !u.getEmail().isEmpty() && !u.getResponseQuestion().isEmpty();
    }
}
