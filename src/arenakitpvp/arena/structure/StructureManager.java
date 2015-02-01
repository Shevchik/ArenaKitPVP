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

package arenakitpvp.arena.structure;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.Vector;

import arenakitpvp.selectionget.PlayerCuboidSelection;

public class StructureManager {

	private String worldname;
	private Vector arenaMin;
	private Vector arenaMax;

	private Vector spawnpoint;

	private Vector safeZoneMin;
	private Vector safeZoneMax;

	public World getWorld() {
		return Bukkit.getWorld(worldname);
	}

	public boolean isInArenaBounds(Location location) {
		return location.toVector().isInAABB(arenaMin, arenaMax);
	}

	public Location getSpawnLocation() {
		return new Location(getWorld(), spawnpoint.getX(), spawnpoint.getY(), spawnpoint.getZ());
	}

	public boolean isInSafeZoneBounds(Location location) {
		return location.toVector().isInAABB(safeZoneMin, safeZoneMax);
	}

	public void setArenaBounds(PlayerCuboidSelection selection) {
		worldname = selection.getMinimumLocation().getWorld().getName();
		arenaMin = selection.getMinimumLocation().toVector();
		arenaMax = selection.getMaximumLocation().toVector();
	}

	public boolean setSpawnLocation(Location location) {
		if (isInArenaBounds(location)) {
			spawnpoint = location.toVector();
			return true;
		}
		return false;
	}

	public boolean setSafeZoneBounds(PlayerCuboidSelection selection) {
		if (isInArenaBounds(selection.getMinimumLocation()) && isInArenaBounds(selection.getMaximumLocation())) {
			safeZoneMin = selection.getMinimumLocation().toVector();
			safeZoneMax = selection.getMaximumLocation().toVector();
			return true;
		}
		return false;
	}

	private Kits kits = new Kits();

	public Kits getKits() {
		return kits;
	}


	public void loadFromConfig(File datafolder) {
		FileConfiguration config = YamlConfiguration.loadConfiguration(new File(datafolder, "arena.yml"));
		// load arena world location
		worldname = config.getString("world", null);
		// load arena bounds
		arenaMin = config.getVector("arenaMin", null);
		arenaMax = config.getVector("arenaMax", null);
		// load spawnpoint
		spawnpoint = config.getVector("spawnpoint", null);
		// load safezone
		safeZoneMin = config.getVector("safeZoneMin", null);
		safeZoneMax = config.getVector("safeZoneMax", null);
		// load kits
		kits.loadFromConfig(datafolder);
	}

	public void saveToConfig(File datafolder) {
		FileConfiguration config = new YamlConfiguration();
		// save arena bounds
		try {
			config.set("world", worldname);
			config.set("arenaMin", arenaMin);
			config.set("arenaMax", arenaMax);
		} catch (Exception e) {
		}
		// save spawnpoint
		try {
			config.set("spawnpoint", spawnpoint);
		} catch (Exception e) {
		}
		// save safezone
		try {
			config.set("safeZoneMin", safeZoneMin);
			config.set("safeZoneMax", safeZoneMax);
		} catch (Exception e) {
		}
		// save kits
		kits.saveToConfig(datafolder);
		try {
			config.save(new File(datafolder, "arena.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
