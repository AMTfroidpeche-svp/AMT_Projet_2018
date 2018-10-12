package ch.heigvd.amt.projet.business;
import ch.heigvd.amt.projet.model.User;

public class InfoChecker {

    public boolean checkinfo(User u){
        return !u.getUsername().isEmpty() && !u.getPassword().isEmpty() && checkMail(u.getMail()));
    }

    public boolean checkNotEmpty (String s){
        return !s.isEmpty();
    }

    public boolean checkMail (String s){
        return s.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" );
    }

}
