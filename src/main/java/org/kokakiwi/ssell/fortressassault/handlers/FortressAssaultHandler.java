package org.kokakiwi.ssell.fortressassault.handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.kokakiwi.ssell.fortressassault.*;
import org.kokakiwi.ssell.fortressassault.entities.*;

public class FortressAssaultHandler {
	
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger("Minecraft");
	
	private FortressAssault plugin;
	
	private int phase = 0;
	private double timeLimit = 1.5;
	
	private Map<String, FAPlayer> playersList = new HashMap<String, FAPlayer>();
	private Map<String, ItemStack[]> inventoryList = new HashMap<String, ItemStack[]>();
	
	private FAPvPHandler pvpHandler;
	private FAGizmoHandler gizmoHandler;
	
	public FortressAssaultHandler(FortressAssault parent)
	{
		plugin = parent;
		pvpHandler = new FAPvPHandler(plugin);
		gizmoHandler = new FAGizmoHandler(plugin);
	}
	
	public FAPlayer getFAPlayer(Player player)
	{
		FAPlayer found = playersList.get(player.getName());
		
		if(found != null)
		{
			if(found.getPlayer().getEntityId() != player.getEntityId())
			{
				found.setPlayer(player);
			}
			
			return found;
		}else{
			return null;
		}
	}
	
	public void setTime(Player sender, double time)
	{
		if(time < 0)
			timeLimit = 1.5;
		else
			timeLimit = time;
		
		sender.sendMessage(ChatColor.GREEN + "Time Limit set to " + timeLimit + "min");
	}
	
	public void addPlayer(CommandSender sender, String team, String player)
	{
		Player tmpPlayer = plugin.getServer().getPlayer(player);
		
		if(tmpPlayer != null)
		{
			if(getFAPlayer(tmpPlayer) != null)
			{
				FAPlayer faPlayer = getFAPlayer(tmpPlayer);
				FATeam newTeam = FATeam.getTeam(team);
				if(newTeam != null)
				{
					faPlayer.setTeam(newTeam);
					plugin.getServer().broadcastMessage(ChatColor.GREEN + faPlayer.getName() + " changed to " +
							newTeam.toString() + " team.");
				}else{
					sender.sendMessage(ChatColor.DARK_RED + "That is not a valid team.");
				}
			}else{
				FAPlayer faPlayer = new FAPlayer(tmpPlayer);
				FATeam newTeam = FATeam.getTeam(team);
				if(newTeam != null)
				{
					faPlayer.setTeam(newTeam);
					playersList.put(player, faPlayer);
					plugin.getServer().broadcastMessage(ChatColor.GREEN + faPlayer.getName() + " added to " +
							newTeam.toString() + " team.");
				}else{
					sender.sendMessage(ChatColor.DARK_RED + "That is not a valid team.");
				}
			}
		}else
			sender.sendMessage(ChatColor.DARK_RED + "Player not found.");
	}
	
	public void removePlayer(Player player)
	{
		FAPlayer faPlayer = getFAPlayer(player);
		if(faPlayer != null)
		{
			if(phase == 0)
			{
				playersList.remove(faPlayer.getName());
				inventoryList.remove(faPlayer.getName());
			}else{
				player.sendMessage(ChatColor.DARK_RED + "You can't leave the game until he isn't finished!");
			}
		}else{
			player.sendMessage(ChatColor.DARK_RED + "You're not ingame!");
		}
	}
	
	public void returnInventory()
	{
		for(FAPlayer faPlayer : playersList.values())
		{
			returnInventory(faPlayer.getPlayer());
		}
	}
	
	@SuppressWarnings("deprecation")
	public void returnInventory(Player player)
	{
		if(player != null)
		{
			PlayerInventory currentInventory = player.getInventory();
			currentInventory.clear();
			
			if(inventoryList.containsKey(player.getName()))
			{
				ItemStack[] oldInventory = inventoryList.get(player.getName());
				currentInventory.setContents(oldInventory);
				
				inventoryList.remove(oldInventory);
				
				player.updateInventory();
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void storeInventory(FAPlayer faPlayer)
	{
		Player player = faPlayer.getPlayer();
		
		returnInventory(player);
		
		ItemStack[] newInventory = player.getInventory().getContents();
		
		player.sendMessage(ChatColor.YELLOW
				+ "Storing your inventory. You will get it back after the event.");
		inventoryList.put(player.getName(), newInventory);
		player.getInventory().clear();
		player.updateInventory();
	}
	
	public void cleanUpPlayerList()
	{
		for(FAPlayer faPlayer : playersList.values())
		{
			if(!faPlayer.getPlayer().isOnline())
			{
				sayPlayers(
						ChatColor.DARK_RED + "Removing offline player "
								+ faPlayer.getName());
				playersList.remove(faPlayer);
			}
		}
	}
	
	public void sayPlayers(String message)
	{
		for(FAPlayer faPlayer : playersList.values())
		{
			faPlayer.getPlayer().sendMessage(message);
		}
	}
	
	public boolean startEvent(Player sender)
	{
		if(phase > 0)
		{
			sender.sendMessage(ChatColor.DARK_RED
					+ "You cannot start a game when one is already occuring!");

			return false;
		}else{
			if (playersList.size() == 0) {
				sender.sendMessage(ChatColor.DARK_RED + "You don't have any players");
				return false;
			}
			
			phase = 1;
			
			sayPlayers(ChatColor.YELLOW + "Start Fortifying! You have " + timeLimit + " minutes!");
			
			resetScoreBoard();
			cleanUpPlayerList();
			replaceInventoriesFortify();
			
			plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
				
				public void run() {
					beginAssault();
				}
			}, (long) (timeLimit * 60 * 20));
			
			plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
				
				public void run() {
					sayPlayers(ChatColor.YELLOW + "1 minute remaining!");
				}
			}, (long) (((timeLimit * 60) - 60) * 20));
			
			plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
				
				public void run() {
					sayPlayers(ChatColor.YELLOW + "30 seconds remainings. Don't forget your gizmo!");
				}
			}, (long) (((timeLimit * 60) - 30) * 20));
			
			plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
				
				public void run() {
					sayPlayers(ChatColor.YELLOW + "10 seconds remainings");
				}
			}, (long) (((timeLimit * 60) - 10) * 20));
		}
		
		return true;
	}
	
	public void stopEvent(Player sender)
	{
		if(phase > 0)
		{
			FAPlayer faPlayer = getFAPlayer(sender);
			if(faPlayer != null)
			{
				sayPlayers(ChatColor.YELLOW
						+ faPlayer.getName()
						+ " has stopped the current game of Fortress Assault.");
				plugin.getServer().getScheduler().cancelTasks(plugin);
				
				phase = 0;
				
				returnInventory();
				gizmoHandler.clearList();
			}else{
				sender.sendMessage(ChatColor.DARK_RED
						+ "You are not a member of the current game!");
			}
		}else{
			sender.sendMessage(ChatColor.DARK_RED
					+ "No Fortress Assault game occuring.");
		}
	}
	
	public void beginAssault()
	{
		phase = 2;
		
		if(gizmoHandler.gizmosPlaced() > 1)
		{
			sayPlayers(ChatColor.DARK_RED + "Begin your assault!");
			replaceInventoriesAssault();
		}else
			noGizmoGameOver();
	}
	
	public void noGizmoGameOver() {
		if(gizmoHandler.gizmosPlaced() == 0)
		{
			sayPlayers(ChatColor.GOLD + "Neither team placed a Gizmo! No winners.");
		}else{
			FAGizmo gizmo = gizmoHandler.getGizmo(gizmoHandler.getFirstTeam());
			
			sayPlayers(getTeamColor(gizmo.getTeam()) + gizmo.getTeam().toString() + " Team "
							+ ChatColor.GOLD
							+ "wins! Other team did not place a Gizmo!");
		}
		
		plugin.getServer().getScheduler().cancelTasks(plugin);
		phase = 0;
		returnInventory();
		gizmoHandler.clearList();
	}

	public void replaceInventoriesFortify()
	{
		for(FAPlayer faPlayer : playersList.values())
		{
			replaceInventoryFortify(faPlayer);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void replaceInventoryFortify(FAPlayer faPlayer)
	{
		storeInventory(faPlayer);
		faPlayer.getPlayer().getInventory().clear();
		
		faPlayer.getPlayer().getInventory().setHelmet(faPlayer.getTeam().getFortifyEquipment()[0]);
		faPlayer.getPlayer().getInventory().setChestplate(faPlayer.getTeam().getFortifyEquipment()[1]);
		faPlayer.getPlayer().getInventory().setLeggings(faPlayer.getTeam().getFortifyEquipment()[2]);
		faPlayer.getPlayer().getInventory().setBoots(faPlayer.getTeam().getFortifyEquipment()[3]);
		
		
		for(ItemStack item : faPlayer.getTeam().getFortifyItems())
		{
			faPlayer.getPlayer().getInventory().addItem(item);
		}
		faPlayer.getPlayer().updateInventory();
	}
	
	public void replaceInventoriesAssault()
	{
		for(FAPlayer faPlayer : playersList.values())
		{
			replaceInventoryAssault(faPlayer);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void replaceInventoryAssault(FAPlayer faPlayer)
	{
		faPlayer.getPlayer().getInventory().clear();
		
		faPlayer.getPlayer().getInventory().setHelmet(faPlayer.getTeam().getAssaultEquipment()[0]);
		faPlayer.getPlayer().getInventory().setChestplate(faPlayer.getTeam().getAssaultEquipment()[1]);
		faPlayer.getPlayer().getInventory().setLeggings(faPlayer.getTeam().getAssaultEquipment()[2]);
		faPlayer.getPlayer().getInventory().setBoots(faPlayer.getTeam().getAssaultEquipment()[3]);
		
		for(ItemStack item : faPlayer.getTeam().getAssaultItems())
		{
			faPlayer.getPlayer().getInventory().addItem(item);
		}
		faPlayer.getPlayer().updateInventory();
	}
	
	public void resetScoreBoard()
	{
		for(FAPlayer faPlayer : playersList.values())
		{
			faPlayer.setDeaths(0);
			faPlayer.setKills(0);
			faPlayer.setDestructions(0);
		}
	}
	
	public void gameOver()
	{
		plugin.getServer().getScheduler().cancelTasks(plugin);
		phase = 0;
		returnInventory();
		gizmoHandler.clearList();
	}

	public FAPvPHandler getPvpHandler() {
		return pvpHandler;
	}

	public FAGizmoHandler getGizmoHandler() {
		return gizmoHandler;
	}
	
	public ChatColor getTeamColor(FATeam team) {
		return team.getColor();
	}

	public int getPhase() {
		return phase;
	}
}
