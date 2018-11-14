package ch.heigvd.amt.projet.services;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseUtils {

    public void cleanUp(PreparedStatement... preparedStatements){
        try {
            for (PreparedStatement p : preparedStatements) {
                if(p != null){
                    p.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
