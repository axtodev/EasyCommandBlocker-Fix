package ecb.ajneb97.spigot.listeners;

import ecb.ajneb97.core.managers.CommandsManager;
import ecb.ajneb97.spigot.EasyCommandBlocker;
import ecb.ajneb97.spigot.utils.OtherUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerListenerNew implements Listener {
    private EasyCommandBlocker plugin;
    public PlayerListenerNew(EasyCommandBlocker plugin){
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCommandSend(PlayerCommandSendEvent event){
        Player player = event.getPlayer();

        if(player.isOp() || player.hasPermission("easycommandblocker.bypass.tab")){
            return;
        }
        CommandsManager commandsManager = plugin.getCommandsManager();
        List<String> allowedCommands = commandsManager.getTabCommands(OtherUtils.getPlayerPermissionsList(player));

        if(allowedCommands == null){
            event.getCommands().clear();
            return;
        }

        List<String> allowedBaseCommands = new ArrayList<>();
        for(String cmd : allowedCommands){
            String base = cmd.replaceFirst("/", "").split(" ")[0];
            allowedBaseCommands.add(base);
        }

        event.getCommands().removeIf(command -> !allowedBaseCommands.contains(command));
    }
}
