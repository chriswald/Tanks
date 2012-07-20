package pkg;

import home.Tanks;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
Represents a Tank (Player)
@author Chris
*/
public class Tank extends ViewPort
{

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 150, HEIGHT = 150;
	private static final double DELTA_VELOCITY = .75d;
	private static final double DELTA_ANGLE = 1d;
	private static final double MAX_VELOCITY = 1.7d;
	private boolean movingLeft = false;
	private boolean movingRight = false;
	private static int PLAYER = 0;
	private int player_num;
	private Bullet bullet = null;
	private static ScoreBoard sb = null;

	/**
	Constructor for the Tank Class
	Initializes a new Tank, loads elements, and
	sets starting values
	*/
	public Tank ()
	{
		super ("Player " + (++PLAYER));
		this.player_num = PLAYER;
		this.setSize (WIDTH, HEIGHT);

		if (PLAYER == 1)
		{
			this.posx = Tanks.SWIDTH - this.getWidth ();
			this.posy = Tanks.SHEIGHT - this.getHeight ();
			this.angle = 90;
		}
		else
		{
			if (PLAYER == 2)
			{
				this.posx = 0;
				this.posy = 0;
				this.angle = 270;
			}
		}
		this.setLocation ((int) this.posx, (int) this.posy);
		this.setResizable (false);

		try
		{
			File sp = new File ("Tank" + PLAYER + ".png");
			this.sprite = (Image) ImageIO.read (sp);
		}
		catch (IOException e)
		{
			System.out.println ("Cannot load the background image \"background.jpg\"\n"
					+ "Please make sure it's still with the game's files."
					+ "If you want to create one of your own, you can do that too. Just make sure it's big enough"
					+ "to cover your screen, then name it \"background.jpg\" and put it with the game's files.");
			System.exit (2);
		}

		this.setVisible (true);
		
		System.out.println("Tank " + PLAYER + " Initialized");
	}

	/**
	Updates locations and does bounds checking
	Acts for both itself and its owned bullet if applicable
	*/
	@Override
	public void update ()
	{
		if (this.movingLeft)
		{
			this.angle = (this.angle + DELTA_ANGLE) % 360;
		}
		else
		{
			if (this.movingRight)
			{
				this.angle = (this.angle - DELTA_ANGLE) % 360;
			}
		}

		super.update ();
		this.keepInScreenBounds ();

		if (this.bullet != null)
		{
			this.bullet.update ();
			this.bullet.drawToBuffer ();
			this.bullet.repaint ();
			this.keepBulletInScreenBounds ();
		}
	}

	/**
	Stops the Tank if it leaves the bounds of the screen
	*/
	private void keepInScreenBounds ()
	{
		int x = (int) this.posx;
		int y = (int) this.posy;

		if (x < 0)
		{
			this.velocity = 0;
			this.posx = 0;
			this.posy = y;
			this.setLocation ((int) this.posx, (int) this.posy);
		}
		if (x > (Tanks.SWIDTH - this.getWidth ()))
		{
			this.velocity = 0;
			this.posx = Tanks.SWIDTH - this.getWidth ();
			this.posy = y;
			this.setLocation ((int) this.posx, y);
		}
		if (y < 0)
		{
			this.velocity = 0;
			this.posx = x;
			this.posy = 0;
			this.setLocation ((int) this.posx, (int) this.posy);
		}
		if (y > (Tanks.SHEIGHT - this.getHeight ()))
		{
			this.velocity = 0;
			this.posx = x;
			this.posy = (Tanks.SHEIGHT - this.getHeight ());
			this.setLocation ((int) this.posx, (int) this.posy);
		}
	}

	/**
	Destroys the bullet if it leaves the bounds of the screen
	*/
	private void keepBulletInScreenBounds ()
	{
		int x = this.bullet.getLocation ().x;
		int y = this.bullet.getLocation ().y;

		if (x < -this.bullet.getWidth () || x > Tanks.SWIDTH
				|| y < -this.bullet.getHeight () || y > Tanks.SHEIGHT)
		{
			new Wit ("FWOOSH!");
			this.requestFocus ();
			this.bullet.dispose ();
			this.bullet = null;
			return;
		}
	}

	/**
	Checks object type and calls proper collide() method
	@param objs The list of game objects
	*/
	@Override
	public void collide (ViewPort[] objs)
	{
		for (ViewPort obj : objs)
		{
			if (obj != this)
			{
				if (obj instanceof Tank)
				{
					this.collide ((Tank) obj);
				}
				if (obj instanceof Boulder)
				{
					this.collide ((Boulder) obj);
				}
			}
		}
	}

	/**
	Checks to see if this or this's bullet has collided with the passed Tank's tank Image
	@param frame The Tank to check against
	*/
	private void collide (Tank frame)
	{

		int x = this.getLocation ().x;
		int y = this.getLocation ().y;

		if (x > frame.getLocation ().x - this.sprite.getWidth (null)
				&& x < frame.getLocation ().x + frame.sprite.getWidth (null)
				&& y > frame.getLocation ().y - this.sprite.getHeight (null)
				&& y < frame.getLocation ().y + frame.sprite.getHeight (null))
		{
			this.velocity = 0;
		}

		if (this.bullet != null)
		{
			x = this.bullet.getLocation ().x;
			y = this.bullet.getLocation ().y;

			int tankx = frame.getLocation ().x + (frame.getWidth () / 2 - frame.sprite.getWidth (null) / 2);
			int tanky = frame.getLocation ().y + (frame.getHeight () / 2 - frame.sprite.getHeight (null) / 2);

			if (x > tankx - this.bullet.getBullet ().getWidth (null)
					&& x < tankx + frame.sprite.getWidth (null)
					&& y > tanky - this.bullet.getBullet ().getHeight (null)
					&& y < tanky + frame.sprite.getHeight (null))
			{
				this.onHit ();
			}
		}
	}

	/**
	Checks to see if this or this's bullet has collided with any of the passed boulders
	@param frame The Tank to check against
	*/
	private void collide (Boulder boulder)
	{


		int x = this.getLocation ().x;
		int y = this.getLocation ().y;

		if (x > boulder.getX () - WIDTH
				&& x < boulder.getX () + boulder.getWidth ()
				&& y > boulder.getY () - HEIGHT
				&& y < boulder.getY () + boulder.getHeight ())
		{
			this.velocity = 0;
		}

		if (this.bullet != null)
		{
			x = this.bullet.getLocation ().x;
			y = this.bullet.getLocation ().y;

			if (x > boulder.getX () - this.bullet.getWidth ()
					&& x < boulder.getX () + boulder.getWidth ()
					&& y > boulder.getY () - this.bullet.getHeight ()
					&& y < boulder.getY () + boulder.getHeight ())
			{
				new Wit ("KLANK!");
				this.requestFocus ();
				this.bullet.dispose ();
				this.bullet = null;
			}
		}

	}

	/**
	Draw this's tank and bullet (if applicable) to the global buffer
	*/
	@Override
	public void drawToBuffer ()
	{
		int x = this.getLocation ().x;
		int y = this.getLocation ().y;

		Graphics2D g2d = (Graphics2D) Tanks.buffer.getGraphics ();
		g2d.translate (x + (this.getWidth () / 2), y + (this.getHeight () / 2));
		g2d.rotate (this.degToRad (-(this.angle)));
		g2d.drawImage (this.sprite, -(this.sprite.getWidth (null) / 2), -(this.sprite.getHeight (null) / 2), null);
	}

	public static void pointToScoreBoard (ScoreBoard scoreboard)
	{
		sb = scoreboard;
	}

	/**
	Indicates that this's bullet has hit another tank
	*/
	public void onHit ()
	{
		if (this.player_num == 1)
		{
			sb.incrementScore1 ();
		}
		else
		{
			sb.incrementScore2 ();
		}
		new Wit ("POW!");
		this.requestFocus ();
		this.bullet.dispose ();
		this.bullet = null;
	}

	/**
	Indicates that this should be rotating counterclockwise
	Called by the ArrowKeyListener
	*/
	@Override
	public void startMoveLeft ()
	{
		this.endMoveRight ();
		this.movingLeft = true;
	}

	/**
	Indicates that this should stop rotating counterclockwise
	Called by the ArrowKeyListener
	*/
	@Override
	public void endMoveLeft ()
	{
		this.movingLeft = false;
	}

	/**
	Indicates that this should be rotating clockwise
	Called by the ArrowKeyListener
	*/
	@Override
	public void startMoveRight ()
	{
		this.endMoveLeft ();
		this.movingRight = true;
	}

	/**
	Indicates that this should stop rotating clockwise
	Called by the ArrowKeyListener
	*/
	@Override
	public void endMoveRight ()
	{
		this.movingRight = false;
	}

	/**
	Indicates that this should be moving in reverse
	Called by the ArrowKeyListener
	*/
	@Override
	public void startMoveDown ()
	{
		if (this.velocity < MAX_VELOCITY)
		{
			this.velocity += DELTA_VELOCITY;
		}
	}

	/**
	Not Used
	*/
	@Override
	public void endMoveDown ()
	{
	}

	/**
	Indicates that this should be moving forward
	Called by the ArrowKeyListener
	*/
	@Override
	public void startMoveUp ()
	{
		if (this.velocity > -MAX_VELOCITY)
		{
			this.velocity -= DELTA_VELOCITY;
		}
	}

	/**
	Not Used
	*/
	@Override
	public void endMoveUp ()
	{
	}

	/**
	Indicates that this should fire its weapon
	Called by the ArrowKeyListener
	*/
	@Override
	public void fireWeapon ()
	{
		if (this.bullet == null && this.velocity < MAX_VELOCITY && this.velocity > -MAX_VELOCITY)
		{
			this.bullet = new Bullet (this.getLocation ().x + (this.getWidth () / 2 - Bullet.WIDTH / 2), this.getLocation ().y + (this.getHeight () / 2 - Bullet.HEIGHT / 2), this.angle - 180);
			new Wit ("BANG!");
			this.requestFocus ();
		}
	}

	/**
	Indicates that the escape button has been pressed
	Let the main game loop know that the game should end now
	Called by the ArrowKeyListener
	*/
	@Override
	public void onClose ()
	{
		Tanks.sigDone ();
	}

	/**
	Routines to dispose this's window and bullet (if applicable)
	*/
	@Override
	public void onEnd ()
	{
		this.dispose ();
		PLAYER--;
		if (this.bullet != null)
		{
			this.bullet.dispose ();
		}
	}

	/**
	Repaints the buffer to this window
	@param g The graphics object of the window to draw to
	*/
	@Override
	public void paint (Graphics g)
	{
		int x = this.getLocation ().x;
		int y = this.getLocation ().y;

		g.drawImage (Tanks.buffer, -x, -y, null);
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
