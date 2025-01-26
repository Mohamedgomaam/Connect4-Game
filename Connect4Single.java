package assginment2;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class Connect4Single extends JFrame implements ActionListener {
	
	public static final int row = 6, column = 7;
	public static int score=0,score1 = 0, score2 = 0;
	public static String name1;
	public static int searchdepth = 7;
	public static final int INF = 0x7fffffff;
	public static int move = 0;
	public static boolean playeroneturn = true, inprogress = false;
	public static int gridstate[][] = new int[row + 1][column];
	public static int gridheight[] = new int[column];
    public static JLabel labelgrid[][] = new JLabel[row][column];
	public static JButton inputButton[] = new JButton[column];
	public static JLabel message = new JLabel("Welcome to Connect 4!");
	public static JButton surrenderButton = new JButton("Surrender?");
	public static JLabel p1score = new JLabel(), p2score = new JLabel();
	public static JPanel boardPanel = new JPanel();
	public static JPanel buttonPanel = new JPanel();
	public static ImageIcon empty, p1, p2,myicon;
    public static String typeofsearch=" ";
	
	public Connect4Single() {
 
		empty = new ImageIcon("empty.jpg");
		p1 = new ImageIcon("red.jpg");
		p2 = new ImageIcon("yellow.jpg");
        myicon=new ImageIcon("origami.png");
        setIconImage(myicon.getImage());
		add(boardPanel);
		add(buttonPanel);
		setLayout(new FlowLayout());
		boardPanel.setLayout(new GridLayout(row + 1, column));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		p1score.setText(name() + ": " + score1);
		p2score.setText("Computer: " + score2);

		for (int i = 0; i < column; i++) {
			inputButton[i] = new JButton(i + "");
			boardPanel.add(inputButton[i]);
			inputButton[i].addActionListener(this);
			inputButton[i].setEnabled(true);
		}

		for (int i = row - 1; i >= 0; i--) {
			for (int j = 0; j < column; j++) {
				labelgrid[i][j] = new JLabel();
				labelgrid[i][j].setIcon(empty);
				boardPanel.add(labelgrid[i][j]);
			}
		}
		
		buttonPanel.add(surrenderButton);
		surrenderButton.setEnabled(false);
		buttonPanel.add(message);
		buttonPanel.add(p1score);
		buttonPanel.add(p2score);

		message.setFont(new Font("Arial Black", Font.PLAIN, 20));
		p1score.setFont(new Font("Arial Black", Font.PLAIN, 20));
		p1score.setForeground(Color.RED);
		p2score.setFont(new Font("Arial Black", Font.PLAIN, 20));
		p2score.setForeground(Color.YELLOW);
		surrenderButton.addActionListener(this);

		setTitle("Connect 4");
		setSize(1100, 850);
		setResizable(true);
		setVisible(false);
		whogoesfirst();
		Type();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command.equals("Surrender?")) {
			int reply = JOptionPane.showConfirmDialog(null, name()
					+ ", do you wish to concede?", name() + " Give Up?",
					JOptionPane.YES_NO_OPTION);
			
			if (reply == JOptionPane.YES_OPTION) {
				p2score.setText("Computer: " + ++score2);
				reset();
			}
		} 
			
		 else {
			 
			surrenderButton.setEnabled(true);
			int num = Integer.parseInt(command);
			inprogress = true;
			labelgrid[gridheight[num]][num].setIcon(p1);
	        gridstate[gridheight[num]][num] = 1;
			
			if(check(gridstate, gridheight[num], num) > 0) {
				message.setText(name() + " wins!");
				JOptionPane.showMessageDialog(null, name() + " wins!",
						"Victory", JOptionPane.INFORMATION_MESSAGE);
			
			
				p1score.setText(name() + ": " + ++score1);
				reset();
			    }
			
			else if (fullgrid(gridstate)) {
				JOptionPane.showMessageDialog(null, "DRAW!!", "",
						JOptionPane.INFORMATION_MESSAGE);
				reset();
			} else {
				gridheight[num]++;
				if (gridheight[num] >= row)
					inputButton[num].setEnabled(false);
				for (int i = 0; i < column; i++) {
					gridstate[row][i] = gridheight[i];
				}
				
				if(typeofsearch.equals("Alpha_beta")) 
				{	
					int moveVal = minimax_alpha_beta(gridstate, gridheight[num] - 1, num,-INF,INF, searchdepth, false);	
				}
				else if(typeofsearch.equals("Minimax")) 
				{
					int moveVal = minimax(gridstate, gridheight[num] - 1, num,-INF,INF, searchdepth, false);
				}
				else 
				{
					int moveVal = minimax_alpha_beta(gridstate, gridheight[num] - 1, num,-INF,INF, searchdepth, false);
				}
				
				if (move == -1) {
					for (int i = 0; i < column; i++)
						if (gridheight[i] < row)
							move = i;
				}
					labelgrid[gridheight[move]][move].setIcon(p2);
				    gridstate[gridheight[move]][move] = 2;

				if (check(gridstate, gridheight[move], move) == 2) {
					message.setText("Computer win");
				JOptionPane.showMessageDialog(null, "Computer win",
							"Victory", JOptionPane.INFORMATION_MESSAGE);
					
					p2score.setText("Computer: " + ++score2);
					reset();
				     }
				else if (fullgrid(gridstate)) {
					JOptionPane.showMessageDialog(null, "DRAW!!",
							"", JOptionPane.INFORMATION_MESSAGE);
					reset();
				     } 
				else {
					message.setText("Player one's turn");
					gridheight[move]++;
					if (gridheight[move] >= row)
						inputButton[move].setEnabled(false);
				}
			}
		}
	}
	
public static String name() {
		if (name1.equals("")) {
			return "Player 1";
		} else {
			return name1;
		}
	}

	public static void reset() {
		for (int i = 0; i < column; i++) {
			inputButton[i].setEnabled(true);
			gridheight[i] = 0;
		}
		inprogress = false; 
		for (int i = 0; i < row; i++)
			for (int j = 0; j < column; j++) {
				gridstate[i][j] = 0;
				labelgrid[i][j].setIcon(empty);
			}
	
		surrenderButton.setEnabled(false);
		whogoesfirst();
	
		for (int i = 0; i < column; i++)
			gridstate[row][i] = 0;
	}
	
   public static int minimax_alpha_beta(int[][] checkBoard, int x, int y, int alpha,
			int beta, int depth, boolean playerturn) {
		int temp = check(checkBoard, x, y);
		if (temp == 1) {
			return -INF;
		}
		if (temp == 2) {
			return INF;
		}
		if (fullgrid(checkBoard))
			return 0;
		if (depth == 0) {
			return Heuristic(checkBoard, playerturn);
		}

		if (depth == searchdepth)
			move = -1;
		
		if (playerturn) {
			for (int i = 0; i < column; i++) {
				if (checkBoard[row][i] >= row)
					continue;
				int[][] newBoard = new int[row + 1][column];
			
				for (int k = 0; k <= row; k++) {
					for (int j = 0; j < column; j++) {
						newBoard[k][j] = checkBoard[k][j];
					}
				}
				newBoard[checkBoard[row][i]][i] = 1;
				newBoard[row][i]++;
				int val = minimax_alpha_beta(newBoard, checkBoard[row][i], i, alpha, beta,depth - 1, !playerturn);
				if (val < beta) {
					beta = val;
					if (depth == searchdepth) {
							move = i;
					}
				}
				if (beta <= alpha) {
					return beta;
				}
			}
			return beta;
		} else {
			for (int i = 0; i < column; i++) {
				if (checkBoard[row][i] >= row) {
					continue;
				}
				int[][] newBoard = new int[row + 1][column];
				for (int k = 0; k <= row; k++) {
					for (int j = 0; j < column; j++) {
						newBoard[k][j] = checkBoard[k][j];
					}
				}
				newBoard[checkBoard[row][i]][i] = 2;
				newBoard[row][i]++;
				int val = minimax_alpha_beta(newBoard, checkBoard[row][i], i, alpha, beta,
						depth - 1, !playerturn);
				if (val > alpha) {
					alpha = val;
					if (depth == searchdepth) { 
							move = i;
					}
				}
				if (beta <= alpha) {
					return alpha;
				}	
			}
			return alpha;
		}
	}
	
	public static int minimax(int[][] checkBoard, int x, int y, int max,int min, int depth, boolean playerturn) {
		int temp = check(checkBoard, x, y);
		if (temp == 1) {
			return -INF;
		}
		if (temp == 2) {
			return INF;
		}
		if (fullgrid(checkBoard))
			return 0;
		if (depth == 0) {
			return Heuristic(checkBoard, playerturn);
		}
     if (depth == searchdepth)
			move = -1;
		if (playerturn) {
		for (int i = 0; i < column; i++) {
				if (checkBoard[row][i] >= row)
					continue;
				int[][] newBoard = new int[row + 1][column];
				for (int k = 0; k <= row; k++) {
					for (int j = 0; j < column; j++) {
						newBoard[k][j] = checkBoard[k][j];
					}
				}
				newBoard[checkBoard[row][i]][i] = 1;
				newBoard[row][i]++;
				int val = minimax(newBoard, checkBoard[row][i], i, max, min,depth - 1, !playerturn);
				if (val < min) {
					min = val;
					if (depth == searchdepth) {
						move = i;
					}
				}
			}
			
		return min;
			
		} else {
			
			for (int i = 0; i < column; i++) {
				if (checkBoard[row][i] >= row) {
					continue;
				}
				int[][] newBoard = new int[row + 1][column];
				for (int k = 0; k <= row; k++) {
					for (int j = 0; j < column; j++) {
						newBoard[k][j] = checkBoard[k][j];
					}
				}
				newBoard[checkBoard[row][i]][i] = 2;
				newBoard[row][i]++;
				int val = minimax(newBoard, checkBoard[row][i], i, max, min,depth - 1, !playerturn);
				if (val > max) {
					max = val;
					if (depth == searchdepth) { 
							move = i;
					}
				}
			}
			return max;
		}
	}
	
	
public static int Heuristic(int[][] checkboard, boolean playerturn) {
		int finalVal = 0;
		int num0, num1, num2;
	for (int i = 0; i < row; i++) {
			for (int j = 0; j < column - 3; j++) {
				num0 = 0;
				num1 = 0;
				num2 = 0;
				for (int k = j; k < j + 4; k++) {
					if (checkboard[i][k] == 0)
						num0++;
					if (checkboard[i][k] == 1)
						num1++;
					if (checkboard[i][k] == 2)
						num2++;
				}
			if (num0 == 1) {
					if (num1 == 3)
						finalVal -= (50 * (row - i));
					else if (num2 == 3)
						finalVal += (50 * (row - i));
				} else if (num0 == 2) {
					if (num1 == 2)
						finalVal -= (20 * (row - i));
					else if (num2 == 2)
						finalVal += (20 * (row - i));
				}
			}
		}
		
		 //check the connected 3's and 2's in a column 
		for (int i = 0; i < row - 3; i++) {
			for (int j = 0; j < column; j++) {
				num0 = 0;
				num1 = 0;
				num2 = 0;
				for (int k = i; k < i + 4; k++) {
					if (checkboard[k][j] == 0)
						num0++;
					if (checkboard[k][j] == 1)
						num1++;
					if (checkboard[k][j] == 2)
						num2++;
				}
				//bady kyam ll path 3l4an minimax 
				if (num0 == 1) {
					if (num1 == 3)
						finalVal -= 50;
					else if (num2 == 3)
						finalVal += 50;
				} else if (num0 == 2) {
					if (num1 == 2)
						finalVal -= 20;
					else if (num2 == 2)
						finalVal += 20;
				}
			}
		}
		
		 //check the connected 3's and 2's in a rising diagonal  
		for (int i = 0; i < row - 3; i++) {
			for (int j = 0; j < column - 3; j++) {
				num0 = 0;
				num1 = 0;
				num2 = 0;
				int ali = 0;
				for (int k = 0; k < 4; k++) {
					if (checkboard[i + k][j + k] == 0) {
						ali = i + k;
						num0++;
					}
					else if (checkboard[i + k][j + k] == 1)
						num1++;
					else if (checkboard[i + k][j + k] == 2)
						num2++;
				}
				if (num0 == 1) {
					if (num1 == 3)
						finalVal -= (50 * (row - ali));
					else if (num2 == 3)
						finalVal += (50 * (row - ali));
				} else if (num0 == 2) {
					if (num1 == 2)
						finalVal -= (20 * (row - ali));
					else if (num2 == 2)
						finalVal += (20 * (row - ali));
				}
			}
		}
		
		 //check the connected 3's and 2's in a falling diagonal 
		for (int i = 0; i < row - 3; i++) {
			for (int j = 3; j < column; j++) {
				num0 = 0;
				num1 = 0;
				num2 = 0;
				int gomaa = 0;
				for (int k = 0; k < 4; k++) {
					if (checkboard[i + k][j - k] == 0) {
						gomaa = i + k;
						num0++;
					}
					if (checkboard[i + k][j - k] == 1)
						num1++;
					if (checkboard[i + k][j - k] == 2)
						num2++;
				}
				if (num0 == 1) {
					if (num1 == 3)
						finalVal -= (50 * (row - gomaa));
					else if (num2 == 3)
						finalVal += (50 * (row - gomaa));
				} else if (num0 == 2) {
					if (num1 == 2)
						finalVal -= (20 * (row - gomaa));
					else if (num2 == 2)
						finalVal += (20 * (row - gomaa));
				}
			}
		}
		return finalVal;
	}

public static int check(int[][] checkBoard, int x, int y) {
		boolean win = true;
		int temp = checkBoard[x][y];
		for (int i = x - 3; i <= x; i++) {
			if (i < 0)
				continue;
			win = true;
			for (int j = i; j <= i + 3; j++) {
				if (j >= row) {
					win = false;
					break;
				}
				if (checkBoard[j][y] != temp)
					win = false;
			}
			if (win) 
				return temp;
		}
		
		for (int i = y - 3; i <= y; i++) {
			if (i < 0)
				continue;
			win = true;
			for (int j = i; j <= i + 3; j++) {	
				if (j >= column) {
					win = false;
					break;
				}
				if (checkBoard[x][j] != temp)
					win = false;
			}
			if (win) 
				return temp;
		}
		
		for (int i = -3; i <= 0; i++) {
			if ((x + i) < 0)
				continue;
			if ((y + i) < 0)
				continue;
			win = true;
			for (int j = i; j <= i + 3; j++) {	
				if ((x + j) >= row || (y + j) >= column) {
					win = false;
					break;
				}
				if (checkBoard[x + j][y + j] != temp)
					win = false;
			}
			if (win) 
				return temp;
		}
		
		for (int i = -3; i <= 0; i++) {
			if ((x + i) < 0)
				continue;
			if ((y - i) >= column)
				continue;
			win = true;
			for (int j = i; j <= i + 3; j++) {
				if ((x + j) >= row || (y - j) < 0) {
					win = false;
					break;
				}
				if (checkBoard[x + j][y - j] != temp)
					win = false;
			}
			if (win) 
				return temp;
		}
	
		return 0;
	}
public static boolean fullgrid(int grid[][]) {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				if (grid[i][j] == 0)
					return false;
			}
		}
		return true;
	}
	public static void playernames() { 
		String[] options = { "OK" };
		JPanel rpan = new JPanel();
		JLabel rlabel = new JLabel("What is player 1's name?");
		JTextField rtext = new JTextField(10);
		rpan.add(rlabel);
		rpan.add(rtext);
		int ok = JOptionPane.showOptionDialog(null, rpan, "Player 1's name?",
				JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, options[0]);
		if (ok == 0) {
			name1 = (rtext.getText());
		}
	}

	public static void whogoesfirst() {
		int reply = JOptionPane
				.showConfirmDialog(null, "Do you wish to play first?", "Turn",
						JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			playeroneturn = true;
		} else {
			gridstate[0][3] = 2;
			gridheight[3] = 1;
			gridstate[row][3] = 1;
		    labelgrid[0][3].setIcon(p2);
			playeroneturn = true;
		}
	}

	public static void Type() 
	{
		String[] types= {"Minimax","Alpha-Beta"};
	int choise=JOptionPane.showOptionDialog(null,
			"select Type of search ",
			"TYPE",
			JOptionPane.YES_NO_OPTION,
			JOptionPane.INFORMATION_MESSAGE,
			myicon, 
			types
			,0);
	if(choise==0) typeofsearch="Minimax";
	else if(choise==1)typeofsearch="Alpha_beta";
	
	}
	public static void main(String[] args) {
		playernames();
		Connect4Single a = new Connect4Single();
		a.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	


}
