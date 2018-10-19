package ch.heigvd.amt.projet.model;

import java.util.UUID;

public class Application {
    private String appOwner;
    private String appName;
    private String description;
    private final String API_TOKEN;
    private final String API_SECRET;

    public Application(String appOwner, String appName, String description){
        this.appOwner = appOwner;
        this.appName = appName;
        this.description = description;
        UUID uuid = UUID.randomUUID();
        API_TOKEN = uuid.toString();
        uuid = UUID.randomUUID();
        API_SECRET = uuid.toString();
    }

    public String getAppOwner() {
        return appOwner;
    }

    public String getDescription() {
        return description;
    }

    public String getAPI_TOKEN() {
        return API_TOKEN;
    }

    public String getAPI_SECRET() {
        return API_SECRET;
    }

}
