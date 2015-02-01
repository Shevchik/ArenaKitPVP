/**
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package arenakitpvp.commands.setup;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import arenakitpvp.ArenaKitPVP;
import arenakitpvp.arena.Messages;
import arenakitpvp.commands.CommandHandlerInterface;
import arenakitpvp.commands.setup.arena.AddKit;
import arenakitpvp.commands.setup.arena.DeleteKit;
import arenakitpvp.commands.setup.arena.SetArena;
import arenakitpvp.commands.setup.arena.SetSafeZone;
import arenakitpvp.commands.setup.arena.SetSpawn;
import arenakitpvp.commands.setup.selection.Clear;
import arenakitpvp.commands.setup.selection.SetP1;
import arenakitpvp.commands.setup.selection.SetP2;
import arenakitpvp.selectionget.PlayerSelection;

public class SetupCommandsHandler implements CommandExecutor {

	private PlayerSelection plselection = new PlayerSelection();

	private HashMap<String, CommandHandlerInterface> commandHandlers = new HashMap<String, CommandHandlerInterface>();

	public SetupCommandsHandler(ArenaKitPVP plugin) {
		commandHandlers.put("setp1", new SetP1(plselection));
		commandHandlers.put("setp2", new SetP2(plselection));
		commandHandlers.put("clear", new Clear(plselection));
		commandHandlers.put("setarena", new SetArena(plugin, plselection));
		commandHandlers.put("setspawn", new SetSpawn(plugin));
		commandHandlers.put("setsafezone", new SetSafeZone(plugin, plselection));
		commandHandlers.put("addkit", new AddKit(plugin));
		commandHandlers.put("deleteKit", new DeleteKit(plugin));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Player is expected");
			return true;
		}
		Player player = (Player) sender;
		// check permissions
		if (!player.hasPermission("arenakitpvp.setup")) {
			Messages.sendMessage(player, Messages.nopermission);
			return true;
		}
		// get command
		if (args.length > 0 && commandHandlers.containsKey(args[0])) {
			CommandHandlerInterface commandh = commandHandlers.get(args[0]);
			//check args length
			if (args.length - 1 < commandh.getMinArgsLength()) {
				Messages.sendMessage(player, ChatColor.RED+"Not enough args");
				return false;
			}
			//execute command
			boolean result = commandh.handleCommand(player, Arrays.copyOfRange(args, 1, args.length));
			return result;
		}
		return false;
	}

}
