package ch.heigvd.amt.projet.services;

import ch.heigvd.amt.projet.business.CipherUtil;
import ch.heigvd.amt.projet.model.User;

import javax.annotation.Resource;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.sql.DataSource;
import javax.ejb.Stateless;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class UserDAO extends DatabaseUtils implements UserDAOLocal {

    @Resource(lookup = "java:/amt_project")
    DataSource dataSource;

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
                resultSet.beforeFirst();
                try {
                    return Arrays.equals(resultSet.getBytes(0), CipherUtil.sha2Generator(password));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cleanUp(preparedStatement);
        }
        return false;
    }

}
