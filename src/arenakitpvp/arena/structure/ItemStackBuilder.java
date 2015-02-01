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

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ItemStackBuilder {

	private boolean canmodify = true;

	private ItemStack itemstack = new ItemStack(Material.AIR);
	private ItemStackBuilder() {
	}

	public ItemStackBuilder setType(Material material) {
		checkModify();
		itemstack.setType(material);
		return this;
	}

	public ItemStackBuilder setAmount(int amount) {
		checkModify();
		itemstack.setAmount(amount);
		return this;
	}

	public ItemStackBuilder setData(int data) {
		checkModify();
		itemstack.setDurability((short) data);
		return this;
	}

	public ItemStackBuilder addEnchantment(Enchantment ench, int level) {
		checkModify();
		itemstack.addUnsafeEnchantment(ench, level);
		return this;
	}

	public ItemStack build() {
		checkModify();
		canmodify = false;
		return itemstack;
	}

	private void checkModify() {
		if (!canmodify) {
			throw new IllegalArgumentException("ItemStack already built");
		}
	}

	public static ItemStackBuilder builder() {
		return new ItemStackBuilder();
	}

}
