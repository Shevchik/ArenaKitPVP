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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Kits {

	private HashMap<String, Kit> kits = new HashMap<String, Kit>();

	public boolean isKitExist(String name) {
		return kits.containsKey(name);
	}

	public HashSet<String> getKits() {
		return new HashSet<String>(kits.keySet());
	}

	public void registerKit(String name, Player player) {
		Kit kit = new Kit(player.getInventory().getArmorContents(), player.getInventory().getContents(), player.getActivePotionEffects());
		registerKit(name, kit);
	}

	public Kit getKit(String nane) {
		return kits.get(nane);
	}

	public void registerKit(String name, Kit kit) {
		kits.put(name, kit);
	}

	public void unregisterKit(String name) {
		kits.remove(name);
	}

	public void giveKit(String name, Player player) {
		try {
			kits.get(name).giveKit(player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class Kit {

		private ItemStack[] armor;
		private ItemStack[] items;
		private Collection<PotionEffect> effects;

		protected Kit() {
		}

		public Kit(ItemStack[] armor, ItemStack[] items, Collection<PotionEffect> effects) {
			this.armor = armor;
			this.items = items;
			this.effects = effects;
		}

		public void giveKit(Player player) {
			player.getInventory().setArmorContents(armor);
			player.getInventory().setContents(items);
			for (PotionEffect effect : player.getActivePotionEffects()) {
				player.removePotionEffect(effect.getType());
			}
			player.addPotionEffects(effects);
			player.updateInventory();
		}

		@SuppressWarnings("unchecked")
		public void loadFromConfig(FileConfiguration config, String path) {
			armor = config.getList(path+".armor").toArray(new ItemStack[1]);
			items = config.getList(path+".items").toArray(new ItemStack[1]);
			effects = (Collection<PotionEffect>) config.getList(path+".effects");
		}

		public void saveToConfig(FileConfiguration config, String path) {
			config.set(path+".armor", Arrays.asList(armor));
			config.set(path+".items", Arrays.asList(items));
			config.set(path+".effects", effects);
		}

	}

	public void loadFromConfig(File datafolder) {
		if (new File(datafolder, "kits.yml").exists()) {
			YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(datafolder, "kits.yml"));
			for (String name : config.getKeys(false)) {
				Kit kit = new Kit();
				kit.loadFromConfig(config, name);
				kits.put(name, kit);
			}
		} else {
			loadDefaultKits();
		}
	}

	private void loadDefaultKits() {
		kits.put("knight", new Kit(
			new ItemStack[] {
				ItemStackBuilder.builder().setType(Material.GOLD_HELMET).build(),
				ItemStackBuilder.builder().setType(Material.GOLD_CHESTPLATE).build(),
				ItemStackBuilder.builder().setType(Material.GOLD_LEGGINGS).build(),
				ItemStackBuilder.builder().setType(Material.GOLD_BOOTS).build()
			},
			new ItemStack[] {
				ItemStackBuilder.builder().setType(Material.IRON_SWORD).build()
			},
			new ArrayList<PotionEffect>()
		));
		kits.put("archer", new Kit(
			new ItemStack[] {
				ItemStackBuilder.builder().setType(Material.LEATHER_HELMET).build(),
				ItemStackBuilder.builder().setType(Material.LEATHER_CHESTPLATE).build(),
				ItemStackBuilder.builder().setType(Material.LEATHER_LEGGINGS).build(),
				ItemStackBuilder.builder().setType(Material.LEATHER_BOOTS).build()
			},
			new ItemStack[] {
				ItemStackBuilder.builder().setType(Material.BOW).addEnchantment(Enchantment.ARROW_KNOCKBACK, 1).build(),
				ItemStackBuilder.builder().setType(Material.GOLD_SWORD).build(),
				ItemStackBuilder.builder().setType(Material.ARROW).setAmount(64).build()
			},
			new ArrayList<PotionEffect>(Arrays.asList(
				new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 1)
			))
		));
		kits.put("pyro", new Kit(
			new ItemStack[] {
				ItemStackBuilder.builder().setType(Material.GOLD_HELMET).build(),
				ItemStackBuilder.builder().setType(Material.GOLD_CHESTPLATE).build(),
				ItemStackBuilder.builder().setType(Material.GOLD_LEGGINGS).build(),
				ItemStackBuilder.builder().setType(Material.GOLD_BOOTS).build()
			},
			new ItemStack[] {
				ItemStackBuilder.builder().setType(Material.IRON_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 1).addEnchantment(Enchantment.FIRE_ASPECT, 1).build()
			},
			new ArrayList<PotionEffect>(Arrays.asList(
				new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0)
			))
		));
		kits.put("ghost", new Kit(
			new ItemStack[4],
			new ItemStack[] {
				ItemStackBuilder.builder().setType(Material.DIAMOND_SWORD).build()
			},
			new ArrayList<PotionEffect>(Arrays.asList(
				new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1)
			))
		));
		kits.put("cactus", new Kit(
			new ItemStack[] {
				ItemStackBuilder.builder().setType(Material.LEATHER_HELMET).addEnchantment(Enchantment.THORNS, 15).addEnchantment(Enchantment.DURABILITY, 10).build(),
				ItemStackBuilder.builder().setType(Material.LEATHER_CHESTPLATE).addEnchantment(Enchantment.THORNS, 15).addEnchantment(Enchantment.DURABILITY, 10).build(),
				ItemStackBuilder.builder().setType(Material.LEATHER_LEGGINGS).addEnchantment(Enchantment.THORNS, 15).addEnchantment(Enchantment.DURABILITY, 10).build(),
				ItemStackBuilder.builder().setType(Material.LEATHER_BOOTS).addEnchantment(Enchantment.THORNS, 15).addEnchantment(Enchantment.DURABILITY, 10).build()
			},
			new ItemStack[] {
				new ItemStack(Material.STONE_SWORD)
			},
			new ArrayList<PotionEffect>(Arrays.asList(
				new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1)
			))
		));
		kits.put("tank", new Kit(
			new ItemStack[] {
				ItemStackBuilder.builder().setType(Material.DIAMOND_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addEnchantment(Enchantment.DURABILITY, 10).build(),
				ItemStackBuilder.builder().setType(Material.DIAMOND_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addEnchantment(Enchantment.DURABILITY, 10).build(),
				ItemStackBuilder.builder().setType(Material.DIAMOND_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addEnchantment(Enchantment.DURABILITY, 10).build(),
				ItemStackBuilder.builder().setType(Material.DIAMOND_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addEnchantment(Enchantment.DURABILITY, 10).build(),
			},
			new ItemStack[] {
				new ItemStack(Material.WOOD_SWORD)
			},
			new ArrayList<PotionEffect>(Arrays.asList(
				new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 0)
			))
		));
		kits.put("berserk", new Kit(
			new ItemStack[] {
				ItemStackBuilder.builder().setType(Material.LEATHER_HELMET).build(),
				ItemStackBuilder.builder().setType(Material.LEATHER_CHESTPLATE).build(),
				ItemStackBuilder.builder().setType(Material.LEATHER_LEGGINGS).build(),
				ItemStackBuilder.builder().setType(Material.LEATHER_BOOTS).build()
			},
			new ItemStack[] {
				ItemStackBuilder.builder().setType(Material.DIAMOND_SWORD).addEnchantment(Enchantment.KNOCKBACK, 2).addEnchantment(Enchantment.DAMAGE_ALL, 5).build()
			},
			new ArrayList<PotionEffect>(Arrays.asList(
				new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1)
			))
		));
	}

	public void saveToConfig(File datafolder) {
		YamlConfiguration config = new YamlConfiguration();
		for (String name : kits.keySet()) {
			kits.get(name).saveToConfig(config, name);
		}
		try {
			config.save(new File(datafolder, "kits.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
