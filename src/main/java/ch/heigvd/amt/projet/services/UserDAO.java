package ch.heigvd.amt.projet.services;

import ch.heigvd.amt.projet.business.CipherUtil;
import ch.heigvd.amt.projet.model.EmailUtility;
import ch.heigvd.amt.projet.model.User;

import javax.annotation.Resource;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.mail.MessagingException;
import javax.sql.DataSource;
import javax.ejb.Stateless;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class UserDAO extends DatabaseUtils implements UserDAOLocal {

    @Resource(lookup = "java:/amt_project")
    DataSource dataSource;

    private static final SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Override
    public boolean addUser(User user){
        String sql = "SELECT email FROM users WHERE email = ?;";
        ResultSet resultSet = null;
        boolean result = false;
        PreparedStatement preparedStatement    = null;
        PreparedStatement preparedStatementAdd = null;

        try (Connection connection = dataSource.getConnection()) {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, user.getEmail());
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                String sqlAdd = "INSERT INTO users(email, hashPass, firstName, lastName, permissionLevel, IDQuestion, responseQuestion) VALUES(?,?,?,?,?,?,?);";
                preparedStatementAdd = connection.prepareStatement(sqlAdd);
                preparedStatementAdd.setString(1, user.getEmail());
                preparedStatementAdd.setString(2, CipherUtil.sha2Generator(user.getPassword()));
                preparedStatementAdd.setString(3, user.getFirstName());
                preparedStatementAdd.setString(4, user.getLastName());
                preparedStatementAdd.setInt(5, user.getPermissionsLevel());
                preparedStatementAdd.setInt(6, user.getIDQuestion());
                preparedStatementAdd.setString(7, user.getResponseQuestion());
                preparedStatementAdd.executeUpdate();

                result = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cleanUp(preparedStatement, preparedStatementAdd);
        }
        return result;
    }

    @Override
    public User getUser() {
        return null;
    }

    public boolean checkPassword(String email, String password){
        String sql = "SELECT hashpass FROM users WHERE email = ?;";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement    = null;

        try (Connection connection = dataSource.getConnection()){
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                return false;
            }
            else{
                 return resultSet.getString(1).equals(CipherUtil.sha2Generator(password));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cleanUp(preparedStatement);
        }
        return false;
    }

    @Override
    public boolean changePermissions(String email, int newPermissionLevel) {
        return false;
    }

    private boolean validateToken(String token){
        return (token != null && token.length() == 64);
    }

    //pas finis
    @Override
    public boolean changePassword(String email, String token, String newPassword) {
        if (validateToken(token)) {
            String sql = "SELECT TOKEN, tokenDate FROM users WHERE email = ?;";
            ResultSet resultSet = null;
            PreparedStatement preparedStatement = null;

            try (Connection connection = dataSource.getConnection()) {
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, email);
                resultSet = preparedStatement.executeQuery();

                if (!resultSet.next()) {
                    return false;
                } else {
                    String TokenInDb =  resultSet.getString(1);
                        return resultSet.getString(0).equals(CipherUtil.sha2Generator(newPassword));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            sql = "UPDATE users SET hashpass = ? WHERE email = ?;";
            resultSet = null;
            PreparedStatement preparedStatement2 = null;

            try (Connection connection = dataSource.getConnection()) {
                preparedStatement2 = connection.prepareStatement(sql);

                preparedStatement2.setString(1, CipherUtil.sha2Generator(newPassword));
                preparedStatement2.setString(2, email);
                preparedStatement2.executeUpdate();
                return true;

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                cleanUp(preparedStatement2, preparedStatement);
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean changePasswordAdmin(String email, String newPassword) {
        String sql = "UPDATE users SET hashpass = ? WHERE email = ?;";
        PreparedStatement preparedStatement    = null;

        try (Connection connection = dataSource.getConnection()){
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, CipherUtil.sha2Generator(newPassword));
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cleanUp(preparedStatement);
        }
        return false;
    }

    @Override
    public int RetrieveSecretQuestion(String email) {
        String sql = "SELECT IDQuestion FROM users WHERE email = ?;";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement    = null;

        try (Connection connection = dataSource.getConnection()){
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                return -1;
            }
            else{
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cleanUp(preparedStatement);
        }
        return -1;
    }

    @Override
    public boolean resetPassword(String email, String response) {
       if(!checkResponse(email, response) || !updateToken(email, true)) {
           return false;
       }
       return true;
    }

    private boolean checkResponse(String email, String response){
        String sql = "SELECT responseQuestion FROM users WHERE email = ?;";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement   = null;

        try (Connection connection = dataSource.getConnection()){
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                return false;
            }
            else{
                String responseInDb = resultSet.getString(1);
                if(!response.equals(responseInDb)){
                    return false;
                };
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cleanUp(preparedStatement);
        }
        return false;
    }

    private boolean updateToken(String email, boolean action){
        String sql = "UPDATE users SET TOKEN = ?, tokenDate = ? WHERE email = ?;";
        PreparedStatement preparedStatement   = null;

        try (Connection connection = dataSource.getConnection()){
            preparedStatement = connection.prepareStatement(sql);
            String token = action ? CipherUtil.sha2Generator(UUID.randomUUID().toString()) : null;
            String tokenDate = action ? f.format(new Date()) : null;

            preparedStatement.setString(1, token);
            preparedStatement.setString(2, tokenDate);
            preparedStatement.setString(3, email);
            preparedStatement.executeUpdate();
            if(action){
                return sendEmail(email, token);
            }
            else{
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cleanUp(preparedStatement);
        }
        return false;
    }

    private boolean sendEmail(String email, String token){
        try {
            EmailUtility.sendEmail("olivier2222@laposte.net", "test", "test");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }

}
