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

package arenakitpvp;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import arenakitpvp.arena.Arena;
import arenakitpvp.arena.Messages;
import arenakitpvp.commands.GameCommandsHandler;
import arenakitpvp.commands.setup.SetupCommandsHandler;

public class ArenaKitPVP extends JavaPlugin {

	private Arena arena = new Arena(this);

	public Arena getArena() {
		return arena;
	}

	@Override
	public void onEnable() {
		Messages.loadMessages(this);
		getCommand("kitpvp").setExecutor(new GameCommandsHandler(this));
		getCommand("kitpvpsetup").setExecutor(new SetupCommandsHandler(this));
		arena.getStructureManager().loadFromConfig(this.getDataFolder());
		getServer().getPluginManager().registerEvents(new ArenaListeners(arena), this);
	}

	@Override
	public void onDisable() {
		for (Player player : arena.getPlayerHandler().getManager().getAllParticipantsCopy()) {
			arena.getPlayerHandler().leavePlayer(player, "Arena is disabling", "");
		}
		arena.getStructureManager().saveToConfig(this.getDataFolder());
	}

}
