package ch.heigvd.amt.projet.business;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class AuthChecker {

    private static DatabaseUtil db = new DatabaseUtil();

    public static boolean checkPassword(String email, String password){
        try {
            db.initConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "SELECT hashpass FROM users WHERE ID = ?;";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement    = null;
        PreparedStatement preparedStatementAdd = null;

        try {
            preparedStatement = DatabaseUtil.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                return false;
            }
            else{
                resultSet.beforeFirst();
                try {
                    return Arrays.equals(resultSet.getBytes(0), sha2Generator(password));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
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
        return false;
    }

    public static byte[] sha2Generator(String password) throws NoSuchAlgorithmException {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] passwordBytes = password.getBytes();
        byte[] digest = sha256.digest(passwordBytes);
        return digest;
    }

}
