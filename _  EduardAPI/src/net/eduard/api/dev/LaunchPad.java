package net.eduard.api.dev;

import net.eduard.api.API;
import net.eduard.api.config.Section;
import net.eduard.api.util.Save;

public class LaunchPad implements Save {

	public void register() {
		API.PADS.add(this);
	}

	public void unregister() {
		API.PADS.remove(this);
	}

	private int blockId;

	private int blockData = -1;
	private int blockHigh = -1;

	private Jump effect;
	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}

	public String toString() {
		return "LaunchPad [blockId=" + blockId + ", blockData=" + blockData + ", blockHigh=" + blockHigh + ", effect="
				+ effect + "]";
	}



	public LaunchPad(int blockId, Jump effect) {
		this(blockId, -1, effect);
	}

	public LaunchPad(int blockId, int blockHigh, Jump effect) {

		setBlockHigh(blockHigh);
		setBlockId(blockId);
		setEffect(effect);
		
		register();
	}

	public LaunchPad() {
	}

	public int getBlockHigh() {

		return blockHigh;
	}

	public Jump getEffect() {

		return effect;
	}

	public void setBlockHigh(int blockHigh) {

		this.blockHigh = blockHigh;
	}

	public void setEffect(Jump effect) {

		this.effect = effect;
	}

	public void save(Section section, Object value) {

	}

	public Object get(Section section) {
		return null;
	}

	public int getBlockData() {
		return blockData;
	}

	public void setBlockData(int blockData) {
		this.blockData = blockData;
	}
}
