package assginment2;

import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class password  {
private int key=6;
private static int length=0;
private static String word="";
private static ArrayList<String>list=new ArrayList<>();
private static String myPassword;
private char[] Encryption;
private static String encryptedpassword="";
private String lowercase="qwertyuiopasdfghjklzxcvbnm";
private String uppercase="QWERTYUIOPASDFGHJKLZXCVBNM";
private String numbers="0123456789";
private String special="!@#$%^&*()_+=-";
public void genratepassword(int length){
   String pass=lowercase+uppercase+numbers+special;
   char[]password=new char[length];
   int i=0;
   for (i = 0; i<length; i++){
       int random= (int) (Math.random()*pass.length());
       password[i]=pass.charAt(random);
   }
	  myPassword=String.valueOf(password); 
     
   if (list.contains(myPassword)){
       genratepassword(length);
   }
   else
       list.add(myPassword);   
}

public password() 
{
	genratepassword(length);
	//System.out.println(myPassword);
	Encryption();
}

public void Encryption() 
{
	Encryption=myPassword.toCharArray();
	for(char c:Encryption) 
	{
	c+=key;
	encryptedpassword+=c;	
	}
	//System.out.println("password after encrpted : "+encryptedpassword);
}

public static void showpass() 
{
	String ww="your password is : "+myPassword;
	JOptionPane.showMessageDialog(null, ww, "Password", JOptionPane.PLAIN_MESSAGE);
}

public static void showencryptedpass() 
{
int ans=JOptionPane.showConfirmDialog(null, "do you want to Encrypt your password?", "Encryption", JOptionPane.YES_NO_CANCEL_OPTION);
if(ans==0)
{
	String ww="you pass word is : "+encryptedpassword;
	JOptionPane.showMessageDialog(null, ww, "Password", JOptionPane.PLAIN_MESSAGE);
}
}

public static void enterlength() { 
	String[] options = { "OK" };
	JPanel rpan = new JPanel();
	JLabel rlabel = new JLabel("enter length of your password");
	JTextField rtext = new JTextField(10);
	rpan.add(rlabel);
	rpan.add(rtext);
	int ok = JOptionPane.showOptionDialog(null, rpan, "Length of password",
			JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
			options, options[0]);
	if (ok == 0) {
			 word=(rtext.getText());
	length=Integer.valueOf(word);	
	}
}
public static void main(String args[])
{ 
enterlength();
password x=new password();	
showpass();
showencryptedpass();
}
}