package net.eduard.api.minigame;

public interface MinigameEvents {

	public void event(MinigameRoom game);
	
	public void event(MinigameRoom room, MinigameEventType event);
}
