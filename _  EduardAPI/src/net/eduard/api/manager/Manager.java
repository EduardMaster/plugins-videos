package net.eduard.api.manager;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import net.eduard.api.API;
import net.eduard.api.config.Save;
import net.eduard.api.config.Section;

public class Manager extends EventRegister implements Runnable,Save{

	
	public Manager() {
		this(API.getAPI());
	}

	public Manager(JavaPlugin plugin) {
		super(false);
		setPlugin(plugin);
	}
	private long time;
	private long startTime;
	private transient Plugin plugin;
	private transient BukkitTask task;

	public void run() {
	}

	public Plugin getPlugin() {
		return plugin;
	}

	public Manager setPlugin(Plugin plugin) {
		this.plugin = plugin;
		return this;
	}
	
	public BukkitTask delay(Plugin plugin){
		setTask(API.delay(plugin, time, this));
		setStartTime(API.getNow());
		return task;
	}
	public BukkitTask timer(Plugin plugin){
		setTask(API.timer(plugin, time, this));
		setStartTime(API.getNow());
		return task;
	}
	public BukkitTask delay(long ticks,Runnable run){
		setTask(API.delay(plugin, ticks, run));
		setStartTime(API.getNow());
		return task;
	}
	public BukkitTask timer(long ticks,Runnable run){
		setTask(API.timer(plugin, ticks, run));
		setStartTime(API.getNow());
		return task;
	}

	public long getTime() {
		return time;
	}
	public boolean existsTask(){
		return task!=null;
	}
	public void stopTask(){
		if (existsTask()){
		task.cancel();
		task =null;
		}
	}

	public void setTime(long time) {
		this.time = time;
	}
	public void setTime(int time) {
		setTime(time*20L);
	}
	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public BukkitTask getTask() {
		return task;
	}

	public void setTask(BukkitTask task) {
		this.task = task;
	}

	@Override
	public Object get(Section section) {
		return null;
	}

	@Override
	public void save(Section section, Object value) {
		
	}

//	public EventAPI register(Class<? extends JavaPlugin> pluginClass) {
//	return register(JavaPlugin.getPlugin(pluginClass));
//}
}
