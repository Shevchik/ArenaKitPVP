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

import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import arenakitpvp.arena.Arena;
import arenakitpvp.arena.Messages;

public class ArenaListeners implements Listener {

	private Arena arena;

	public ArenaListeners(Arena arena) {
		this.arena = arena;
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		if (arena.getPlayerHandler().getManager().isInArena(player.getName())) {
			arena.getGameHandler().handleDeath(event);
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (arena.getPlayerHandler().getManager().isInArena(player.getName())) {
			arena.getGameHandler().handleMove(event);
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			if (arena.getPlayerHandler().getManager().isInArena(player.getName())) {
				arena.getGameHandler().handleDamage(event);
			}
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (arena.getPlayerHandler().getManager().isInArena(player.getName())) {
			arena.getPlayerHandler().leavePlayer(player, Messages.playerlefttoplayer, Messages.playerlefttoothers);
		}
	}

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if (event.getMessage().startsWith("/kitpvp kit") || event.getMessage().startsWith("/kitpvp leave")) {
			return;
		}
		if (arena.getPlayerHandler().getManager().isInArena(player.getName()) && !player.hasPermission("arenakitpvp.cmdblockbypass")) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (arena.getPlayerHandler().getManager().isInArena(player.getName())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlace(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (arena.getPlayerHandler().getManager().isInArena(player.getName())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (arena.getPlayerHandler().getManager().isInArena(player.getName())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onSignClick(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		if (!(event.getClickedBlock().getState() instanceof Sign)) {
			return;
		}
		Player player = event.getPlayer();
		if (arena.getPlayerHandler().getManager().isInArena(player.getName())) {
			arena.getGameHandler().handleClick(event);
		}
	}

}
