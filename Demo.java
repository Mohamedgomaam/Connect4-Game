package assginment2;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class Demo extends JFrame {
public static String typeofsearch="";

	// Driver code.
	public static void main(String args[])
	{ 
		System.out.println(typeofsearch);
		Type();
		System.out.println(typeofsearch);
		
	}
	public static void Type() 
	{
		
		String[] types= {"Minimax","Alpha-Beta"};
	int choise=JOptionPane.showOptionDialog(null,"select Type of search ", "TYPE", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,types,0);
	if(choise==0) typeofsearch="Minimax";
	else if(choise==1)typeofsearch="Alpha_beta";
	
	}

}