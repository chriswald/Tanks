package pkg;

import home.Tanks;
import interfaces.GameWindow;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;

/**
Base class for the game's objects
@author Chris
*/
public abstract class ViewPort extends JFrame implements GameWindow
{

	private static final long serialVersionUID = 1L;
	protected Image sprite;
	protected Image background;
	protected double posx, posy;
	protected double velocity;
	protected double angle;

	/**
	Initializes a new ViewPort
	@param title The window's title
	*/
	public ViewPort (String title)
	{
		super (title);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

	/**
	Checks for collisions between objects
	@param objs The list of ViewPorts to check
	*/
	public abstract void collide (ViewPort[] objs);

	/**
	Enforces drawing of graphics to a global buffer
	*/
	public abstract void drawToBuffer ();

	/**
	Supplied default paint method
	@param g System generated graphics object
	*/
	@Override
	public void paint (Graphics g)
	{
		super.paint (g);
	}

	/**
	Moves the window
	*/
	@Override
	public void update ()
	{
		double velx = this.velocity * Math.sin (this.degToRad (this.angle));
		double vely = this.velocity * Math.cos (this.degToRad (this.angle));

		this.posx += velx;
		this.posy += vely;

		this.setLocation ((int) this.posx, (int) this.posy);
	}

	/**
	These methods are to be overridden in sub-classes
	*/
	@Override
	public void startMoveLeft () {}

	@Override
	public void startMoveRight () {}

	@Override
	public void startMoveUp () {}

	@Override
	public void startMoveDown () {}

	@Override
	public void endMoveLeft () {}

	@Override
	public void endMoveRight () {}

	@Override
	public void endMoveUp () {}

	@Override
	public void endMoveDown () {}

	@Override
	public void fireWeapon () {}

	/**
	Default operation for when the window is closed
	*/
	@Override
	public void onClose ()
	{
		Tanks.sigDone ();
	}

	/**
	Default operation for when the program ends
	*/
	@Override
	public void onEnd ()
	{
		this.dispose ();
	}

	/**
	Simple function to convert degrees to radians
	@param degree
	@return The value of the degrees in radians
	*/
	private double degToRad (double degree)
	{
		return degree * Math.PI / 180;
	}
}
