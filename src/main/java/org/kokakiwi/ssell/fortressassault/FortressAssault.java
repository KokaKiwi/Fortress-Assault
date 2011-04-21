package org.kokakiwi.ssell.fortressassault;

import java.util.logging.Logger;

import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import org.kokakiwi.ssell.fortressassault.commands.*;
import org.kokakiwi.ssell.fortressassault.events.*;
import org.kokakiwi.ssell.fortressassault.handlers.*;

public class FortressAssault extends JavaPlugin {
	
	private Logger logger = Logger.getLogger("Minecraft");
	
	private PluginManager pm;
	
	private FABlockListener blockListener = new FABlockListener(this);
	private FAPlayerListener playerListener = new FAPlayerListener(this);
	private FAEntityListener entityListener = new FAEntityListener(this);
	
	private FortressAssaultHandler handler = new FortressAssaultHandler(this);

	public void onEnable() {
		pm = getServer().getPluginManager();
		
		getCommand("faStart").setExecutor(new StartCommand(this));
		getCommand("faStop").setExecutor(new StopCommand(this));
		getCommand("faAdd").setExecutor(new AddCommand(this));
		getCommand("faRemove").setExecutor(new RemoveCommand(this));
		getCommand("faTime").setExecutor(new TimeCommand(this));
		getCommand("faReturn").setExecutor(new ReturnCommand(this));
		getCommand("faTeams").setExecutor(new TeamsCommand(this));
		
		pm.registerEvent(Event.Type.BLOCK_DAMAGE, blockListener,
				Event.Priority.High, this);
		pm.registerEvent(Event.Type.BLOCK_PLACE, blockListener,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.ENTITY_DEATH, entityListener,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_RESPAWN, playerListener,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener,
				Event.Priority.Normal, this);
		
		logger.info("[FortressAssault] Plugin v" + getDescription().getVersion() + " loaded.");
	}
	
	public void onDisable() {
		logger.info("[FortressAssault] Plugin v" + getDescription().getVersion() + " disabled.");
	}
	
	public FortressAssaultHandler getHandler()
	{
		return handler;
	}

}
