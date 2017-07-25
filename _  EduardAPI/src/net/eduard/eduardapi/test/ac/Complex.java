package net.eduard.eduardapi.test.ac;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Complex {

	private Complex father;
	private String name;

	private Map<String, Complex> complexs = new HashMap<>();
	private List<Complex> list = new ArrayList<>();
	private Object value;

	public Complex(String name) {
		this.name = name;
	}

	public Complex(String name, Complex father) {
		this(name);
		this.father = father;
	}

	public Object getValue() {
		// header: value

		return value;
	}

	public void read(List<String> lines) {
		StringBuilder builder = new StringBuilder();
		for (String line : lines) {
			builder.append(line);
			builder.append("\n");
			System.out.println(line);
		}
		System.out.println("-------------------------");
		String text = builder.toString();
		ComplexReader reader = new ComplexReader(text.toCharArray(),0);
		reader.readObject();
//		reader.readObject();
//		reader.readSection();
//		reader.readSection();
//		int maxSection = 10;
//		int section = 0;
//		while(reader.isSection()&reader.hasNext()) {
//			reader.next();
//			if (reader.isEmpty() | reader.isSection()) {
//				System.out.println("Empty sections");
//				break;
//			}
//			String name = reader.readObject().toString();
//			System.out.println("name: " + name);
//			reader.readDelimiter();
//			Object object = reader.readObject();
//			System.out.println("objecto: " + object);
//			section++;
//			if (section==maxSection)break;
//		}
	}

}
