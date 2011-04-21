package org.kokakiwi.ssell.fortressassault.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.kokakiwi.ssell.fortressassault.FortressAssault;
import org.kokakiwi.ssell.fortressassault.entities.FAPlayer;

public class FAEntityListener extends EntityListener {

	private FortressAssault plugin;

	public FAEntityListener(FortressAssault fortressAssault) {
		plugin = fortressAssault;
	}

	@Override
	public void onEntityDamage(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		if(entity instanceof Player)
		{
			Player player = (Player) entity;
			FAPlayer faPlayer = plugin.getHandler().getFAPlayer(player);
			if(faPlayer != null)
			{
				if(plugin.getHandler().getPhase() == 1)
				{
					event.setCancelled(true);
				}else if(plugin.getHandler().getPhase() == 2) {
					if(event instanceof EntityDamageByEntityEvent)
					{
						EntityDamageByEntityEvent dEvent = (EntityDamageByEntityEvent) event;
						if(dEvent.getDamager() instanceof Player 
								&& dEvent.getEntity() instanceof Player) {
							Player victim = (Player) dEvent.getEntity();
							Player attacker = (Player) dEvent.getDamager();
							
							int damage = dEvent.getDamage();
							int oldHealth = victim.getHealth();
							int newHealth = oldHealth - damage;
							
							if(newHealth <= 0)
							{
								event.setDamage(999);
								victim.getInventory().clear();
								plugin.getHandler().getPvpHandler().killEvent(attacker, victim);
							}
						}else if(event.getEntity() instanceof Player) {
							Player victim = (Player) dEvent.getEntity();
							
							int damage = dEvent.getDamage();
							int oldHealth = victim.getHealth();
							int newHealth = oldHealth - damage;
							
							if(newHealth <= 0)
							{
								event.setDamage(999);
								victim.getInventory().clear();
							}
						}
					}else {
						Player victim = (Player) event.getEntity();
						
						int damage = event.getDamage();
						int oldHealth = victim.getHealth();
						int newHealth = oldHealth - damage;
						
						if(newHealth <= 0)
						{
							event.setDamage(999);
							victim.getInventory().clear();
						}
					}
				}
			}
		}
	}

	@Override
	public void onEntityDeath(EntityDeathEvent event) {
		Entity entity = event.getEntity();
		if(entity instanceof Player)
		{
			Player player = (Player) entity;
			FAPlayer faPlayer = plugin.getHandler().getFAPlayer(player);
			if(faPlayer != null)
			{
				
			}
		}
	}

}
