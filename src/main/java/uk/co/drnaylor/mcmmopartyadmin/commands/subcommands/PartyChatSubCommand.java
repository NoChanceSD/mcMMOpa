package uk.co.drnaylor.mcmmopartyadmin.commands.subcommands;

import com.gmail.nossr50.api.ChatAPI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.drnaylor.mcmmopartyadmin.PartyAdmin;
import uk.co.drnaylor.mcmmopartyadmin.Util;
import uk.co.drnaylor.mcmmopartyadmin.dualcommandexecutor.DualSubCommandInterface;
import uk.co.drnaylor.mcmmopartyadmin.locales.L10n;

public class PartyChatSubCommand implements DualSubCommandInterface {

    private List<String> permissions = new ArrayList<>();
    
    public PartyChatSubCommand() {
        permissions.add("mcmmopartyadmin.admin");
    }

    @Override
	public List<String> getSubCommands() {
        return Arrays.asList("chat", "pc");
    }

    @Override
	public String getShortHelp() {
        return ChatColor.YELLOW + "/partyadmin pc <party> " + ChatColor.WHITE + "- " + L10n.getString("Description.PartyChat");
    }

    @Override
	public String[] getLongHelp() {
        return new String[] { getShortHelp() };
    }

    @Override
	public List<String> getPermissions() {
        return permissions;
    }

    @Override
	public boolean checkPermissions(CommandSender sender) {
        if (!(sender instanceof Player) || sender.isOp()) return true;
        
        for (String p : permissions) {
            if (sender.hasPermission(p)) return true;
        }
        
        return false;
    }

    @Override
	public void executeSubCommand(CommandSender sender, String[] cmdargs) {
        
            if (cmdargs.length > 1) {
                StringBuilder a = new StringBuilder();

                for (int i = 1; i < cmdargs.length; i++) {
                    a.append(cmdargs[i]);
                    if (i != cmdargs.length - 1) {
                        a.append(" ");
                    }
                }

                partyChat(sender, cmdargs[0], a.toString());
                return;
            }
            sender.sendMessage(ChatColor.RED + "Invalid Usage!");
            sender.sendMessage(getLongHelp());
    }

    @Override
	public List<String> onSubCommandTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return Util.getPartyCollection();
        }
        return null;
    }
    
    /**
     * Send a message to the specified party.
     *
     * @param sender Player sending the message
     * @param args
     */
    private void partyChat(CommandSender sender, String party, String message) {
        if (Util.getPartyFromList(party) == null) {
            sender.sendMessage(L10n.getString("Party.DoesNotExist", party));
            return;
        }

        if (!(sender instanceof Player)) {
            ChatAPI.sendPartyChat(PartyAdmin.getPlugin(), L10n.getString("Console.Name"), party, message);
        } else {
            ChatAPI.sendPartyChat(PartyAdmin.getPlugin(), ((Player) sender).getDisplayName(), party, message);
        }

        if (sender instanceof Player) {
            Player send = (Player) sender;
            if (!PartyAdmin.getPlugin().getPartySpyHandler().isSpy(send)) {
                sender.sendMessage(L10n.getString("PartySpy.Off"));
                String p2 = ChatColor.GRAY + "[" + party + "] " + ChatColor.GREEN + " (" + ChatColor.WHITE + ((Player) sender).getDisplayName() + ChatColor.GREEN + ") ";
                sender.sendMessage(p2 + message);
            }
        }
    }
}
