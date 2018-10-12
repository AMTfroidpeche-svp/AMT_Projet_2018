package ch.heigvd.amt.projet.business;
import ch.heigvd.amt.projet.model.User;

public class InfoChecker {

    public boolean checkinfo(User u){
        return !u.getUsername().isEmpty() && !u.getPassword().isEmpty() && !u.getMail().isEmpty() && !u.getResponseQuestion().isEmpty();
    }
}
