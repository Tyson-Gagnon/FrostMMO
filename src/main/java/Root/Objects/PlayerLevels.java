package Root.Objects;

import org.spongepowered.api.entity.living.player.Player;

public class PlayerLevels {

    private Player player;

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

    private int breedlevel,cathclevel,killlevel;

    public PlayerLevels(Player player, int breedexp, int catchexp, int killexp) {
        this.player = player;
        this.breedlevel = expToLevel(breedexp);
        this.cathclevel = expToLevel(catchexp);
        this.killlevel = expToLevel(killexp);
    }

    private int expToLevel(int exp){
        int level = 0;

        level =(int) Math.floor((25+ Math.sqrt(5*(125+exp)))/50);

        return level;
    }
}
