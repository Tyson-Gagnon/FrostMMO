package Root.Commands;

import Root.Objects.Storage;
import net.minecraft.command.CommandDebug;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class AddExp implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        String stat = args.<String>getOne("stat").get();
        Player target = args.<Player>getOne("target").get();
        int xp = args.<Integer>getOne("xp").get();

        switch (stat.toLowerCase()) {
            case "breeding":
                if (!(Storage.getBreedExp(target.getUniqueId()) + xp > 4950000)) {
                    Storage.setBreedXp(target.getUniqueId(), Storage.getBreedExp(target.getUniqueId()) + xp);
                } else {
                    src.sendMessage(Text.of(TextColors.RED, "Players cannot exceed a total of 495000 xp(lvl100)! The max you can give to the player is ",
                            4950000 - Storage.getBreedExp(target.getUniqueId())));
                }
                break;

            case "catching":
                if (!(Storage.getCatchEXP(target.getUniqueId()) + xp > 4950000)) {
                    Storage.setCatchingXp(target.getUniqueId(), Storage.getCatchEXP(target.getUniqueId()) + xp);
                } else {
                    src.sendMessage(Text.of(TextColors.RED, "Players cannot exceed a total of 495000 xp(lvl100)! The max you can give to the player is ",
                            4950000 - Storage.getCatchEXP(target.getUniqueId())));
                }
                break;

            case "battling":
                if (!(Storage.getBattleExp(target.getUniqueId()) + xp > 4950000)) {
                    Storage.setBattleXp(target.getUniqueId(), Storage.getBattleExp(target.getUniqueId()) + xp);
                } else {
                    src.sendMessage(Text.of(TextColors.RED, "Players cannot exceed a total of 495000 xp(lvl100)! The max you can give to the player is ",
                            4950000 - Storage.getBattleExp(target.getUniqueId())));
                }
                break;
            case "pball":
                if (!(Storage.getPokeballExp(target.getUniqueId()) + xp > 4950000)) {
                    Storage.setPokeballXp(target.getUniqueId(), Storage.getPokeballExp(target.getUniqueId()) + xp);
                } else {
                    src.sendMessage(Text.of(TextColors.RED, "Players cannot exceed a total of 495000 xp(lvl100)! The max you can give to the player is ",
                            4950000 - Storage.getPokeballExp(target.getUniqueId())));
                }
                break;

            default:
                src.sendMessage(Text.of("Stat not found! The available types are 'breeding''battling''catching'"));
                break;
        }

        src.sendMessage(Text.of(TextColors.AQUA, "[FrostMMO] - ", TextColors.GRAY, "Added ", TextColors.YELLOW, xp, TextColors.GRAY, " Xp to ", TextColors.YELLOW, TextColors.GRAY, "'s stats for ", TextColors.YELLOW, stat));


        return CommandResult.success();
    }
}
