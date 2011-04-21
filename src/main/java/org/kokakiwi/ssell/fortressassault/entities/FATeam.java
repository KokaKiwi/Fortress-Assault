package org.kokakiwi.ssell.fortressassault.entities;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum FATeam {
	
	NONE(),
	RED(ChatColor.RED),
	BLUE(ChatColor.BLUE),
	HUMAN(ChatColor.AQUA),
	ZOMBIE(ChatColor.GREEN);
	
	private static final Map<String, FATeam> lookupName = new HashMap<String, FATeam>();
	
	private final ItemStack[] fortifyItems;
	private final ItemStack[] assaultItems;
	private final ItemStack[] fortifyEquipment;
	private final ItemStack[] assaultEquipment;
	private final ChatColor color;
	
	private FATeam()
	{
		this(ChatColor.GRAY);
	}
	
	private FATeam(final ChatColor color)
	{
		this(color, DefaultValues.defaultFortifyItems, DefaultValues.defaultAssaultItems);
	}
	
	private FATeam(final ChatColor color, final ItemStack[] fortifyItems, final ItemStack[] assaultItems)
	{
		this(color, fortifyItems, assaultItems, 
				DefaultValues.defaultFortifyEquipment, DefaultValues.defaultAssaultEquipment);
	}
	
	private FATeam(final ChatColor color, final ItemStack[] fortifyItems, final ItemStack[] assaultItems,
						final ItemStack[] fortifyEquipment, final ItemStack[] assaultEquipment)
	{
		this.color = color;
		this.fortifyItems = fortifyItems;
		this.assaultItems = assaultItems;
		this.fortifyEquipment = fortifyEquipment;
		this.assaultEquipment = assaultEquipment;
	}
	
	public static FATeam getTeam(String teamName)
	{
		String teamId = teamName.toUpperCase();
		return lookupName.get(teamId);
	}

	public ItemStack[] getFortifyItems() {
		return fortifyItems;
	}

	public ItemStack[] getAssaultItems() {
		return assaultItems;
	}
	
	public ItemStack[] getFortifyEquipment() {
		return fortifyEquipment;
	}

	public ItemStack[] getAssaultEquipment() {
		return assaultEquipment;
	}

	public ChatColor getColor() {
		return color;
	}
	
	@SuppressWarnings("unused")
	private static ItemStack item(Material id)
	{
		return new ItemStack(id, 1);
	}
	
	@SuppressWarnings("unused")
	private static ItemStack item(Material id, int num)
	{
		return new ItemStack(id, num);
	}

	static
	{
		for(FATeam team : values())
		{
			lookupName.put(team.name(), team);
		}
	}
}
