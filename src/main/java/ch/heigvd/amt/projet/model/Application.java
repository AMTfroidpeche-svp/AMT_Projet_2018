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

    public Application(String appOwner, String appName, String description, String API_TOKEN, String API_SECRET){
        this.appOwner = appOwner;
        this.appName = appName;
        this.description = description;
        this.API_TOKEN = API_TOKEN;
        this.API_SECRET = API_SECRET;
    }

    public String getAppOwner() {
        return appOwner;
    }

    public String getAppName() {
        return appName;
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

    public void setAppOwner(String appOwner) {
        this.appOwner = appOwner;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
