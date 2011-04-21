package org.kokakiwi.ssell.fortressassault.handlers;

import org.bukkit.entity.Player;
import org.kokakiwi.ssell.fortressassault.FortressAssault;
import org.kokakiwi.ssell.fortressassault.entities.FAGizmo;
import org.kokakiwi.ssell.fortressassault.entities.FAPlayer;

public class FAPvPHandler {
	private FortressAssault plugin;
	
	public FAPvPHandler(FortressAssault parent)
	{
		plugin = parent;
	}
	
	public void killEvent(Player killer, Player killed)
	{
		FAPlayer faKiller = plugin.getHandler().getFAPlayer(killer);
		FAPlayer faKilled = plugin.getHandler().getFAPlayer(killed);
		
		if(faKiller != null && faKilled != null)
		{
			if(faKiller.getTeam() == faKilled.getTeam())
			{
				plugin.getHandler().sayPlayers(plugin.getHandler().getTeamColor(faKiller.getTeam())
								+ faKiller.getName() + " committed treason on " + faKilled.getName());
				faKiller.kills -= 1;
			}else {
				plugin.getHandler().sayPlayers(plugin.getHandler().getTeamColor(faKiller.getTeam())
								+ faKiller.getName() + " killed " + faKilled.getName());
				faKiller.kills += 1;
				faKilled.deaths += 1;
			}
		}
	}
	
	public void destructionEvent(Player destructor, FAGizmo gizmo)
	{
		FAPlayer faPlayer = plugin.getHandler().getFAPlayer(destructor);
		
		if(faPlayer != null)
		{
			plugin.getHandler().sayPlayers(plugin.getHandler().getTeamColor(faPlayer.getTeam())
								+ faPlayer.getName() + " destroyed the "
								+ gizmo.getTeam().toString() + " gizmo!");
			faPlayer.destructions += 1;
		}
	}
}
