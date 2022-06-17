/** [BulletHellMain.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/

//import statements
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

public class BulletHellMain{
    
    //declaring class variables
    private static JFrame gameWindow;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static Room room;
    private static int gameCounter = 0;

    public static void main(String[] args){
        
        gameWindow = new JFrame("Bullet_Hell"); //create JFrame
        Player player = new Player(400 - 13.5, 300 - 17.5, 100,6, 6);
        room = new BossRoom(0,0, player); 
        player.setPresentRoom(room);

        gameWindow.add(room);
        
        //Setting up JFrame states
        gameWindow.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setVisible(true);
        gameWindow.setResizable(false);
        gameWindow.setIconImage(player.getSprites()[3]);
        gameWindow.pack();
        runGameLoop(); //loop keepa game running 
    }
    
    private static void runGameLoop() {
        while(true) {
            gameCounter++;
            gameWindow.repaint(); //repaint will do visuals
            room.runGame(); //method in gamePanel which does all the game stuff
            try  {Thread.sleep(20); //20 milliseconds between each frame, ~50 fps without lag
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static int getGameCounter() {
        return gameCounter;
    }

    public static JFrame getGameWindow() {
        return gameWindow;
    }

    public static void setRoom(Room room) {
        gameWindow.remove(BulletHellMain.room);
        BulletHellMain.room = room;
        gameWindow.add(room);
        room.getPlayer().setPresentRoom(room);
        gameWindow.pack();
    }

    public static void characterDeath() {
        JPanel deathPanel = new JPanel();
        deathPanel.setLayout(new BorderLayout());
        Font deathFont = new Font("Comic Sans MS", Font.PLAIN, 40);
        JLabel deathText = new JLabel("GAME OVER", 
        new ImageIcon(room.getPlayer().getSprites()[4]), 0);
        JButton tryAgainButton = new JButton("Try Again");
        tryAgainButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                    Player player = new Player(400 - 13.5, 300 - 17.5, 100,6, 6);
                    room = new BossRoom(0,0, player); 
                    player.setPresentRoom(room);
                    gameWindow.add(room);
                    gameWindow.remove(deathPanel);
                    gameWindow.pack();
                    room.requestFocusInWindow();
                    }  
                });  
        deathText.setHorizontalAlignment(JLabel.CENTER);
        deathText.setFont(deathFont);
        deathPanel.setBackground(Color.WHITE);
        gameWindow.remove(room);
        gameWindow.add(deathPanel);
        deathPanel.add(tryAgainButton, BorderLayout.PAGE_END);
        deathPanel.add(deathText, BorderLayout.CENTER);
        deathPanel.setVisible(true);
        gameWindow.pack();
    }
}