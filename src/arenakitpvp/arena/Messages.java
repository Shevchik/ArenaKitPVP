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

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import arenakitpvp.ArenaKitPVP;

public class Messages {

	public static String nopermission = "&4You don't have permission to do this";
	public static String notinarena = "&4You are not in arena";
	public static String cantjoin = "&4Can't join an arena, it is not setup yet";

	public static String playerjoinedtoplayer = "&6You joined the arena";
	public static String playerjoinedtoothers = "&6Player {PLAYER} joined the arena";
	public static String playerlefttoplayer = "&6You left the arena";
	public static String playerlefttoothers = "&6Player {PLAYER} left the game";

	public static String kitgiven = "&6You chose kit {KITNAME}";
	public static String kitdoesnotexist = "&4Kit {KITNAME} doesn't exist";
	public static String kitshouldbeinsafezone = "&4You should be in a safezone to choose kit";

	public static void sendMessage(Player player, String message) {
		if (!message.equals("")) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
		}
	}

	public static void broadcastMessage(String message) {
		if (!message.equals("")) {
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
		}
	}

	public static void loadMessages(ArenaKitPVP plugin) {
		File messageconfig = new File(plugin.getDataFolder(), "configmsg.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(messageconfig);
		nopermission = config.getString("nopermission", nopermission);
		notinarena = config.getString("notinarena", notinarena);
		cantjoin = config.getString("cantjoin", cantjoin);;
		playerjoinedtoplayer = config.getString("playerjoinedtoplayer", playerjoinedtoplayer);
		playerjoinedtoothers = config.getString("playerjoinedtoothers", playerjoinedtoothers);
		playerlefttoplayer = config.getString("playerlefttoplayer", playerlefttoplayer);
		playerlefttoothers = config.getString("playerlefttoothers", playerlefttoothers);
		kitgiven = config.getString("kitgiven", kitgiven);
		kitdoesnotexist = config.getString("kitdoesnotexist", kitdoesnotexist);
		kitshouldbeinsafezone = config.getString("kitshouldbeinsafezone", kitshouldbeinsafezone);
		saveMessages(messageconfig);
	}

	private static void saveMessages(File messageconfig) {
		FileConfiguration config = new YamlConfiguration();
		config.set("nopermission", nopermission);
		config.set("notinarena", notinarena);
		config.set("cantjoin", cantjoin);
		config.set("playerjoinedtoplayer", playerjoinedtoplayer);
		config.set("playerjoinedtoothers", playerjoinedtoothers);
		config.set("playerlefttoplayer", playerlefttoplayer);
		config.set("playerlefttoothers", playerlefttoothers);
		config.set("kitgiven", kitgiven);
		config.set("kitdoesnotexist", kitdoesnotexist);
		config.set("kitshouldbeinsafezone", kitshouldbeinsafezone);
		try {
			config.save(messageconfig);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
