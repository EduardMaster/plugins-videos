package net.eduard.api.config.master;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MasterSection {

	
	private Object value;
	private String key;
	private MasterSection parent;
	private MasterSection root;
	private Map<String, Object> map;
	private List<Object> list;
	
	
	
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public List<Object> getList() {
		return list;
	}
	public void setList(List<Object> list) {
		this.list = list;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public void setParent(MasterSection parent) {
		this.parent = parent;
	}
	public void setRoot(MasterSection root) {
		this.root = root;
	}
	public void createSection(String path) {
		
	}
	public boolean isSection() {
		
		return false;
	}
	public boolean isList() {
		
		return false;
	}
	public boolean isMap() {
		
		return false;
	}
	public boolean isLines() {
		return false;
	}
	public boolean isRoot() {
		return root.equals(this);
	}
	public MasterSection getParent() {
		return parent;
	}
	public MasterSection getRoot() {
		return root;
	}
	public String getKey() {
		return key;
	}
	public Object getValue() {
		return value;
	}

	
	public void save(File file) {
		
	}
	
	public void save(Path path) {
		try {
			
			
			Files.write(path, getLines(), Charset.forName("UTF-8"));
		} catch (Exception e) {
		}
	}
	public List<String> getLines(){
		
		List<String> lines = new ArrayList<>();
		
		int id = 0;
		if (root.getValue() instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String,Object> map = (Map<String,Object>) root.getValue();
			for (Entry<String, Object> entry:map.entrySet()) {
				lines.add(entry.getKey()+": "+key.toString());
			}
			id++;
			
		}
		id=0;
		
		
		
		
		return lines;
				
				
	}
	
	public void reload(File file) {
		
	}
	public void reload(Path path) {
		
	}
	
	public void reload(String config) {
		MasterReader reader = new MasterReader(config);
		this.value = reader.readObject();
	}
	public void reload(List<String> lines) {
		
	}
	
	

}
