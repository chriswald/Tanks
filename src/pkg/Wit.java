package pkg;

import home.Tanks;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
Display on-screen messages to the user
@author Chris
*/
public class Wit extends JFrame implements ActionListener
{

	private static final long serialVersionUID = 1L;
	private float alpha = 1;
	private String slogan;
	private static Image background;
	private Timer tic = new Timer (1000 / 40, this);

	/**
	Initializes a new window with the slogan as text
	@param slogan The text to display
	*/
	public Wit (String slogan)
	{
		super ();

		this.slogan = slogan;

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

		this.setSize (300, 200);
		this.setUndecorated (true);

		JLabel label = new JLabel (slogan);
		label.setFont (new Font ("Calibri", Font.BOLD, 72));
		label.setForeground (Color.red);
		label.setBackground (new Color (0, 0, 0, 0));

		this.add (label);

		this.pack ();
		this.setLocation ((Tanks.SWIDTH / 2) - (this.getWidth () / 2), (Tanks.SHEIGHT / 2) - (this.getHeight () / 2));

		this.tic.start ();
		this.setVisible (true);
		
		System.out.println ("Wit Initialized");
	}

	/**
	Paints the background and message to the window
	@param g System supplied graphics object
	*/
	@Override
	public void paint (Graphics g)
	{
		g.drawImage (background, -this.getX (), -this.getY (), null);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setFont (new Font ("Calibri", Font.BOLD, 72));
		g2d.setColor (new Color(255, 0, 0, (int) (255 * alpha)));	//Red with diminshing alpha
		g2d.drawString (this.slogan, 0, this.getHeight () - 10);
	}

	/**
	Called to decrement alpha value whenever the timer "tics"
	@param arg0 Not Used
	*/
	@Override
	public void actionPerformed (ActionEvent arg0)
	{
		this.alpha -= .07;
		if (this.alpha <= 0)
		{
			this.dispose ();
			this.tic.stop ();
		}
		this.repaint ();
	}
}
