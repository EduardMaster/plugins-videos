package net.eduard.eduardapi.test.ac;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonCopy {
	public static void main(String[] args) {
		String value = "{[teste]:[teste]}";
		readJson(value);
	}

	public static JsonCopy readJson(String text) {
		JsonCopy json = new JsonCopy();
		JsonReader r = new JsonReader(text);
		r.readObject();
		// System.out.println(r.readObject());
		return json;
	}
	public static class JsonReader {

		public char[] chars;
		public int index;
		public int line;
		public int linesChars;
		public int lineIndex;
		public int tabSize = 0;
		public JsonReader(String text) {
			chars = text.toCharArray();
		}
		public Object readObject() {
			ignore();
			if (isMapStart()) {
				return readMap();
			} else if (isListStart()) {
				return readList();
			} else {
				return readText();
			}
		}
		public String readText() {
			if (isStringStart()) {
				return readString();
			} else {
				int start = index;
				while (!isStringEnd()) {
					if (isEnd()) {
						break;
					}
					if (isListEnd())
						break;
					if (isMapEnd())
						break;
					if (isDelimiter())
						break;
					next();
				}
				String result = String.copyValueOf(chars, start, index - start);
				pr(result);
				return result;
			}
		}
		public int skip(char... vars) {
			boolean can = true;
			int amount = 0;
			while (can) {
				can = false;
				for (char var : vars) {
					if (var == getCurrent()) {
						can = true;
					}
				}
				if (can) {
					next();
					amount++;
				}
			}
			return amount;
		}
		public void next() {
			if (isNewLine()) {
				line++;
				linesChars += lineIndex;
				lineIndex = -1;
			}
			lineIndex++;
			index++;
		}
		public void print(String value) {
			System.out.println(value);
		}
		public void expected(String value) {
			int size = lineIndex;
			print("\nErro na linha: " + (line + 1));
			print("  " + String.copyValueOf(chars, linesChars, size + 1));
			print("  " + getSpaces(size) + "^");
			print("Esperava-se: ( " + value + " )");
		}
		public String getSpaces(int size) {
			String text = "";
			for (int i = 0; i < size; i++) {
				text += " ";
			}
			return text;
		}
		public List<Object> readList() {
			List<Object> list = new ArrayList<>();

			nextPrint();
			printNewLine();
			tabSize++;
			printTab();

			while (!isListEnd()) {
				if (isEnd()) {
					break;

				} else if (isListEnd()) {
					break;
				} else {
					ignore();
					if (isListSeparator()) {
						nextPrint();
						printNewLine();
						printTab();
					} else {
						Object object = readObject();
						list.add(object);
					}
				}
			}
			printNewLine();

			printTab();
			tabSize--;
			nextPrint();
			return list;
		}

		public Map<String, Object> readMap() {
			Map<String, Object> map = new HashMap<>();
			nextPrint();
			printNewLine();
			tabSize++;
			printTab();
			while (!isMapEnd()) {
				ignore();
				if (isEnd()) {
					break;
				} else if (isMapEnd()) {
					break;
				} else {

					if (isMapStart()) {

						pr("'':");
						printNewLine();
						map.put("''", readMap());
					} else {
						String key = readText();
						if (!isDelimiter()) {
							expected(":");
						} else {
							nextPrint();
							Object value = readObject();
							// System.out.println("\nValor: "+value+"|");
							map.put(key, value);
						}
					}
				}

			}
			printNewLine();
			tabSize--;
			printTab();
			nextPrint();

			return map;
		}

		public String readString() {
			nextPrint();
			int start = index;
			while (!isStringEnd()) {
				if (isEnd()) {
					break;
				}
				if (!isStringEnd())
					next();
			}
			String result = String.copyValueOf(chars, start, index - start);
			pr(result);
			nextPrint();
			return result;

		}
		public void printNewLine() {
			pr("\n");
		}
		public void nextPrint() {
			pr(String.valueOf(getCurrent()));
			next();
		}
		public void printTab() {
			pr(getSpaces(tabSize * 2));
		}
		public String pr(String text) {
			System.out.print(text);
			return text;
		}
		public int ignore() {
			return skip(' ', '\n');
		}
		public char getNext() {
			index++;
			char value = getCurrent();
			index--;
			return value;
		}
		public boolean isDelimiter() {
			return getCurrent() == ':';
		}
		public boolean isNewLine() {
			return getCurrent() == '\n';
		}
		public boolean isMapStart() {
			return getCurrent() == '{';
		}
		public boolean isMapEnd() {
			return getCurrent() == '}';
		}
		public boolean isStringStart() {
			return getCurrent() == '\"' | getCurrent() == '\'';
		}
		public boolean isStringEnd() {
			return getCurrent() == '\"' | getCurrent() == '\'';
		}
		public boolean isListSeparator() {
			return getCurrent() == ',';
		}
		public boolean isEmpty() {
			return chars.length == 0;
		}
		public boolean isListStart() {
			return getCurrent() == '[';
		}
		public boolean isListEnd() {
			return getCurrent() == ']';
		}
		public boolean isEnd() {
			return index >= chars.length;
		}
		public char getCurrent() {
			if (isEmpty()) {
				return '\n';
			}
			if (isEnd()) {
				return '?';
			}
			return chars[index];
		}

	}

}
