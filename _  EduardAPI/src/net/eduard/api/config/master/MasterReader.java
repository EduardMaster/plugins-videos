package net.eduard.api.config.master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterReader {
	public char[] chars;
	public int currentChar;
	public int currentLine;
	public int lastLineChar;
	public int currentLineChar;
	public int tabSize = 0;
	public int spaceSkipped = -1;
	public int allSkips = 0;
	private int lastSpacesSkipped = -1;
	public MasterReader(String text) {
		chars = text.toCharArray();
	}


	public void expected(String value) {
		printLine("\nErro na linha: " + (currentLine + 1));
		printLine("->  "
				+ String.copyValueOf(chars, lastLineChar, currentLineChar));
		printLine("->  " + getSpaces(currentLineChar) + "^");
		printLine("Esperava-se: ( " + value + " )");
	}

	public char getCurrent() {
		if (isEmpty()) {
			return '\n';
		}
		if (isEnd()) {
			return '?';
		}
		return chars[currentChar];
	}
	public void next() {

		if (isNewLine()) {
			currentLine++;
			lastLineChar += currentLineChar + 1;
			currentLineChar = -1;
		} else {

		}
		currentLineChar++;
		currentChar++;
	}
	public char getNext() {
		currentChar++;
		char value = getCurrent();
		currentChar--;
		return value;
	}

	public String getSpaces(int size) {
		String text = "";
		for (int i = 0; i < size; i++) {
			text += " ";
		}
		return text;
	}
	public int ignore() {
		return skip(' ', '\n');
	}
	public boolean isDelimiter() {
		return getCurrent() == ':';
	}

	public boolean isEmpty() {
		return chars.length == 0;
	}
	public boolean isEnd() {
		return currentChar >= chars.length;
	}
	public boolean isListDelimiter() {
		return getCurrent() == '-';
	}
	public boolean isListEnd() {
		return getCurrent() == ']';
	}
	public boolean isListSeparator() {
		return getCurrent() == ',';
	}
	public boolean isListStart() {
		return getCurrent() == '[';
	}
	public boolean isMapEnd() {
		return getCurrent() == '}';
	}
	public boolean isMapStart() {
		return getCurrent() == '{';
	}
	public boolean isNewLine() {
		return getCurrent() == '\n';
	}
	public boolean isSpace() {
		return getCurrent() == ' ';
	}
	public boolean isStringEnd() {
		return getCurrent() == '\"' | getCurrent() == '\'';
	}
	public boolean isStringStart() {
		return getCurrent() == '\"' | getCurrent() == '\'';
	}
	public boolean isSection() {
		return spaceSkipped == lastSpacesSkipped + 2;
	}
	public boolean isFirstSection() {
		return spaceSkipped == 0 & lastSpacesSkipped == -1;
	}
	public boolean isSectionEnd() {
		return spaceSkipped < lastSpacesSkipped;
	}

	public void nextPrint() {
		print(String.valueOf(getCurrent()));
		next();
	}
	public String print(String text) {
		System.out.print(text);
		return text;
	}
	public void printLine(String value) {
		System.out.println(value);
	}
	public void printNewLine() {
		print("\n");
	}
	public void printSpace() {
		print(" ");
	}
	public void printTab() {
		print(getSpaces(tabSize * 2));
	}
	public List<Object> readLines() {
		List<Object> list = new ArrayList<>();
		while (isListDelimiter()) {
			if (isEnd()) {
				break;
			}
			printNewLine();
			printTab();
			nextPrint();
			printSpace();
			list.add(readObject());
			ignore();
		}
		return list;
	}
	public String readComment() {
		nextPrint();
		printSpace();
		return readText();
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
				if (isListSeparator() | isDelimiter()) {
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
			} else if (isListSeparator()) {
				nextPrint();
				printNewLine();
				printTab();
			} else {

				if (isMapStart()) {

					print("'':");
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
	public Object readObject() {

		if (isEmpty()) {
			return "";
		}
		ignore();
		if (isMapStart()) {
			return readMap();
		} else if (isListDelimiter()) {
			return readLines();
		} else if (isListStart()) {
			return readList();
		}else if (isFirstSection()) {
			return readSection();
		} else if (isSection()) {
			return readSection();
		} else {
			return readText();
		}
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> readSection() {

		Map<String, Object> map = new HashMap<>();
		int current = spaceSkipped;
		print("{");
		tabSize++;
		while (spaceSkipped == current) {
			if (isEnd()) {
				break;
			}
			printNewLine();
			printTab();
			String key = readText();
			if (!isDelimiter()) {
				expected(":");
			} else {
				nextPrint();
				Object value = readObject();
				if (current == spaceSkipped) {
					if (value instanceof Map) {
						map.putAll(
								(Map<? extends String, ? extends Object>) value);
					}
				} else
					map.put(key, value);
			}
			ignore();
		}
		tabSize--;
		printNewLine();
		printTab();
		print("}");
		return map;

	}
	public String readString() {
		nextPrint();
		int start = currentChar;
		while (!isStringEnd()) {
			if (isEnd()) {
				break;
			}
			if (!isStringEnd())
				next();
		}
		String result = String.copyValueOf(chars, start, currentChar - start);
		print(result);
		nextPrint();
		return result;

	}
	public String readLine() {
		
		int start = currentChar;
		while (!isStringEnd()) {
			if (isEnd()) {
				break;
			}
			if (isNewLine()) {
				break;
			}
			next();
		}
		String result = String.copyValueOf(chars, start,
				currentChar - start);
		print(result);
		return result;
	}
	public String readText() {
		if (isStringStart()) {
			return readString();
		} else {
			int start = currentChar;
			while (!isStringEnd()) {
				if (isEnd()) {
					break;
				}
				if (isListSeparator())
					break;
				if (isNewLine()) {
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
			String result = String.copyValueOf(chars, start,
					currentChar - start);
			print(result);
			return result;
		}
	}
	public int skip(char... vars) {
		lastSpacesSkipped = spaceSkipped;
		spaceSkipped = 0;
		boolean can = true;
		int amount = 0;
		while (can) {
			can = false;
			for (char var : vars) {

				if (var == getCurrent()) {
					can = true;

				}
			}
			if (getCurrent() == ' ') {
				spaceSkipped++;
			}
			if (getCurrent() == '\n') {
				spaceSkipped = 0;
			}
			if (can) {
				next();
				allSkips++;
				amount++;
			}
		}
		return amount;
	}

}
