package org.kokakiwi.ssell.fortressassault.entities;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DefaultValues {
	public static final ItemStack[] defaultFortifyItems = new ItemStack[] {
		item(Material.OBSIDIAN),
		item(Material.IRON_PICKAXE),
		item(Material.IRON_AXE),
		item(Material.IRON_SPADE),
		item(Material.STONE, 64),
		item(Material.STONE, 64),
		item(Material.COBBLESTONE, 64),
		item(Material.COBBLESTONE, 64),
		item(Material.COBBLESTONE, 64),
		item(Material.COBBLESTONE, 64),
		item(Material.COBBLESTONE, 64),
		item(Material.COBBLESTONE, 64),
		item(Material.WOOD, 64),
		item(Material.TORCH, 64)
	};
	
	public static final ItemStack[] defaultAssaultItems = new ItemStack[] {
		item(Material.IRON_SWORD),
		item(Material.BOW),
		item(Material.STONE_PICKAXE),
		item(Material.TNT, 3),
		item(Material.LADDER, 6),
		item(Material.MUSHROOM_SOUP),
		item(Material.COOKED_FISH),
		item(Material.BREAD),
		item(Material.ARROW, 64),
		item(Material.ARROW, 64)
	};
	
	public static final ItemStack[] defaultFortifyEquipment = new ItemStack[] {
		item(Material.AIR),
		item(Material.AIR),
		item(Material.AIR),
		item(Material.AIR)
	};
	
	public static final ItemStack[] defaultAssaultEquipment = new ItemStack[] {
		item(Material.CHAINMAIL_HELMET),
		item(Material.CHAINMAIL_CHESTPLATE),
		item(Material.CHAINMAIL_LEGGINGS),
		item(Material.CHAINMAIL_BOOTS)
	};
	
	private static ItemStack item(Material id)
	{
		return new ItemStack(id, 1);
	}
	
	private static ItemStack item(Material id, int num)
	{
		return new ItemStack(id, num);
	}
}
