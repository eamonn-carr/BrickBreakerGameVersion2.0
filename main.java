/* BrickBreakerGame Version 2.0
*
*  Code By Eamonn Carr
*
* */package brick;

import javax.swing.JFrame;

public class main {

	public static void main(String[] args) {
		JFrame obj = new JFrame(); 
		Gameplay gamePlay = new Gameplay();
		obj.setBounds(10,19,700,600);
		obj.setTitle("Breakout Ball");
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gamePlay);
	}
	

}
