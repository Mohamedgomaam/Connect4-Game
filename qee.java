package assginment2;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class qee {

public static void main(String[] arges){
	ImageIcon icon=new ImageIcon("origami.png");
	
	JButton button=new JButton("neger");
	button.setBounds(300, 300, 100, 100);
	button.setBackground(Color.yellow);
	button.setFont(new Font("comic sans",Font.BOLD,20));
	button.setFocusable(false);
	button.addActionListener(e->System.out.println("negeeeeeeer"));
	
/*JPanel panel=new JPanel();	
panel.setBackground(Color.black);
panel.setBounds(0, 250, 500, 250);

JLabel label=new JLabel();

label.setIcon(icon);
label.setText("HIOO");
*/



JFrame frame=new JFrame();

frame.setTitle("GOAT");
frame.getContentPane().setBackground(Color.green);
frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
frame.setIconImage(icon.getImage());
frame.setLayout(null);
frame.setSize(750, 750);
frame.setVisible(true);
frame.add(button);
//panel.add(label);
//frame.add(panel);



}	
}
