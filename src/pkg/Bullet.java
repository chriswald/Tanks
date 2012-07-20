package pkg;

import home.Tanks;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
Represents a bullet and allows for the tanks to interact in combat
@author Chris
*/
public class Bullet extends JFrame
{

	private static final long serialVersionUID = 1L;
	private static final double VELOCITY = 21;
	public  static final int WIDTH = 15, HEIGHT = 15;
	private double angle;
	private double posx, posy;
	private static Image bullet;
	private static Image background;

	/**
	Initializes a bullet at the given location and angle
	@param x The x position
	@param y The y position
	@param angle The trajectory
	*/
	public Bullet (int x, int y, double angle)
	{
		super ("Bullet");
		this.setSize (WIDTH, HEIGHT);
		this.posx = x;
		this.posy = y;
		this.setLocation ((int) this.posx, (int) this.posy);
		this.setDefaultCloseOperation (DISPOSE_ON_CLOSE);
		this.setResizable (false);
		this.setUndecorated (true);

		this.angle = angle;

		try
		{
			Robot rbt = new Robot ();
			background = (Image) rbt.createScreenCapture (new Rectangle (0, 0, Tanks.SWIDTH, Tanks.SHEIGHT));
		}
		catch (AWTException e)
		{
			System.out.println (e.getMessage ());
			background = Tanks.buffer;
		}
		catch (SecurityException e)
		{
			System.out.println ("We don't have permissions to create a screen capture!\n"
					+ "Try running the program as administrator next time.");
			System.out.println (e.getLocalizedMessage ());
			background = Tanks.buffer;
		}

		try
		{
			File sp = new File ("bullet.jpg");
			bullet = ImageIO.read (sp);
		}
		catch (IOException e)
		{
			System.out.println (e.getLocalizedMessage ());
		}
		catch (NullPointerException e)
		{
			System.out.println (e.getLocalizedMessage ());
		}
		catch (IllegalArgumentException e)
		{
			System.out.println (e.getLocalizedMessage ());
		}

		this.setVisible (true);
		
		System.out.println ("Bullet Initialized");
	}

	/**
	Paints background to the bullet's window
	@param g System provided graphics object
	*/
	@Override
	public void paint (Graphics g)
	{
		int x = this.getLocation ().x;
		int y = this.getLocation ().y;
		g.drawImage (background, -x, -y, null);
	}

	/**
	Move the bullet on the screen
	*/
	public void update ()
	{
		double velx = VELOCITY * Math.sin (this.degToRad (this.angle));
		double vely = VELOCITY * Math.cos (this.degToRad (this.angle));

		this.posx += velx;
		this.posy += vely;

		this.setLocation ((int) (this.posx), (int) (this.posy));
	}

	/**
	Draws the bullet to its own buffer
	*/
	public void drawToBuffer ()
	{
		int x = this.getLocation ().x;
		int y = this.getLocation ().y;

		Graphics2D g2d = (Graphics2D) background.getGraphics ();
		g2d.translate (x + (this.getWidth () / 2), y + (this.getHeight () / 2));
		g2d.rotate (this.degToRad (-(this.angle - 180)));
		g2d.drawImage (bullet, -(bullet.getWidth (null) / 2), -(bullet.getHeight (null) / 2), null);
	}

	/**
	Returns the bullet's Image object
	@return The Bullet Image
	*/
	public Image getBullet ()
	{
		return bullet;
	}

	/**
	Converts degree measures to radians
	@param degree The angle to convert
	@return The supplied angle in radians
	*/
	private double degToRad (double degree)
	{
		return degree * Math.PI / 180;
	}
}
