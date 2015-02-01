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

package arenakitpvp.arena.players;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import arenakitpvp.arena.Arena;
import arenakitpvp.arena.Messages;

public class PlayerHandler {

	private PlayerDataStore storage = new PlayerDataStore();
	private PlayersManager manager = new PlayersManager();

	public PlayerDataStore getDataStorage() {
		return storage;
	}

	public PlayersManager getManager() {
		return manager;
	}

	private Arena arena;

	public PlayerHandler(Arena arena) {
		this.arena = arena;
	}

	public boolean checkJoin() {
		try {
			arena.getStructureManager().getWorld().getName();
			arena.getStructureManager().isInArenaBounds(new Location(null, 0, 0, 0));
			arena.getStructureManager().isInSafeZoneBounds(new Location(null, 0, 0, 0));
			arena.getStructureManager().getSpawnLocation().getY();
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	// spawn player on arena
	public void spawnPlayer(final Player player, String msgtoplayer, String msgtoarenaplayers) {
		// teleport player to arena
		storage.storePlayerLocation(player);
		player.teleport(arena.getStructureManager().getSpawnLocation());
		// set player visible to everyone
		for (Player aplayer : Bukkit.getOnlinePlayers()) {
			aplayer.showPlayer(player);
		}
		// change player status
		storage.storePlayerGameMode(player);
		player.setFlying(false);
		player.setAllowFlight(false);
		storage.storePlayerInventory(player);
		storage.storePlayerArmor(player);
		storage.storePlayerPotionEffects(player);
		storage.storePlayerHunger(player);
		// update inventory
		player.updateInventory();
		// send message to player
		Messages.sendMessage(player, msgtoplayer);
		// send message to other players
		for (Player oplayer : manager.getPlayers()) {
			msgtoarenaplayers = msgtoarenaplayers.replace("{PLAYER}", player.getName());
			Messages.sendMessage(oplayer, msgtoarenaplayers);
		}
		// set player on arena data
		manager.add(player);
	}

	// remove player from arena
	public void leavePlayer(Player player, String msgtoplayer, String msgtoarenaplayers) {
		// remove kit
		arena.getGameHandler().removeKit(player);
		// remove player on arena data
		manager.remove(player);
		// remove all potion effects
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
		// restore player status
		storage.restorePlayerHunger(player);
		storage.restorePlayerPotionEffects(player);
		storage.restorePlayerArmor(player);
		storage.restorePlayerInventory(player);
		storage.restorePlayerGameMode(player);
		// restore location or teleport to lobby
		storage.restorePlayerLocation(player);
		// update inventory
		player.updateInventory();
		// send message to player
		Messages.sendMessage(player, msgtoplayer);
		// send message to other players
		for (Player oplayer : manager.getAllParticipantsCopy()) {
			msgtoarenaplayers = msgtoarenaplayers.replace("{PLAYER}", player.getName());
			Messages.sendMessage(oplayer, msgtoarenaplayers);
		}
	}

}
