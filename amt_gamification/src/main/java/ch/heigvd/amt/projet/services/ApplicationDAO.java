package ch.heigvd.amt.projet.services;

import ch.heigvd.amt.projet.model.Application;
import ch.heigvd.amt.projet.business.Constants;


import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ApplicationDAO extends DatabaseUtils implements ApplicationDaoLocal {
    private final String sqlAppExist = "SELECT appName FROM applications WHERE appowner = ? && appname = ?;";
    private final String sqlInsertApp = "INSERT INTO applications(appOwner, appName, description, APIToken, APISecret) VALUES(?,?,?,?,?);";
    private final String sqlSelectApp = "SELECT APIToken FROM applications WHERE APIToken = ? and appOwner = ?;";
    private final String sqlDeleteApp = "DELETE FROM applications WHERE APIToken = ? and appOwner = ?;";
    private final String sqlUpdateApp = "UPDATE applications SET appName = ?, description = ? WHERE APIToken = ? and appOwner = ?;";
    private final String sqlSelectAppByDev = "SELECT appOwner FROM applications WHERE appOwner = ?;";
    private final String sqlSelectSetOfApp = "SELECT * FROM applications WHERE appOwner = ? ORDER BY appName LIMIT ? OFFSET ?;";
    private final String sqlSelectAllApp = "SELECT appOwner FROM applications";
    private final String sqlSelectSetOfAllApp = "SELECT * FROM applications ORDER BY appName LIMIT ? OFFSET ?;";
    private final String sqlSelectUniqueApp = "SELECT * FROM applications WHERE appOwner = ? AND APIToken = ?;";

    @Resource(lookup = "java:/amt_project")
    DataSource dataSource;


    @Override
    public boolean createApp(Application app) {
        ResultSet resultSet;
        PreparedStatement preparedStatementExists;
        PreparedStatement preparedStatement    = null;

        try (Connection connection = dataSource.getConnection()) {
            preparedStatementExists = connection.prepareStatement(sqlAppExist);
            preparedStatementExists.setString(1,app.getAppOwner());
            preparedStatementExists.setString(2,app.getAppName());
            resultSet = preparedStatementExists.executeQuery();
            if(!resultSet.next()) {
                preparedStatement = connection.prepareStatement(sqlInsertApp);

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
        return deleteApp(app.getAPI_TOKEN(), app.getAppOwner());
    }

    @Override
    public boolean deleteApp(String APIToken, String appOwner) {
        ResultSet resultSet;
        PreparedStatement preparedStatement    = null;
        PreparedStatement preparedStatementDel = null;

        try (Connection connection = dataSource.getConnection()) {
            preparedStatement = connection.prepareStatement(sqlSelectApp);

            preparedStatement.setString(1, APIToken);
            preparedStatement.setString(2, appOwner);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                return false;
            }
            else{
                resultSet.beforeFirst();
                preparedStatementDel = connection.prepareStatement(sqlDeleteApp);
                preparedStatementDel.setString(1, APIToken);
                preparedStatementDel.setString(2, appOwner);
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
    public boolean updateApp(Application app, String newName, String newDescription, String appOwner) {
        return updateApp(app.getAPI_TOKEN(), newName, newDescription, appOwner);
    }

    @Override
    public boolean updateApp(String APIToken, String newName, String newDescription, String appOwner) {
        if(APIToken == null || newName == null || newDescription == null){
            return false;
        }
        ResultSet resultSet;
        PreparedStatement preparedStatement    = null;
        PreparedStatement preparedStatementDel = null;

        try (Connection connection = dataSource.getConnection()) {
            preparedStatement = connection.prepareStatement(sqlSelectApp);

            preparedStatement.setString(1, APIToken);
            preparedStatement.setString(2, appOwner);
            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                return false;
            }
            else{
                resultSet.beforeFirst();
                preparedStatementDel = connection.prepareStatement(sqlUpdateApp);
                preparedStatementDel.setString(1, newName);
                preparedStatementDel.setString(2, newDescription);
                preparedStatementDel.setString(3, APIToken);
                preparedStatementDel.setString(4, appOwner);
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
        int maxNumberOfAppToReturn = Constants.APPS_PER_PAGE + 1;
        if(permissionLevel < 0 || permissionLevel > 1 || appOwner == null || pageNumber < 1){
            return null;
        }
        String sql, sqlSelect;
        if(permissionLevel == 0){
            sql = sqlSelectAppByDev;
            sqlSelect = sqlSelectSetOfApp;
        }
        else {
            sql = sqlSelectAllApp;
            sqlSelect = sqlSelectSetOfAllApp;
        }
        ResultSet resultSet;
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
                    preparedStatementDel.setInt(2, maxNumberOfAppToReturn);
                    preparedStatementDel.setInt(3, Constants.APPS_PER_PAGE * (pageNumber - 1));
                }
                else {
                    preparedStatementDel.setInt(1, maxNumberOfAppToReturn);
                    preparedStatementDel.setInt(2, Constants.APPS_PER_PAGE * (pageNumber - 1));
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

    @Override
    public Application getApp(String appTOKEN, String appOwner) {
        if(appTOKEN == null || appOwner == null){
            return null;
        }

        ResultSet resultSet;
        PreparedStatement preparedStatement = null;
        Application app;

        try (Connection connection = dataSource.getConnection()) {
            preparedStatement = connection.prepareStatement(sqlSelectUniqueApp);
            preparedStatement.setString(1, appOwner);
            preparedStatement.setString(2, appTOKEN);

            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                return null;
            }

            app = new Application(resultSet.getString("appOwner"), resultSet.getString("appName"), resultSet.getString("description"), resultSet.getString("APIToken"), resultSet.getString("APISecret"));

            return app;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            cleanUp(preparedStatement);
        }
    }

}
