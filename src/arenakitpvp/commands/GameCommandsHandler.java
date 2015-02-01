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

package arenakitpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import arenakitpvp.ArenaKitPVP;
import arenakitpvp.arena.Arena;
import arenakitpvp.arena.Messages;
import arenakitpvp.arena.structure.Kits.Kit;

public class GameCommandsHandler implements CommandExecutor {

	private ArenaKitPVP plugin;

	public GameCommandsHandler(ArenaKitPVP plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("A player is expected");
			return true;
		}
		Player player = (Player) sender;
		// handle commands
		// help command
		if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
			sender.sendMessage("/kitpvp join - join arena");
			sender.sendMessage("/kitpvp leave - leave current arena");
			return true;
		}
		// join arena
		else if (args.length == 1 && args[0].equalsIgnoreCase("join")) {
			Arena arena = plugin.getArena();
			boolean canJoin = arena.getPlayerHandler().checkJoin();
			if (canJoin) {
				arena.getPlayerHandler().spawnPlayer(player, Messages.playerjoinedtoplayer, Messages.playerjoinedtoothers);
			} else {
				Messages.sendMessage(player, Messages.cantjoin);
			}
			return true;
		}
		// leave arena
		else if (args.length == 1 && args[0].equalsIgnoreCase("leave")) {
			Arena arena = plugin.getArena();
			if (arena.getPlayerHandler().getManager().isInArena(player.getName())) {
				arena.getPlayerHandler().leavePlayer(player, Messages.playerlefttoplayer, Messages.playerlefttoothers);
				return true;
			} else {
				Messages.sendMessage(player, Messages.notinarena);
				return true;
			}
		}
		// select kit
		else if (args.length == 2 && args[0].equals("kit")) {
			Arena arena = plugin.getArena();
			if (arena.getPlayerHandler().getManager().isInArena(player.getName())) {
				if (arena.getStructureManager().getKits().isKitExist(args[1])) {
					if (arena.getStructureManager().isInSafeZoneBounds(player.getLocation())) {
						Kit kit = arena.getStructureManager().getKits().getKit(args[1]);
						arena.getGameHandler().setKit(player, kit);
						kit.giveKit(player);
						Messages.sendMessage(player, Messages.kitgiven.replace("{KITNAME}", args[1]));
						return true;
					} else {
						Messages.sendMessage(player, Messages.kitshouldbeinsafezone);
						return true;
					}
				} else {
					Messages.sendMessage(player, Messages.kitdoesnotexist.replace("{KITNAME}", args[1]));
					return true;
				}
			} else {
				Messages.sendMessage(player, Messages.notinarena);
				return true;
			}
		}
		return false;
	}

}
