package interfaces;

/**
Provides default methods for moving windows
@author Chris
*/
public interface MovableWindow
{

	/**
	Used for updating the window's position on screen
	*/
	public void update ();

	/**
	Allows the signaling of the beginning of a move left
	*/
	public void startMoveLeft ();

	/**
	Allows the signaling of the beginning of a move right
	*/
	public void startMoveRight ();

	/**
	Allows the signaling of the beginning of a move up
	*/
	public void startMoveUp ();

	/**
	Allows the signaling of the beginning of a move down
	*/
	public void startMoveDown ();

	/**
	Allows the signaling of the end of a move left
	*/
	public void endMoveLeft ();

	/**
	Allows the signaling of the end of a move right
	*/
	public void endMoveRight ();

	/**
	Allows the signaling of the end of a move up
	*/
	public void endMoveUp ();

	/**
	Allows the signaling of the end of a move down
	*/
	public void endMoveDown ();

	/**
	Allows signaling that the window should close
	*/
	public void onClose ();

	/**
	Allows signaling that the program is ending
	*/
	public void onEnd ();
}
