package Root.Commands;

import Root.FrostMMO;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class Base implements CommandExecutor
{
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        src.sendMessage(Text.of(TextColors.AQUA,"[FrostMMO] - ",TextColors.GRAY,"A Pixelmon MMO plugin made by itsTyxD and Future, Version:", FrostMMO.pluginContainer.getVersion()));
        return CommandResult.success();
    }
}
