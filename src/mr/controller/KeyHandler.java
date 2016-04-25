package mr.controller;

import org.newdawn.slick.Input;

public class KeyHandler {

	public static boolean isCommand(String command, int key) {
		if ( command.equals("Up") ) {
			return key == Input.KEY_UP || key == Input.KEY_Z;
		}
		if ( command.equals("Down") ) {
			return key == Input.KEY_DOWN || key == Input.KEY_S;
		}
		if ( command.equals("Left") ) {
			return key == Input.KEY_LEFT || key == Input.KEY_Q;
		}
		if ( command.equals("Right") ) {
			return key == Input.KEY_RIGHT || key == Input.KEY_D;
		}
		if ( command.equals("Rewind") ) {
			return key == Input.KEY_SPACE || key == Input.KEY_R;
		}
		if ( command.equals("Retry") ) {
			return key == Input.KEY_Y;
		}
		if ( command.equals("Escape") ) {
			return key == Input.KEY_ESCAPE;
		}
		if ( command.equals("Ok") ) {
			return key == Input.KEY_ENTER || key == Input.KEY_SPACE;
		}
		if ( command.equals("Cancel") ) {
			return key == Input.KEY_ESCAPE || key == Input.KEY_A;
		}

		return false;
	}
}
