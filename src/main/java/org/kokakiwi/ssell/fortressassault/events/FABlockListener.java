package org.kokakiwi.ssell.fortressassault.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.kokakiwi.ssell.fortressassault.FortressAssault;
import org.kokakiwi.ssell.fortressassault.entities.FAPlayer;

public class FABlockListener extends BlockListener {
	
	private FortressAssault plugin;
	
	public FABlockListener(FortressAssault plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public void onBlockDamage(BlockDamageEvent event) {
		Block block = event.getBlock();
		Player player = event.getPlayer();
		FAPlayer faPlayer = plugin.getHandler().getFAPlayer(player);
		if(faPlayer != null)
		{
			if(block.getType() == Material.OBSIDIAN)
			{
				if(plugin.getHandler().getPhase() == 2)
				{
					if(plugin.getHandler().getGizmoHandler().isGizmo(block))
					{
						plugin.getHandler().getGizmoHandler().gizmoHit(faPlayer, faPlayer.getTeam());
						event.setCancelled(true);
					}
				}else if(plugin.getHandler().getPhase() == 1) {
					if(plugin.getHandler().getGizmoHandler().isGizmo(block))
					{
						event.setCancelled(true);
					}
				}
			}
		}
	}

	@Override
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		FAPlayer faPlayer = plugin.getHandler().getFAPlayer(player);
		if(faPlayer == null)
			return;
		
		if(event.getBlock().getType() == Material.OBSIDIAN)
		{
			if(plugin.getHandler().getPhase() == 1)
			{
				if(!plugin.getHandler().getGizmoHandler().addGizmo(faPlayer, event.getBlock()))
				{
					event.getBlock().setType(Material.AIR);
				}
			}
		}
	}
}
