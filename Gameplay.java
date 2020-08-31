package brick;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener{
	private boolean play = false;
	private int score = 0;
	private int totalBricks = 21;
	private Timer timer;
	private int delay = 8;
	private int playerX = 310;

	//ball position and direction
	private int ballposx = 120;
	private int ballposy = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;
	
	private MapGenerator map;
	
	public Gameplay() {
		map = new MapGenerator(3, 7);
		addKeyListener(this); 
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
		
	}

	//Function to draw all graphics needed
	public void paint(Graphics g) {
		//background
		g.setColor(Color.black);
		g.fillRect(1,1, 692,592);
		
		//drawing map
		map.draw((Graphics2D)g);

		//borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);

		//score
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("SCORE"+score, 560, 30);

		//Paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);

		//ball
		g.setColor(Color.yellow);
		g.fillOval(ballposx, ballposy, 20, 20);

		//win senario
		if(totalBricks <= 0){
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("YOU WIN SCORE:"+score, 190, 300);

			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("PRESS ENTER TO RESTART", 190, 350);
		}

		//lose senario
		if(ballposy > 570){
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);

			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("GAME OVER, SCORE:"+score, 190, 300);

			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("PRESS ENTER TO RESTART", 190, 350);
		}

		g.dispose();

	}

	public void actionPerformed(ActionEvent arg0) {
		//Collision Code For ball
		timer.start();
		if(play) {
			if(new Rectangle(ballposx, ballposy, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballYdir = -ballYdir;
			}
			
		A:	for(int i = 0; i<map.map.length; i++) {
				for(int j = 0; j<map.map[0].length; j++) {
					if(map.map[i][j] >0) {
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposx, ballposy, 20, 20);
						Rectangle brickRect  = rect;
						
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score +=1;
							
							if(ballposx + 19 <= brickRect.x || ballposx + 1 >= brickRect.width) {
								ballXdir = -ballXdir;
							}
								else {
									ballYdir = - ballYdir;
								}
						
								break A;
										
							}
						}
					}
				}
			}
			
			ballposx += ballXdir;
			ballposy += ballYdir;
			if(ballposx < 0) {
				ballXdir = -ballXdir;
			}
			if(ballposy < 0) {
				ballYdir = -ballYdir;
			}
			if(ballposx >670) {
				ballXdir = -ballXdir;
			}

	
		
		repaint();
	
}

	public void keyPressed(KeyEvent e) {
		//Right Key Press
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX >= 600) {
				playerX = 600;
			}else {
				moveRight();
			}
		}
		//Left Key Press
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX <= 10) {
				playerX = 10;
			}else {
				moveLeft();
			}
		}
		//Enter Key Pres: Only Works in Game Over Senario, Resets Game
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(!play){
				play = true;
				ballposx = 120;
				ballposy = 350;
				ballXdir = -1;
				ballYdir = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3,7);
				repaint();
			}
		}
	}

	public void moveRight() {
		play = true;
		playerX+=20;
	}
	
	public void moveLeft() {
		play = true;
		playerX-=20;	
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
