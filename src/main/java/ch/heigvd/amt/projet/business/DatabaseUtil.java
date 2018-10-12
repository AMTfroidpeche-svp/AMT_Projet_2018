package ch.heigvd.amt.projet.business;

import ch.heigvd.amt.projet.model.User;

import java.sql.*;

public class DatabaseUtil {

    private static Connection connection;
    public void initConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/amt_project?user=root&password=root";
        connection = DriverManager.getConnection(url);
    }

    /**
     * Ajoute un utilisateur dans la base de données s'il n'est pas déjà présent.
     *
     * @param u  nom d'utilisateur
     * @return 0,       l'utilisateur est déjà présent dans la base de données ou erreur
     *         1,       l'utilisateur est ajouté à la base de données
     */
    public static int addUserIfNotExists(User u) {
        String sql = "SELECT ID FROM users WHERE ID = ?;";
        ResultSet resultSet = null;
        int result = 0;
        PreparedStatement preparedStatement    = null;
        PreparedStatement preparedStatementAdd = null;

        try {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, u.getUsername());
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                String sqlAdd = "INSERT INTO users VALUES(?,?,?,?,?,?);";
                preparedStatementAdd = connection.prepareStatement(sqlAdd);
                preparedStatementAdd.setString(1, u.getUsername());
                preparedStatementAdd.setString(2, u.getPassword());
                preparedStatementAdd.setString(3, u.getMail());
                preparedStatementAdd.setInt(4, u.getPermissionsLevel());
                preparedStatementAdd.setInt(5, u.getIDQuestion());
                preparedStatementAdd.setString(6, u.getResponseQuestion());
                preparedStatementAdd.executeUpdate();

                result = 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(preparedStatementAdd != null && preparedStatement != null) {
                    preparedStatementAdd.close();
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static Connection getConnection() {
        return connection;
    }
}
