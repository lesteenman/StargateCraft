package net.minecraft.src.sgc;

import net.minecraft.src.mod_SGC;

public class SGCHelper {
	
	public static String[] forbiddenChars = {
		"M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
	};

	/**
	 * When given a valid address, returns a name in the format of
	 * P#X-##[#][#] where
	 *   [#] is an optional number
	 *   # is a number
	 *   X is a letter
	 * @param address
	 * 
	 */
	public static String nameForAddress(String address) {
		for (int i = 0; i < forbiddenChars.length; i++) {
			if (address.contains(forbiddenChars[i])) {
				System.out.println("Contained invalid letter! Only use all lowercase and uppercase up to L");
				System.exit(0);
			}
		}
				
		String sub = address.substring(0, 1);
		int f = intForGlyph(address.charAt(0)) + intForGlyph(address.charAt(1));
		int f1 = f % 5 + 2;
		String f2 = mod_SGC.glyphs[(int)(f / 5)];
		int f3 = intForGlyph(address.charAt(2)) + 1;
		int f4 = intForGlyph(address.charAt(3)) + 1;
		int f5 = intForGlyph(address.charAt(4)) + 1;
		int f6 = intForGlyph(address.charAt(5)) + 1;
		return String.format("P%d%s-%d", f1, f2.toUpperCase(), f3*f4 + f5*f6);
	}
	
	public static int intForGlyph(char glyph) {
		for (int i = 0; i < mod_SGC.glyphs.length; i++) {
			if (mod_SGC.glyphs[i].charAt(0) == glyph)
				return i;
		}
		return -1;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1 || args[0].length() != 6) {
			System.out.println("Use with one argument that consists of 6 letters!");
			System.exit(0);
		}
		System.out.println(nameForAddress(args[0]));
	}

}
