import javax.swing.JFrame;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	GameFrame(){
//		GamePanel panel = new GamePanel();
		// this.add(panel);
		// this can be replaced with 
		this.add(new GamePanel()); 
		this.setTitle("Snake Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack(); // this.pack fit the conponets nicely into our frame
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
	}
}
