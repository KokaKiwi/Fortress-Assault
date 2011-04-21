package org.kokakiwi.ssell.fortressassault.events;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.kokakiwi.ssell.fortressassault.FortressAssault;
import org.kokakiwi.ssell.fortressassault.entities.FAGizmo;
import org.kokakiwi.ssell.fortressassault.entities.FAPlayer;

public class FAPlayerListener extends PlayerListener {

	private FortressAssault plugin;

	public FAPlayerListener(FortressAssault fortressAssault) {
		plugin = fortressAssault;
	}

	@Override
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		FAPlayer faPlayer = plugin.getHandler().getFAPlayer(player);
		if(faPlayer != null)
		{
			if(plugin.getHandler().getPhase() == 1)
			{
				plugin.getHandler().replaceInventoryFortify(faPlayer);
			}else if(plugin.getHandler().getPhase() == 2) {
				plugin.getHandler().replaceInventoryAssault(faPlayer);
			}
		}
	}

	@Override
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		FAPlayer faPlayer = plugin.getHandler().getFAPlayer(player);
		if(faPlayer != null)
		{
			if(plugin.getHandler().getPhase() == 1)
			{
				
			}else if(plugin.getHandler().getPhase() == 2) {
				
			}
		}
	}

	@Override
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		final Player player = event.getPlayer();
		final FAPlayer faPlayer = plugin.getHandler().getFAPlayer(player);
		
		if(faPlayer != null)
		{
			plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
				
				public void run() {
					if(plugin.getHandler().getPhase() == 1)
					{
						plugin.getHandler().replaceInventoryFortify(faPlayer);
					}else if(plugin.getHandler().getPhase() == 2) {
						plugin.getHandler().replaceInventoryAssault(faPlayer);
					}
					
					FAGizmo gizmo = plugin.getHandler().getGizmoHandler().getGizmo(faPlayer.getTeam());
					Block gizmoBlock = gizmo.getBlock();
					Location spawnLocation = gizmoBlock.getLocation();
					spawnLocation.setY(spawnLocation.getY() + 1);
					
					player.teleport(spawnLocation);
				}
			}, 20);
		}
	}

}
