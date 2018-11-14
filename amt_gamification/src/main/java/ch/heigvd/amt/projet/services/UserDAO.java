package ch.heigvd.amt.projet.services;

import ch.heigvd.amt.projet.business.CipherUtil;
import ch.heigvd.amt.projet.model.Application;
import ch.heigvd.amt.projet.model.EmailUtility;
import ch.heigvd.amt.projet.model.User;

import javax.annotation.Resource;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.mail.MessagingException;
import javax.sql.DataSource;
import javax.ejb.Stateless;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
                preparedStatementAdd.setString(2, user.getPassword());
                preparedStatementAdd.setString(3, user.getFirstName());
                preparedStatementAdd.setString(4, user.getLastName());
                preparedStatementAdd.setInt(5, user.getPermissionLevel());
                preparedStatementAdd.setInt(6, user.getIDQuestion());
                preparedStatementAdd.setString(7, user.getResponseQuestion());
                preparedStatementAdd.executeUpdate();

                result = true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            cleanUp(preparedStatement, preparedStatementAdd);
        }
        return result;
    }

    @Override
    public User getUser(String email) {
        String sql = "SELECT * FROM users WHERE email = ?;";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement    = null;

        try (Connection connection = dataSource.getConnection()){
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                return null;
            }
            else{
                return mapUser(resultSet);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            cleanUp(preparedStatement);
        }
    }

    @Override
    public List<User> getPageUser(int pageNumber) {
        if(pageNumber < 1){
            return null;
        }
        String sql, sqlSelect;
        sql = "SELECT email FROM users";
        sqlSelect = "SELECT * FROM users ORDER BY email LIMIT 11 OFFSET ?;";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement    = null;
        PreparedStatement preparedStatementDel = null;

        try (Connection connection = dataSource.getConnection()) {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            int numberOfApp = 0;
            if (resultSet.last()) {
                numberOfApp = resultSet.getRow();
            }
            if((pageNumber - 1) * 10 >= numberOfApp){
                return null;
            }
            else{
                preparedStatementDel = connection.prepareStatement(sqlSelect);
                preparedStatementDel.setInt(1, 10 * (pageNumber - 1));
                resultSet = preparedStatementDel.executeQuery();
                ArrayList<User> retArray = new ArrayList<>();
                while(resultSet.next()){
                    retArray.add(mapUser(resultSet));
                }
                return retArray;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            cleanUp(preparedStatement, preparedStatementDel);
        }

    }

    public User checkPassword(String email, String password){
        String sql = "SELECT * FROM users WHERE email = ?;";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement    = null;

        try (Connection connection = dataSource.getConnection()){
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            String hashedPassword = CipherUtil.sha2Generator(password);
            if(!resultSet.next() || !resultSet.getString("hashPass").equals(hashedPassword)) {
                return null;
            }
            else{
                return mapUser(resultSet);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            cleanUp(preparedStatement);
        }
    }

    @Override
    public boolean changePermissions(String email, int newPermissionLevel) {
        String sql = "UPDATE users SET permissionLevel = ? WHERE email = ?;";
        PreparedStatement preparedStatement    = null;

        try (Connection connection = dataSource.getConnection()){
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, newPermissionLevel);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            cleanUp(preparedStatement);
        }
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
            PreparedStatement preparedStatementUpdate = null;

            try (Connection connection = dataSource.getConnection()) {
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, email);
                resultSet = preparedStatement.executeQuery();

                if (!resultSet.next()) {
                    return false;
                } else {
                    String tokenInDb =  resultSet.getString(1);
                    Date dateToken = f.parse(resultSet.getString(2));
                    long diff = new Date().getTime() - dateToken.getTime();
                    diff = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);

                    if(token.equals(tokenInDb) && diff < 30){
                        sql = "UPDATE users SET hashpass = ? WHERE email = ?;";
                        preparedStatementUpdate = null;
                        preparedStatementUpdate = connection.prepareStatement(sql);

                        preparedStatementUpdate.setString(1, CipherUtil.sha2Generator(newPassword));
                        preparedStatementUpdate.setString(2, email);
                        preparedStatementUpdate.executeUpdate();
                        return true;
                    }
                }

            } catch (SQLException | ParseException e) {
                throw new RuntimeException(e);
            } finally {
            cleanUp(preparedStatementUpdate, preparedStatement);
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
            throw new RuntimeException(e);
        } finally {
            cleanUp(preparedStatement);
        }
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
            throw new RuntimeException(e);
        } finally {
            cleanUp(preparedStatement);
        }
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
            throw new RuntimeException(e);
        } finally {
            cleanUp(preparedStatement);
        }
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
                return sendEmailToken(email, token);
            }
            else{
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            cleanUp(preparedStatement);
        }
    }

    private boolean sendEmailToken(String email, String token){
        try {
            EmailUtility.sendEmail("olivier2222@laposte.net", "test", token);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean sendEmailPassword(String email, String password){
        try {
            EmailUtility.sendEmail("", "test", "test");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static User mapUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setFirstName(resultSet.getString("firstName"));
        user.setLastName(resultSet.getString("lastName"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("hashPass"));
        user.setIDQuestion(resultSet.getInt("IDQuestion"));
        user.setPermissionLevel(resultSet.getInt("permissionLevel"));
        user.setResponseQuestion(resultSet.getString("responseQuestion"));
        user.setToken(resultSet.getString("TOKEN"));
        // todo
        //user.setTokenGeneration(resultSet.get);
        return user;
    }

}
