package mr.model;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class GameConstant {
	public final static int WIDTH = 20;
	public final static int HEIGHT = 20;
	public final static Charset ENCODING = StandardCharsets.UTF_8;

	public final static float TILE_SIZE = 32;

	public enum Layers {
		BACKGROUND,
		TILES,
		ITEMS,
		FOREGROUND
	};
}
