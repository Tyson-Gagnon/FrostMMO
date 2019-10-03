package Root.Objects;

import Root.Manager.SQLManager;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.user.UserStorageService;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class Storage extends SQLManager {
    public static int getBattleLevel(UUID player) {
        int value = 0;

        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PLAYERDB WHERE PLAYER=?");
            preparedStatement.setString(1, player.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
                value = resultSet.getInt("BATTLELEVEL");

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return value;
    }

    public static int getBreedLevel(UUID player) {
        int value = 0;

        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PLAYERDB WHERE PLAYER=?");
            preparedStatement.setString(1, player.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
                value = resultSet.getInt("BREEDLEVEL");

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return value;
    }
    public static int getCatchLevel(UUID player) {
        int value = 0;

        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PLAYERDB WHERE PLAYER=?");
            preparedStatement.setString(1, player.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
                value = resultSet.getInt("CATCHLEVEL");

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return value;
    }
    public static int getBattleExp(UUID player) {
        int value = 0;

        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PLAYERDB WHERE PLAYER=?");
            preparedStatement.setString(1, player.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
                value = resultSet.getInt("BATTLEEXP");

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return value;
    }
    public static int getCatchEXP(UUID player) {
        int value = 0;

        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PLAYERDB WHERE PLAYER=?");
            preparedStatement.setString(1, player.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
                value = resultSet.getInt("CATCHEXP");

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return value;
    }
    public static int getBreedExp(UUID player) {
        int value = 0;

        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PLAYERDB WHERE PLAYER=?");
            preparedStatement.setString(1, player.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
                value = resultSet.getInt("BREEDEXP");

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return value;
    }
    public static void setBattleXp(UUID player, int xp) {

        try {

            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("MERGE INTO PLAYERDB (PLAYER,BATTLEEXP) KEY (PLAYER) VALUES (?,?)");

            preparedStatement.setString(1, player.toString());
            preparedStatement.setInt(2, xp);


            preparedStatement.execute();

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void setBreedXp(UUID player, int xp) {

        try {

            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("MERGE INTO PLAYERDB (PLAYER,BREEDEXP) KEY (PLAYER) VALUES (?,?)");

            preparedStatement.setString(1, player.toString());
            preparedStatement.setInt(2, xp);


            preparedStatement.execute();

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void setCatchingXp(UUID player, int xp) {

        try {

            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("MERGE INTO PLAYERDB (PLAYER,CATCHEXP) KEY (PLAYER) VALUES (?,?)");

            preparedStatement.setString(1, player.toString());
            preparedStatement.setInt(2, xp);


            preparedStatement.execute();

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static ArrayList<PlayerLevels> getPlayerRankings() {
        try {
            Connection conn = getConnection();
            Statement stm;
            stm = conn.createStatement();
            String sql = "SELECT * FROM PLAYERDB";
            ResultSet rst;
            rst = stm.executeQuery(sql);
            ArrayList<PlayerLevels> playerRankingArrayList = new ArrayList<>();
            while (rst.next()) {
                UserStorageService userStorageService = Sponge.getServiceManager().provide(UserStorageService.class).get();
                User user = userStorageService.get(UUID.fromString(rst.getString("PLAYER"))).get();
                PlayerLevels playerRanking = new PlayerLevels(user,
                        rst.getInt("BREEDEXP"),
                        rst.getInt("CATCHEXP"),
                        rst.getInt("BATTLEEXP"));
                playerRankingArrayList.add(playerRanking);
            }
            stm.close();
            conn.close();

            return playerRankingArrayList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getPokeballExp(UUID player) {
        int value = 0;

        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PLAYERDB WHERE PLAYER=?");
            preparedStatement.setString(1, player.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
                value = resultSet.getInt("POKEBALLEXP");

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return value;
    }

    public static void setPokeballXp(UUID player, int xp) {

        try {

            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("MERGE INTO PLAYERDB (PLAYER,POKEBALLEXP) KEY (PLAYER) VALUES (?,?)");

            preparedStatement.setString(1, player.toString());
            preparedStatement.setInt(2, xp);


            preparedStatement.execute();

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
