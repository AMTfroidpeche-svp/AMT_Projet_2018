package ch.heigvd.amt.projet.services;

import ch.heigvd.amt.projet.model.User;

import javax.annotation.Resource;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.sql.DataSource;
import javax.ejb.Stateless;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class UserDAO implements UserDAOLocal {

    @Resource(lookup = "java:/amt_project")
    DataSource dataSource;

    @Override
    public boolean addUser(User user){
        String sql = "SELECT email FROM users WHERE ID = ?;";
        ResultSet resultSet = null;
        boolean result = false;
        PreparedStatement preparedStatement    = null;
        PreparedStatement preparedStatementAdd = null;

        try (Connection connection = dataSource.getConnection()) {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, user.getMail());
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                String sqlAdd = "INSERT INTO users VALUES(?,?,?,?,?,?,?);";
                preparedStatementAdd = connection.prepareStatement(sqlAdd);
                preparedStatementAdd.setString(1, user.getMail());
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
}
