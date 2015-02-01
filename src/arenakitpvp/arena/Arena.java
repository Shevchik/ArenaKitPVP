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

import arenakitpvp.ArenaKitPVP;
import arenakitpvp.arena.players.PlayerHandler;
import arenakitpvp.arena.structure.StructureManager;

public class Arena {

	private ArenaKitPVP plugin;
	public ArenaKitPVP getPlugin() {
		return plugin;
	}

	public Arena(ArenaKitPVP plugin) {
		this.plugin = plugin;
	}

	private PlayerHandler playerHandler = new PlayerHandler(this);

	public PlayerHandler getPlayerHandler() {
		return playerHandler;
	}

	private StructureManager sctructureManager = new StructureManager();

	public StructureManager getStructureManager() {
		return sctructureManager;
	}

	private GameHandler gameHandler = new GameHandler(this);

	public GameHandler getGameHandler() {
		return gameHandler;
	}

}
