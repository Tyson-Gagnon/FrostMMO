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

        switch (stat.toLowerCase()){
            case "breeding":
                Storage.setBreedXp(target.getUniqueId(),Storage.getBreedExp(target.getUniqueId())+xp);
                break;

            case "catching":
                Storage.setCatchingXp(target.getUniqueId(),Storage.getCatchEXP(target.getUniqueId())+xp);
                break;

            case "battling":
                Storage.setBattleXp(target.getUniqueId(),Storage.getBattleExp(target.getUniqueId())+xp);
                break;

            default:
                src.sendMessage(Text.of("Stat not found! The available types are 'breeding''battling''catching'"));
                break;
        }

        src.sendMessage(Text.of(TextColors.AQUA,"[FrostMMO] - ",TextColors.GRAY,"Added ",TextColors.YELLOW,xp,TextColors.GRAY," Xp to ",TextColors.YELLOW,TextColors.GRAY,"'s stats for ",TextColors.YELLOW,stat));


        return CommandResult.success();
    }
}
