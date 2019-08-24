package Root.Objects;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;

public class PlayerLevels {

    private Player player;

    private User user;

    public PlayerLevels(int breedExp, int catchEXP, int battleExp, int pokeballExp) {
    }

    public Player getPlayer() {
        return player;
    }

    public int getBreedlevel() {
        return breedlevel;
    }

    public int getCathclevel() {
        return cathclevel;
    }

    public int getKilllevel() {
        return killlevel;
    }

    public int getPballLevel() {
        return pballLevel;
    }

    private int breedlevel, cathclevel, killlevel, pballLevel;

    public User getUser() {
        return user;
    }

    public PlayerLevels(Player player, int breedexp, int catchexp, int killexp) {
        this.player = player;
        this.breedlevel = expToLevel(breedexp);
        this.cathclevel = expToLevel(catchexp);
        this.killlevel = expToLevel(killexp);
    }
    public PlayerLevels(int breedexp, int catchexp, int killexp) {
        this.breedlevel = expToLevel(breedexp);
        this.cathclevel = expToLevel(catchexp);
        this.killlevel = expToLevel(killexp);
    }

    public PlayerLevels(User user, int breedexp, int catchexp, int killexp) {
        this.user = user;
        this.breedlevel = expToLevel(breedexp);
        this.cathclevel = expToLevel(catchexp);
        this.killlevel = expToLevel(killexp);
    }

    public PlayerLevels(User user, int breedexp, int catchexp, int killexp, int pokeballExp) {
        this.user = user;
        this.breedlevel = expToLevel(breedexp);
        this.cathclevel = expToLevel(catchexp);
        this.killlevel = expToLevel(killexp);
        this.pballLevel = expToLevel(pokeballExp);
    }

    private int expToLevel(int exp){
        int level = 0;

        level =(int) Math.floor((25+ Math.sqrt(5*(125+exp)))/50);

        return level;
    }
}
