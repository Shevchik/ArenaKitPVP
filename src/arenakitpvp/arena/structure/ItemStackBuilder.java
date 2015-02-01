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
