package org.kokakiwi.ssell.fortressassault.entities;

import org.bukkit.block.Block;

public class FAGizmo {
	
	private FATeam team;
	private Block block;
	private FAPlayer destroyer;
	private boolean destructing;
	
	public FAGizmo(FATeam p_Team, Block p_Block) {
		team = p_Team;
		block = p_Block;
		destroyer = null;
		destructing = false;
	}

	public FAPlayer getDestroyer() {
		return destroyer;
	}

	public void setDestroyer(FAPlayer destroyer) {
		this.destroyer = destroyer;
	}

	public boolean isDestructing() {
		return destructing;
	}

	public void setDestructing(boolean destructing) {
		this.destructing = destructing;
	}

	public FATeam getTeam() {
		return team;
	}

	public Block getBlock() {
		return block;
	}
}
