package org.kokakiwi.ssell.fortressassault.entities;

import org.bukkit.entity.Player;

public class FAPlayer implements Comparable<Object> {
	private String name;
	private Player player;
	private FATeam team;
	private FAClass type;
	
	public int kills;
	public int deaths;
	public int destructions;
	
	public FAPlayer(Player bPlayer)
	{
		name = bPlayer.getName();
		player = bPlayer;
		team = FATeam.NONE;
		type = FAClass.NONE;
		
		kills = 0;
		deaths = 0;
		destructions = 0;
	}

	public int compareTo(Object anotherPlayer) {
		return getKills() - ((FAPlayer) anotherPlayer).getKills();
	}

	public String getName() {
		return name;
	}

	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player)
	{
		this.player = player;
	}

	public FATeam getTeam() {
		return team;
	}
	
	public void setTeam(FATeam team) {
		this.team = team;
	}

	public FAClass getType() {
		return type;
	}

	public void setType(FAClass type) {
		this.type = type;
	}

	public int getKills() {
		return kills;
	}
	
	public void setKills(int kills) {
		this.kills = kills;
	}
	
	public int addKill(int num)
	{
		this.kills += num;
		return this.kills;
	}

	public int getDeaths() {
		return deaths;
	}
	
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	
	public int addDeaths(int num)
	{
		this.deaths += num;
		return this.deaths;
	}

	public int getDestructions() {
		return destructions;
	}
	
	public void setDestructions(int destructions) {
		this.destructions = destructions;
	}
	
	public int addDestruction(int num)
	{
		this.destructions += num;
		return this.destructions;
	}
}
