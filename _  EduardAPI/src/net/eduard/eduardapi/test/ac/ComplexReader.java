package net.eduard.eduardapi.test.ac;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

public class ComplexReader {

	private char[] vars;
	private int index;
	private char current;
	public ComplexReader(char[] vars, int index) {
		this.vars = vars;
		this.index = index;
		updateCurrent();
	}

	public char[] getVars() {
		return vars;
	}
	public void setVars(char[] vars) {
		this.vars = vars;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public static final char DOUBLE_MARK = '\"';
	public static final char SIMPLE_MARK = '\'';

	public static final char LIST_START = '[';
	public static final char LIST_ITEM = '-';
	public static final char LIST_DELIMITER = ',';
	public static final char LIST_END = ']';
	public static final char MAP_DELIMITER = ':';
	public static final char MAP_START = '{';
	public static final char MAP_END = '}';
	public static final char SPACE = ' ';
	public static final char ENTER = '\n';
	public static final char COMMENT = '#';
	public static final int SECTION_SPACES = 2;

	// private int past;
	public String readString() {
		int start = index;
		int past = 0;
		if (current == DOUBLE_MARK) {
			next();
			start++;
			past = find(DOUBLE_MARK);
			next();
			 YamlConfiguration a;
			 
		} else if (current == SIMPLE_MARK) {
			next();
			start++;
			past = find(SIMPLE_MARK);
			next();
		} else {

			past = find(MAP_DELIMITER, LIST_DELIMITER, LIST_END, MAP_END,
					COMMENT, ENTER);
		}
		return String.copyValueOf(vars, start, past);
	}
	public Map<String, Object> readEntry() {
		HashMap<String, Object> map = new HashMap<>();
		String key = readString();
		readDelimiter();
		Object value = readObject();
		if (!key.equals("")) {
			map.put(key, value);	
		}
		

		System.out.println(map);
		return map;
	}
	public List<Object> readItemList() {
		List<Object> items = new ArrayList<>();
		next();

		while (true) {
			if (!hasNext()) {
				break;
			}
			if (!isSectionList()) {
				break;
			}
			next();
			items.add(readObject());

		}
		return items;
	}
	public Map<String, Object> readMap() {
		Map<String, Object> map = new HashMap<>();

		while (true) {
			
			if (isMapEnd()) {
				next();
				break;
			}
			if (isNewLine()) {
				next();
				continue;
			}
			// skipSpaces();

			next();

			if (current == LIST_DELIMITER) {
				continue;
			}

			map.putAll(readEntry());

			if (!hasNext()) {
				break;
			}

		}
		return map;

	}

	public List<Object> readList() {
		List<Object> items = new ArrayList<>();
		while (true) {
			if (isListEnd()) {
				next();
				break;
			}
			if (isNewLine()) {
				next();
				continue;
			}

			next();
			if (current == LIST_DELIMITER) {
				continue;
			}

			items.add(readObject());

			if (!hasNext()) {
				break;
			}

		}
		return items;
	}

	public int find(char... vars) {
		int past = 0;

		loop : while (true) {
			for (char var : vars) {
				if (current == var) {
					break loop;
				}
			}
			past++;
			if (!hasNext()) {
				break;
			}
			next();
		}
		return past;

	}
	public String skipWhen(char... variations) {
		int start = index;
		int past = 0;
		boolean find = true;
		while (find) {
			 find = false;
			for (char var : variations) {
				if (current == var) {
					find = true;
				}
			}
			if (!find)
				break;
			else
				next();
			past++;

		}
		return String.copyValueOf(vars, start, past);
	}
	public boolean lineContains(char var) {
		int fic = index + 1;
		while (true) {
			if (fic == vars.length)
				return false;
			if (vars[fic] == ENTER) {
				break;
			}
			if (vars[fic] == var)
				return true;
			fic++;
		}
		return false;
	}

	public Object readObject() {
		return readObject(false);
	}
	public Object readObject(boolean ignoreEntry) {
		skipSpaces();
		if (isComment()) {
			next();
			return readString();

		} else {

			if (isList()) {
				return readList();
			} else if (isMap()) {
				return readMap();
			} else if (isItemList()) {
				return readItemList();
			} else if (isEntry() & !ignoreEntry) {
				return readEntry();
			} else if (isSection()) {
				return readSection();
			} else {
				return readString();
			}
		}
	}

	public Complex readSection() {
		next();
		Map<String, Object> map = readEntry();

		return null;
	}

	public boolean lineStartWith(char var) {
		int fic = index + 1;
		while (true) {
			if (fic == vars.length)
				return false;
			char value = vars[fic];
			if (value == ENTER) {
				break;
			} else if (value != SPACE && value == var) {
				return true;
			}
			fic++;
		}
		return false;
	}
	public boolean isItemList() {
		return lineStartWith(LIST_ITEM) & isNewLine();
	}
	public boolean isSection() {
		return !isItemList() & (lineContains(MAP_DELIMITER) & isNewLine());
	}
	public boolean isEntry() {
		return lineContains(MAP_DELIMITER) & !isNewLine();
	}
	public boolean isMap() {
		return current == MAP_START;
	}
	public String readDelimiter() {
		return skipWhen(SPACE, MAP_DELIMITER);
	}
	public boolean isString() {
		return current == SIMPLE_MARK | current == DOUBLE_MARK;
	}
	public boolean isComment() {
		return current == COMMENT;
	}
	public boolean isList() {

		return current == LIST_START;
	}
	public boolean isSectionList() {

		return vars[index + 1] == LIST_ITEM;
	}

	public Integer readInteger() {
		return Integer.valueOf(readString());
	}
	public boolean hasNext(boolean log) {
		boolean hasNext = index + 1 < vars.length;
		if (!hasNext) {
			if (log)
				System.out.println("!NEXT");
		}
		return hasNext;
	}
	public boolean hasNext() {
		return hasNext(false);
	}
	public char next() {

		if (hasNext()) {
			index++;
			return updateCurrent();
		}
		return current;
	}
	public boolean isEmpty() {
		return vars.length == 0;
	}
	public String skipSpaces() {
		return skipWhen(SPACE);
	}
	public char updateCurrent() {
		if (!isEmpty()) {
			this.current = vars[index];
		}
		return current;
	}
	public boolean isListEnd() {
		return current == LIST_END;
	}
	public boolean isNewLine() {
		return current == ENTER;
	}

	public boolean isMapEnd() {
		return current == MAP_END;
	}
}
