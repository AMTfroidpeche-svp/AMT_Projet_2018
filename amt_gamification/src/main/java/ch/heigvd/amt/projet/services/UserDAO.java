package ch.heigvd.amt.projet.services;

import ch.heigvd.amt.projet.business.CipherUtil;
import ch.heigvd.amt.projet.model.EmailUtility;
import ch.heigvd.amt.projet.model.Question;
import ch.heigvd.amt.projet.model.User;

import javax.annotation.Resource;
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
public class UserDAO extends DatabaseUtils implements UserDAOLocal {
    private final static String sqlAddUser = "INSERT INTO users(email, hashPass, firstName, lastName, permissionLevel, IDQuestion, responseQuestion, isActive, hasToChangePassword, imageUrl) VALUES(?,?,?,?,?,?,?,?,?,?);";
    private final static String sqlSelectUser = "SELECT email FROM users WHERE email = ?;";
    private final static String sqlSelectAllUser = "SELECT * FROM users WHERE email = ?;";
    private final static String sqlSetOfUser = "SELECT * FROM users ORDER BY email LIMIT 11 OFFSET ?;";
    private final static String sqlChangePermission = "UPDATE users SET permissionLevel = ? WHERE email = ?;";
    private final static String sqlSelectToken = "SELECT TOKEN, tokenDate FROM users WHERE email = ?;";
    private final static String sqlUpdatePassToken = "UPDATE users SET hashpass = ?, TOKEN = null, tokenDate = null WHERE email = ?;";
    private final static String sqlPass = "SELECT hashpass FROM users WHERE email = ?;";
    private final static String sqlUpdatePassHasToChange = "UPDATE users SET hashpass = ?, hasToChangePassword = ? WHERE email = ?;";
    private final static String sqlIdQuestion = "SELECT IDQuestion FROM users WHERE email = ?;";
    private final static String sqlQuestions = "SELECT * FROM questions;";
    private final static String sqlUpdateImage = "UPDATE users SET imageUrl = ? WHERE email = ?;";
    private final static String sqlUpdateDescription = "UPDATE users SET description = ? WHERE email = ?;";
    private final static String sqlUpdateActive = "UPDATE users SET isActive = ? WHERE email = ?;";
    private final static String sqlUpdateToken = "UPDATE users SET TOKEN = ?, tokenDate = ? WHERE email = ?;";
    private final static String sqlSelectResponse = "SELECT responseQuestion FROM users WHERE email = ?;";

    private final static int TOKEN_VALIDITY = 30; //token validity in minutes


    @Resource(lookup = "java:/amt_project")
    DataSource dataSource;

    private static final SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Override
    public boolean addUser(User user){
        ResultSet resultSet;
        boolean result = false;
        PreparedStatement preparedStatement    = null;
        PreparedStatement preparedStatementAdd = null;

        try (Connection connection = dataSource.getConnection()) {
            preparedStatement = connection.prepareStatement(sqlSelectUser);

            preparedStatement.setString(1, user.getEmail());
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                preparedStatementAdd = connection.prepareStatement(sqlAddUser);
                preparedStatementAdd.setString(1, user.getEmail());
                preparedStatementAdd.setString(2, user.getPassword());
                preparedStatementAdd.setString(3, user.getFirstName());
                preparedStatementAdd.setString(4, user.getLastName());
                preparedStatementAdd.setInt(5, user.getPermissionLevel());
                preparedStatementAdd.setInt(6, user.getIDQuestion()+1); // +1 because idQuestion starts from 0 in jsp
                preparedStatementAdd.setString(7, user.getResponseQuestion());
                preparedStatementAdd.setBoolean(8, user.getIsActive());
                preparedStatementAdd.setBoolean(9, user.hasToChangedPassword());
                preparedStatementAdd.setString(10, User.DEFAULT_IMAGE_URL);

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
        ResultSet resultSet;
        PreparedStatement preparedStatement    = null;

        try (Connection connection = dataSource.getConnection()){
            preparedStatement = connection.prepareStatement(sqlSelectAllUser);

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
        ResultSet resultSet;
        PreparedStatement preparedStatement    = null;
        PreparedStatement preparedStatementDel = null;

        try (Connection connection = dataSource.getConnection()) {
            preparedStatement = connection.prepareStatement(sqlSelectUser);
            resultSet = preparedStatement.executeQuery();
            int numberOfApp = 0;
            if (resultSet.last()) {
                numberOfApp = resultSet.getRow();
            }
            if((pageNumber - 1) * 10 >= numberOfApp){
                return null;
            }
            else{
                preparedStatementDel = connection.prepareStatement(sqlSetOfUser);
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
        ResultSet resultSet;
        PreparedStatement preparedStatement    = null;

        try (Connection connection = dataSource.getConnection()){
            preparedStatement = connection.prepareStatement(sqlSelectAllUser);

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
        PreparedStatement preparedStatement    = null;

        try (Connection connection = dataSource.getConnection()){
            preparedStatement = connection.prepareStatement(sqlChangePermission);

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

    @Override
    public boolean changePassword(String email, String token, String newPassword) {
        if (validateToken(token)) {
            ResultSet resultSet;
            PreparedStatement preparedStatement = null;
            PreparedStatement preparedStatementUpdate = null;

            try (Connection connection = dataSource.getConnection()) {
                preparedStatement = connection.prepareStatement(sqlSelectToken);

                preparedStatement.setString(1, email);
                resultSet = preparedStatement.executeQuery();

                if (!resultSet.next()) {
                    return false;
                } else {
                    String tokenInDb =  resultSet.getString(1);
                    Date dateToken = f.parse(resultSet.getString(2));
                    long diff = new Date().getTime() - dateToken.getTime();
                    diff = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);

                    if(token.equals(tokenInDb) && diff < TOKEN_VALIDITY){
                        preparedStatementUpdate = null;
                        preparedStatementUpdate = connection.prepareStatement(sqlUpdatePassToken);

                        preparedStatementUpdate.setString(1, CipherUtil.sha2Generator(newPassword));
                        preparedStatementUpdate.setString(2, email);
                        preparedStatementUpdate.executeUpdate();
                        return updateToken(email, false);
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
    public boolean changePasswordWithoutToken(String email, String oldPassword, String newPassword) {
            ResultSet resultSet;
            PreparedStatement preparedStatement = null;
            PreparedStatement preparedStatementUpdate = null;

            try (Connection connection = dataSource.getConnection()) {
                preparedStatement = connection.prepareStatement(sqlPass);

                preparedStatement.setString(1, email);
                resultSet = preparedStatement.executeQuery();

                if (!resultSet.next()) {
                    return false;
                } else {
                    String oldPasswordHash =  CipherUtil.sha2Generator(oldPassword);

                    if(oldPasswordHash.equals(resultSet.getString(1))){
                        preparedStatementUpdate = null;
                        preparedStatementUpdate = connection.prepareStatement(sqlUpdatePassHasToChange);

                        preparedStatementUpdate.setString(1, CipherUtil.sha2Generator(newPassword));
                        preparedStatementUpdate.setBoolean(2, false);
                        preparedStatementUpdate.setString(3, email);
                        preparedStatementUpdate.executeUpdate();
                        return true;
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                cleanUp(preparedStatementUpdate, preparedStatement);
            }

            return false;
    }

    @Override
    public boolean changePasswordAdmin(String email) {
        PreparedStatement preparedStatement    = null;

        try (Connection connection = dataSource.getConnection()){
            preparedStatement = connection.prepareStatement(sqlUpdatePassHasToChange);

            String newPassword = UUID.randomUUID().toString();
            preparedStatement.setString(1, CipherUtil.sha2Generator(newPassword));
            preparedStatement.setBoolean(2, false);
            preparedStatement.setString(3, email);
            preparedStatement.executeUpdate();
            return sendEmailPassword(email, newPassword);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            cleanUp(preparedStatement);
        }
    }

    @Override
    public int RetrieveSecretQuestion(String email) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement    = null;

        try (Connection connection = dataSource.getConnection()){
            preparedStatement = connection.prepareStatement(sqlIdQuestion);

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
    public List<Question> getAllQuestions() {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement    = null;

        try (Connection connection = dataSource.getConnection()){
            preparedStatement = connection.prepareStatement(sqlQuestions);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                return null;
            }
            else{
                resultSet.beforeFirst();
                ArrayList<Question> ret = new ArrayList<>();
                while (resultSet.next()){
                    ret.add(new Question(resultSet.getString("question"), resultSet.getInt("ID")));
                }
                return ret;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            cleanUp(preparedStatement);
        }
    }

    @Override
    public boolean updateImage(String email, String url) {
        PreparedStatement preparedStatement    = null;

        try (Connection connection = dataSource.getConnection()){
            preparedStatement = connection.prepareStatement(sqlUpdateImage);

            String newPassword = UUID.randomUUID().toString();
            preparedStatement.setString(1, url);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
            return sendEmailPassword(email, newPassword);

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

    @Override
    public boolean setActive(String email, int isActive) {
        PreparedStatement preparedStatement = null;

        try (Connection connection = dataSource.getConnection()) {
            preparedStatement = connection.prepareStatement(sqlUpdateActive);

            preparedStatement.setInt(1, isActive);
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
    public boolean setDescription(String email, String description) {

        PreparedStatement preparedStatement = null;

        try (Connection connection = dataSource.getConnection()) {
            preparedStatement = connection.prepareStatement(sqlUpdateDescription);

            preparedStatement.setString(1, description);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            cleanUp(preparedStatement);
        }
    }

    private boolean checkResponse(String email, String response){
        ResultSet resultSet;
        PreparedStatement preparedStatement   = null;

        try (Connection connection = dataSource.getConnection()){
            preparedStatement = connection.prepareStatement(sqlSelectResponse);

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
        PreparedStatement preparedStatement   = null;

        try (Connection connection = dataSource.getConnection()){
            preparedStatement = connection.prepareStatement(sqlUpdateToken);
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
            EmailUtility.sendEmail(email, "Request reset password", "A request has been made to reset your password.\nPlease use this token to change your password within 30 minutes : " + token + "\n\nIf you didn't ask to reset your password, you can ignore this email.\n\n\nAMTFroidPeche devTeam.");
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean sendEmailPassword(String email, String password){
        try {
            EmailUtility.sendEmail(email, "Password reset", "An admin reset your password.\nPlease use this new password to connect to the app : " + password + "\n\n\nAMTFroidPeche devTeam.");
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
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
        user.setActive(resultSet.getBoolean("isActive"));
        user.setHasToChangedPassword(resultSet.getBoolean("hasToChangePassword"));
        user.setDescription(resultSet.getString("description"));
        user.setImageUrl(resultSet.getString("imageUrl"));
        return user;
    }

}
