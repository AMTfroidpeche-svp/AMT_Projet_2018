package ch.heigvd.amt.projet.services;

import ch.heigvd.amt.projet.model.Application;

import java.util.List;

public interface ApplicationDaoLocal {

    public boolean createApp(Application app);

    public boolean deleteApp(Application app);

    public boolean updateApp(Application app, String newName, String newDescription);

    public List<Application> retrieveApp(String appOwner, int pageNumber, int permissionLevel);

    public List<Application> retrieveApp(String appOwner, int pageNumber);
}
