package interfaces;

/**
Extends the MovableWindow by making it more game specific
@author Chris
*/
public interface GameWindow extends MovableWindow
{

	/**
	Allows the signaling of a weapon fire
	*/
	public void fireWeapon ();
}
