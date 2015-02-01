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

package arenakitpvp.arena;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import arenakitpvp.arena.structure.Kits.Kit;

public class GameHandler {

	private Arena arena;

	public GameHandler(Arena arena) {
		this.arena = arena;
	}

	private HashMap<String, Kit> playerKits = new HashMap<String, Kit>();

	public void setKit(Player player, Kit kit) {
		playerKits.put(player.getName(), kit);
	}

	public void removeKit(Player player) {
		playerKits.remove(player.getName());
	}

	public void handleDeath(PlayerDeathEvent event) {
		event.getDrops().clear();
		final Player player = event.getEntity();
		player.setHealth(player.getMaxHealth());
		player.setFoodLevel(20);
		Bukkit.getScheduler().runTask(arena.getPlugin(), new Runnable() {
			@Override
			public void run() {
				if (arena.getPlayerHandler().getManager().isInArena(player.getName())) {
					player.teleport(arena.getStructureManager().getSpawnLocation());
					if (playerKits.containsKey(player.getName())) {
						playerKits.get(player.getName()).giveKit(player);
					}
				}
			}
		});
	}

	public void handleMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (!arena.getStructureManager().isInArenaBounds(event.getTo())) {
			arena.getPlayerHandler().leavePlayer(player, Messages.playerlefttoplayer, Messages.playerlefttoothers);
		}
	}

	public void handleDamage(EntityDamageEvent event) {
		Player entity = (Player) event.getEntity();
		if (arena.getStructureManager().isInSafeZoneBounds(entity.getLocation())) {
			event.setCancelled(true);
		}
	}

}
