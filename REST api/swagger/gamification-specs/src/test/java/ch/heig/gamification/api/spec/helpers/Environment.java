package ch.heig.gamification.api.spec.helpers;

import ch.heig.gamification.api.DefaultApi;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Olivier Kopp on 27.12.18.
 */
public class Environment {

    private DefaultApi api = new DefaultApi();

    public Environment() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader().getResourceAsStream("environment.properties"));
        String url = properties.getProperty("ch.heig.gamification.server.url");
        api.getApiClient().setBasePath(url);

    }

    public DefaultApi getApi() {
        return api;
    }


}
