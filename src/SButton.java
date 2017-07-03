/**
 * \brief
 * @author Jignesh Chudasama
 */
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JButton;
/**
 * This class creates the buttons used to control the game.
 */

class SButton extends JButton   //create a JButton with some fixed properties
{
	private static final long serialVersionUID = 1L;
	Color DB = new Color(0x00, 0x00, 0xcd);
        Color WS = new Color(0xf5, 0xf5, 0xf5);  //White Smoke background
    public SButton(String action, String command) 
    {
       super(action);  //construct button
       this.setBackground(WS);
       this.setForeground(DB);
       this.setBorder(BorderFactory.createBevelBorder(0, DB, DB));       
       this.setActionCommand(command);        
    }//end of constructor     
    public Dimension getPreferredSize() 
    {
        return new Dimension(130,30);  //nice size button
    }//end of get dimension    
}//end of SButton

