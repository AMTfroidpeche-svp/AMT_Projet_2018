package ch.heigvd.amt.projet.business;

import ch.heigvd.amt.projet.model.Application;

public class ManageApp {

    public boolean createApp(String name, String appName, String description){
        Application App = new Application(name, appName, description);
        return false;
    }

}
