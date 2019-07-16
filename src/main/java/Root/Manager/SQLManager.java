package Root.Manager;

import Root.FrostMMO;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.service.sql.SqlService;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLManager {

    private static SqlService sql;
    private static final String URI = "jdbc:h2:" + FrostMMO.getDir().toString() + "/players.db";

    public static void load(){
        try{
            sql = Sponge.getServiceManager().provide(SqlService.class).get();
            Connection connection = getConnection();
            Statement stmt = connection.createStatement();

            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS PLAYERDB (" +
                            "PLAYER UUID UNSIGNED NOT NULL," +
                    "PLAYERNAME VARCHAR(20)," +
                    "BATTLELEVEL INT NOT NULL DEFAULT 1," +
                    "BATTLEEXP INT NOT NULL DEFAULT 0," +

                    "CATCHLEVEL INT NOT NULL DEFAULT 1000," +
                    "CATCHEXP INT NOT NULL DEFAULT 0," +
                            "BREEDLEVEL INT NOT NULL DEFAULT 1000," +
                            "BREEDEXP INT NOT NULL DEFAULT 0," +
                    "PRIMARY KEY (PLAYER))");

            stmt.close();
            connection.close();


        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    protected static Connection getConnection(){
        try {
            return sql.getDataSource(URI).getConnection();
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}