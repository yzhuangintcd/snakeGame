import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L; // eclipse self-generated
	static final int SCREEN_WIDTH = 1200;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
	static final int DELAY = 100;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	int applesEaten;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	int j = 0;

	GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}

	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
		if (running) {
			// for(int i=0; i < SCREEN_HEIGHT ;i++) {
			// g.setColor(Color.white);
			// g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
			// g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			// }
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			for (int i = 0; i < bodyParts; i++) {
				if (i == 0) {
					g.setColor(Color.BLACK);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				} else {
					g.setColor(new Color(40, 90, 0));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free", Font.BOLD, 40));
			getFontMetrics(g.getFont());
			g.drawString("SCORE: " + applesEaten, (SCREEN_WIDTH / 2 - 75),
					(SCREEN_HEIGHT / 2 - 235));
		} else {
			gameOver(g);
		}
	}

	public void newApple() {
		appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
		appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
	}

	public void move() {
		for (int i = bodyParts; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		switch (direction) {
			case 'U':
				y[0] = y[0] - UNIT_SIZE;
				break;
			case 'D':
				y[0] = y[0] + UNIT_SIZE;
				break;
			case 'L':
				x[0] = x[0] - UNIT_SIZE;
				break;
			case 'R':
				x[0] = x[0] + UNIT_SIZE;
				break;
		}

	}

	public void checkApple() {
		if ((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}

	public void checkCollisions() {
		// check if head collide with body
		for (int i = bodyParts; i < 0; i--) {
			if ((x[0] == x[i]) && y[0] == y[i]) {
				running = false;
			}
		}
		// check if head collide with left boarder
		if (x[0] < 0) {
			running = false;
		}
		// check if head collide with right boarder
		if (x[0] > SCREEN_WIDTH) {
			running = false;
		}
		// check if head collide with top boarder
		if (y[0] < 0) {
			running = false;
		}
		// check if head collide with bottom boarder
		if (y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		if (!running)
			timer.stop();

	}

	public void gameOver(Graphics g) {
		// Game over text

		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH / 2 - 200),
				(SCREEN_HEIGHT / 2));

		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		getFontMetrics(g.getFont());
		g.drawString("SCORE: " + applesEaten, (SCREEN_WIDTH / 2 - 75),
				(SCREEN_HEIGHT / 2 - 235));

		int scores[] = new int[9999];
		scores[j] = applesEaten;
		j++;
		int size = scores.length;
		Arrays.sort(scores);
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		getFontMetrics(g.getFont());
		g.drawString("HIGHEST SCORE: " + scores[size - 1], (SCREEN_WIDTH / 2 - 175),
				(SCREEN_HEIGHT / 2 - 100));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}

	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if (direction != 'R') {
						direction = 'L';
					}
					break;
				case KeyEvent.VK_RIGHT:
					if (direction != 'L') {
						direction = 'R';
					}
					break;
				case KeyEvent.VK_UP:
					if (direction != 'D') {
						direction = 'U';
					}
					break;
				case KeyEvent.VK_DOWN:
					if (direction != 'U') {
						direction = 'D';
					}
					break;
			}

		}
	}

}
