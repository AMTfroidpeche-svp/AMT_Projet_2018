package ch.heigvd.amt.projet.services;

import ch.heigvd.amt.projet.model.Application;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ApplicationDAO extends DatabaseUtils implements ApplicationDaoLocal {

    @Resource(lookup = "java:/amt_project")
    DataSource dataSource;


    @Override
    public boolean createApp(Application app) {
        //TODO: insert if not exists
        String sqlAppExist = "SELECT FROM applications(appName) WHERE appowner = ?;";
        String sql = "INSERT INTO applications(appOwner, appName, description, APIToken, APISecret) VALUES(?,?,?,?,?);";
        boolean result = false;
        ResultSet resultSet = null;
        PreparedStatement preparedStatementExists = null;
        PreparedStatement preparedStatement    = null;

        try (Connection connection = dataSource.getConnection()) {
            preparedStatementExists = connection.prepareStatement(sqlAppExist);
            preparedStatementExists.setString(1,app.getAppOwner());
            resultSet = preparedStatementExists.executeQuery();
            if(!resultSet.next()) {
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, app.getAppOwner());
                preparedStatement.setString(2, app.getAppName());
                preparedStatement.setString(3, app.getDescription());
                preparedStatement.setString(4, app.getAPI_TOKEN());
                preparedStatement.setString(5, app.getAPI_SECRET());
                preparedStatement.executeUpdate();
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
        cleanUp(preparedStatement);
    }
}

    @Override
    public boolean deleteApp(Application app) {
        String sql = "SELECT APIToken FROM applications WHERE APIToken = ?;";
        ResultSet resultSet = null;
        boolean result = false;
        PreparedStatement preparedStatement    = null;
        PreparedStatement preparedStatementDel = null;

        try (Connection connection = dataSource.getConnection()) {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, app.getAPI_TOKEN());
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                return false;
            }
            else{
                resultSet.beforeFirst();
                String sqlDel = "DELETE FROM applications WHERE APIToken = ?;";
                preparedStatementDel = connection.prepareStatement(sqlDel);
                preparedStatementDel.setString(1, app.getAPI_TOKEN());
                preparedStatementDel.execute();
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            cleanUp(preparedStatement, preparedStatementDel);
        }
    }

    @Override
    public boolean updateApp(Application app, String newName, String newDescription) {
        if(newName == null || newDescription == null){
            return false;
        }
        String sql = "SELECT APIToken FROM applications WHERE APIToken = ?;";
        ResultSet resultSet = null;
        boolean result = false;
        PreparedStatement preparedStatement    = null;
        PreparedStatement preparedStatementDel = null;

        try (Connection connection = dataSource.getConnection()) {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, app.getAPI_TOKEN());
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                return false;
            }
            else{
                resultSet.beforeFirst();
                String sqlUpdate = "UPDATE applications SET appName = ?, description = ? WHERE APIToken = ?;";
                preparedStatementDel = connection.prepareStatement(sqlUpdate);
                preparedStatementDel.setString(1, newName);
                preparedStatementDel.setString(2, newDescription);
                preparedStatementDel.setString(3, app.getAPI_TOKEN());
                preparedStatementDel.execute();
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            cleanUp(preparedStatement, preparedStatementDel);
        }

    }

    @Override
    public List<Application> retrieveApp(String appOwner, int pageNumber, int permissionLevel) {
        if(permissionLevel < 0 || permissionLevel > 1 || appOwner == null || pageNumber < 1){
            return null;
        }
        String sql, sqlSelect;
        if(permissionLevel == 0){
            sql = "SELECT appOwner FROM applications WHERE appOwner = ?;";
            sqlSelect = "SELECT * FROM applications WHERE appOwner = ? ORDER BY appName LIMIT 11 OFFSET ?;";
        }
        else {
            sql = "SELECT appOwner FROM applications";
            sqlSelect = "SELECT * FROM applications ORDER BY appName LIMIT 11 OFFSET ?;";
        }
        ResultSet resultSet = null;
        PreparedStatement preparedStatement    = null;
        PreparedStatement preparedStatementDel = null;

        try (Connection connection = dataSource.getConnection()) {
            preparedStatement = connection.prepareStatement(sql);
            if(permissionLevel == 0) {
                preparedStatement.setString(1, appOwner);
            }
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
                if(permissionLevel == 0) {
                    preparedStatementDel.setString(1, appOwner);
                    preparedStatementDel.setInt(2, 10 * (pageNumber - 1));
                }
                else {
                    preparedStatementDel.setInt(1, 10 * (pageNumber - 1));
                }
                resultSet = preparedStatementDel.executeQuery();
                ArrayList<Application> retArray = new ArrayList<>();
                while(resultSet.next()){
                    retArray.add(new Application(resultSet.getString("appOwner"), resultSet.getString("appName"), resultSet.getString("description"), resultSet.getString("APIToken"), resultSet.getString("APISecret")));
                }
                return retArray;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            cleanUp(preparedStatement, preparedStatementDel);
        }

    }

    @Override
    public List<Application> retrieveApp(String appOwner, int pageNumber) {
        return retrieveApp(appOwner, pageNumber, 0);
    }

}
