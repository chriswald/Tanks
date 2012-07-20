package pkg;

import home.Tanks;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
A Container window for the Tanks' scores
@author Chris
 */
public class ScoreBoard extends ViewPort
{

	private static final long serialVersionUID = 1L;
	private static int score0 = 0;
	private static int score1 = 0;
	private static JLabel lscore0;
	private static JLabel lscore1;
	private static JPanel panel;

	/**
	Initializes a new ScoreBoard
	 */
	public ScoreBoard ()
	{
		super ("Score");

		panel = new JPanel (new GridLayout (1, 3));
		this.add (panel);

		lscore0 = new JLabel ("0");
		lscore0.setFont (new Font ("Calibri", Font.BOLD, 48));

		lscore1 = new JLabel ("0");
		lscore1.setFont (new Font ("Calibri", Font.BOLD, 48));

		panel.add (lscore1);
		panel.add (new JLabel (" "));
		panel.add (lscore0);

		this.setSize (200, 100);
		this.setLocation ((Tanks.SWIDTH / 2) - (this.getWidth () / 2), 10);

		this.setVisible (true);

		System.out.println ("ScoreBoard Initialized");
	}

	/**
	Called by Player 1 to increment score
	 */
	public void incrementScore1 ()
	{
		lscore0.setText ("" + (++score0));
		System.out.println (this);
	}

	/**
	Called by Player 2 to increment score
	 */
	public void incrementScore2 ()
	{
		lscore1.setText ("" + (++score1));
		System.out.println (this);
	}

	/**
	Disposes the window
	 */
	@Override
	public void onEnd ()
	{
		this.dispose ();
	}

	@Override
	public String toString()
	{
		return "Score " + lscore0.getText () + ", " + lscore1.getText ();
	}

	@Override
	public void collide(ViewPort[] objs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawToBuffer() {
		// TODO Auto-generated method stub

	}
}
