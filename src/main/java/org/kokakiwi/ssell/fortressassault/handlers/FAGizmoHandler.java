package org.kokakiwi.ssell.fortressassault.handlers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.kokakiwi.ssell.fortressassault.*;
import org.kokakiwi.ssell.fortressassault.entities.*;

public class FAGizmoHandler {
	
	private FortressAssault plugin;
	
	private Map<FATeam, FAGizmo> gizmoList = new HashMap<FATeam, FAGizmo>();
	private int teamCount = 0;
	private FATeam firstTeam = null;
	
	public FAGizmoHandler(FortressAssault parent)
	{
		plugin = parent;
	}
	
	public boolean addGizmo(FAPlayer player, Block block)
	{
		if(!gizmoList.containsKey(player.getTeam()))
		{
			FAGizmo gizmo = new FAGizmo(player.getTeam(), block);
			
			plugin.getHandler().sayPlayers(
					plugin.getHandler().getTeamColor(player.getTeam())
							+ player.getTeam().toString()
							+ " Team has placed their Gizmo!");
			gizmoList.put(player.getTeam(), gizmo);
			teamCount++;
			
			if(teamCount == 1)
			{
				firstTeam = player.getTeam();
			}
			
			return true;
		}else{
			player.getPlayer().sendMessage(ChatColor.DARK_RED
					+ "There is already a Gizmo for your team!");
			
			return false;
		}
	}
	
	public int gizmosPlaced()
	{
		return gizmoList.size();
	}
	
	public boolean isGizmo(Block block)
	{
		for(FAGizmo gizmo : gizmoList.values())
		{
			if(gizmo.getBlock() == block)
				return true;
		}
		
		return false;
	}
	
	public FATeam getFirstTeam()
	{
		return firstTeam;
	}
	
	public void gizmoHit(FAPlayer player, FATeam team)
	{
		FAGizmo gizmo = gizmoList.get(team);
		
		if(player.getTeam() == team)
		{
			if(gizmo.isDestructing())
			{
				gizmo.setDestroyer(null);
				gizmo.setDestructing(false);
				
				plugin.getHandler().sayPlayers(plugin.getHandler().getTeamColor(player.getTeam()) + player.getName()
								+ " saved the " + player.getTeam().toString()
								+ " Gizmo!");
			}
		}else{
			if(!gizmo.isDestructing())
			{
				gizmo.setDestroyer(player);
				gizmo.setDestructing(true);
				
				plugin.getHandler().sayPlayers(plugin.getHandler().getTeamColor(player.getTeam()) + player.getName()
								+ " attacked the " + gizmo.getTeam().toString()
								+ " Gizmo!");
				
				final FAGizmo finalGizmo = gizmo;
				plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
					public void run() {
						destroyGizmo(finalGizmo);
					}
				}, 100); // 5 seconds
			}
		}
	}

	public void destroyGizmo(FAGizmo gizmo) {
		if(gizmo.isDestructing())
		{
			FAPlayer destroyer = gizmo.getDestroyer();
			plugin.getHandler().getPvpHandler().destructionEvent(destroyer.getPlayer(), gizmo);
			teamCount--;
			
			if(teamCount == 1)
			{
				plugin.getHandler().sayPlayers(plugin.getHandler().getTeamColor(destroyer.getTeam())
							+ destroyer.getTeam().toString() + " Team "
							+ ChatColor.GOLD + "wins!");
				plugin.getHandler().gameOver();
			}else{
				plugin.getHandler().sayPlayers(plugin.getHandler().getTeamColor(gizmo.getTeam())
							+ gizmo.getTeam().toString() + " Team "
							+ ChatColor.DARK_PURPLE + "lose.");
			}
		}
	}
	
	public void clearList()
	{
		for(FAGizmo gizmo : gizmoList.values())
			gizmo.getBlock().setType(Material.AIR);
		
		gizmoList.clear();
		firstTeam = null;
		teamCount = 0;
	}
	
	public FAGizmo getGizmo(FATeam team)
	{
		return gizmoList.get(team);
	}
}
