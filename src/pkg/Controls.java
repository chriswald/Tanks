package pkg;

import home.Tanks;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
Shows the controls and signals the start of the game
@author Chris
*/
public class Controls extends javax.swing.JFrame
{
	Image img = null;
	
	/**
	Initializes the new window
	*/
	public Controls()
	{
		try
		{
			File file = new File("Controls.jpg");
			img = ImageIO.read (file);
			this.setSize (img.getWidth (null) + 15, img.getHeight (null) + 40);
			this.setLocation ((Tanks.SWIDTH / 2) - (this.getWidth () / 2), (Tanks.SHEIGHT / 2) - (this.getHeight () / 2));
			initComponents();
		}
		catch (IOException ex) {}
		
		this.setVisible (true);  
	}
	
	/**
	Paints the image to the window
	@param g System supplied graphics object
	*/
	public void paint(Graphics g)
	{
		super.paint (g);
		g.drawImage (img, 5, 10, null);
	}

	/** This method is called from within the init() method to
	initialize the form.
	WARNING: Do NOT modify this code. The content of this method is
	always regenerated by the Form Editor.
	*/
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        start_button = new java.awt.Button();

        setLayout(new java.awt.BorderLayout());

        start_button.setLabel("Start Game");
        start_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                start_buttonActionPerformed(evt);
            }
        });
        add(start_button, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

	/**
	Called when the button is clicked
	@param evt Not Used
	*/
	private void start_buttonActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_start_buttonActionPerformed
	{//GEN-HEADEREND:event_start_buttonActionPerformed
		Tanks.can_start = true;
		this.dispose();
	}//GEN-LAST:event_start_buttonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button start_button;
    // End of variables declaration//GEN-END:variables
}