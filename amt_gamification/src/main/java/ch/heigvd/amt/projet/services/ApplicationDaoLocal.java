package ch.heigvd.amt.projet.services;

import ch.heigvd.amt.projet.model.Application;

import java.util.List;

public interface ApplicationDaoLocal {

    public boolean createApp(Application app);

    public boolean deleteApp(Application app);

    public boolean deleteApp(String APIToken, String appOwner);

    public boolean updateApp(Application app, String newName, String newDescription, String appOwner);

    public boolean updateApp(String APIToken, String newName, String newDescription, String appOwner);

    public List<Application> retrieveApp(String appOwner, int permissionLevel);

    public List<Application> retrieveApp(String appOwner);

    public Application getApp(String appTOKEN, String appOwner);
}
