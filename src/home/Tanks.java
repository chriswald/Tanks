package home;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import pkg.Boulder;
import pkg.Controls;
import pkg.ScoreBoard;
import pkg.Tank;
import pkg.ViewPort;

import companion.ArrowKeyListener;

/**
Main class for the game
Holds some globally accessible elements such as the screen dimensions
and the buffer. Initializes and runs the game.
@author Chris
 */
public class Tanks
{

	private static boolean done = false;
	public static int SWIDTH;
	public static int SHEIGHT;
	private static Image background;
	public static Image buffer;
	public static boolean can_start = false;

	/**
	Initializes and runs the entire game (That's right: all of it)
	What's to say? This function is pretty awesome. It loads all of
	the game elements and initializes each of the windows.
	@param args Not Used
	 */
	public static void main (String[] args)
	{
		loadComponents ();

		Controls c = new Controls();

		while (!can_start) {System.out.print("");}

		ViewPort[] game_objects = loadGameObjects ();

		runGame (game_objects);

		onEndAll (game_objects);
	}

	/**
	Executes the main game loop
	@param game_objects The ViewPort's that make up the game
	 */
	private static void runGame (ViewPort[] game_objects)
	{
		while (!done)
		{
			// Update locations of the ViewPorts
			updateAll (game_objects);

			// Have they collided with each other?
			collideAll (game_objects);

			// Repaint the background buffer with the static
			// background image, then call on the ViewPorts
			// to paint any images they or their owned
			// children need to.
			refreshBuffer ();
			drawToBufferAll (game_objects);

			// The buffer repaint is complete, now allow the
			// ViewPorts to access and draw it.
			repaintAll (game_objects);

			ralentirVotreRouleau ();
		}
	}

	/**
	Adds each of the game's windows to a list
	@return The compiled list
	 */
	private static ViewPort[] loadGameObjects ()
	{
		int num_boulders = (int) (3 + (Math.random () * 3));
		int num_objects = num_boulders + 3;
		int index = 0;

		ViewPort[] objects = new ViewPort[num_objects];

		ViewPort port0 = new Tank ();
		ViewPort port1 = new Tank ();
		port0.addKeyListener (new ArrowKeyListener (port0, port1));
		port1.addKeyListener (new ArrowKeyListener (port0, port1));

		ViewPort sb = new ScoreBoard ();
		sb.addKeyListener (new ArrowKeyListener (port0, port1));
		Tank.pointToScoreBoard ((ScoreBoard) sb);

		objects[index++] = port0;
		objects[index++] = port1;
		objects[index++] = sb;

		for (int i = 0;i < num_boulders;i++)
		{
			ViewPort boulder = new Boulder ();
			boulder.addKeyListener (new ArrowKeyListener (port0, port1));
			objects[index++] = boulder;
		}

		return objects;
	}

	/**
	Does the initial setup of globally accessible variables
	and Image resources.
	 */
	private static void loadComponents ()
	{

		// Get the dimensions of the screen
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment ();
		GraphicsDevice gd = ge.getDefaultScreenDevice ();
		SWIDTH = gd.getDefaultConfiguration ().getBounds ().width;
		SHEIGHT = gd.getDefaultConfiguration ().getBounds ().height;

		// Loads images (or exits on fail [we kind of need those
		// to be present as there really isn't any way to run
		// properly without them])
		try
		{
			File bg = new File ("background.jpg");
			background = ImageIO.read (bg);
			Graphics g = background.getGraphics ();
			g.drawImage (background, 0, 0, SWIDTH, SHEIGHT, 0, 0, background.getWidth (null), background.getHeight (null), null);
			g.clipRect (0, 0, SWIDTH, SHEIGHT);
			buffer = ImageIO.read (bg);
		}
		catch (IOException e)
		{
			System.out.println ("Cannot load the background image \"background.jpg\"\n"
					+ "Please make sure it's still with the game's files.\n"
					+ "If you want to create one of your own, you can do that too. Just make sure it's big enough"
					+ "to cover your screen, then name it \"background.jpg\" and put it with the game's files.");
			System.exit (1);
		}
	}

	/**
	Calls update() on each of the game objects
	@param objs The list of the game's objects
	 */
	private static void updateAll (ViewPort[] objs)
	{
		for (ViewPort obj : objs)
		{
			obj.update ();
		}
	}

	/**
	Calls collide() on each of the game objects
	@param objs The list of the game's objects
	 */
	private static void collideAll (ViewPort[] objs)
	{
		for (ViewPort obj : objs)
		{
			obj.collide (objs);
		}
	}

	/**
	Calls drawToBuffer() on each of the game objects
	@param objs The list of the game's objects
	 */
	private static void drawToBufferAll (ViewPort[] objs)
	{
		for (ViewPort obj : objs)
		{
			obj.drawToBuffer ();
		}
	}

	/**
	Calls repaint() on each of the game objects
	@param objs The list of the game's objects
	 */
	private static void repaintAll (ViewPort[] objs)
	{
		for (ViewPort obj : objs)
		{
			obj.repaint ();
		}
	}

	/**
	Calls onEnd() on each of the game objects
	@param objs The list of the game's objects
	 */
	private static void onEndAll (ViewPort[] objs)
	{
		for (ViewPort obj : objs)
		{
			obj.onEnd ();
		}
	}

	/**
	Redraws the static background image to the buffer
	 */
	private static void refreshBuffer ()
	{
		Graphics2D g2d = (Graphics2D) buffer.getGraphics ();
		g2d.drawImage (background, 0, 0, null);
	}

	/**
	Slows the pace of the game (preferably to a human
	playable speed).
	 */
	public static void ralentirVotreRouleau ()
	{
		try
		{
			Thread.sleep (1000 / 60);
		}
		catch (InterruptedException e)
		{
		}
	}

	/**
	Allows sub process to signal the head that the game
	should end.
	 */
	public static void sigDone ()
	{
		done = true;
	}
}
