package edu.zju.cs.ooobgy.visualization.font;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class UnderLineFont extends Font{

	public UnderLineFont(String name, int style, int size) {
		super(name, style, size);
		Map<TextAttribute, Integer> fontAttribute = new HashMap<TextAttribute, Integer>();
		fontAttribute.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		super.deriveFont(fontAttribute);
	}

}
