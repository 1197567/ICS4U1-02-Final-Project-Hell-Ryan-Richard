/** [BulletHellMain.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/

//import statements
import javax.swing.JFrame;
import java.awt.Dimension;

public class BulletHellMain{
    
    //declaring class variables
    private static JFrame gameWindow;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static Room gamePanel;
    
    
    public static void main(String[] args){
        
        gameWindow = new JFrame("Bullet_Hell"); //create JFrame
        gamePanel = new BasicRoom(0,0); //create test jPanel
        gameWindow.add(gamePanel);
        
        //Setting up JFrame states
        gameWindow.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setVisible(true);
        gameWindow.pack();
        runGameLoop(); //loop keepa game running 
    }
    
    private static void runGameLoop() {
        while(true) {
            gameWindow.repaint(); //repaint will do visuals
            gamePanel.runGame(); //method in gamePanel which does all the game stuff
            try  {Thread.sleep(20); //20 milliseconds between each frame, ~50 fps without lag
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}