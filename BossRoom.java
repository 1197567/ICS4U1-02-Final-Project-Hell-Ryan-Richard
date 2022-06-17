/** [BasicRoom.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/

import java.awt.Graphics;
import java.awt.Color;

public class BossRoom extends Room {
    
    /*class variables
    * backgroundColor - color of the floor;
    * wallColor - color of the walls
    * horizontalDimension - horizontal dimension of room in squares
    * verticalDimension - verticalDimension of room in squares
    * roomFilling - 2d array showing a map of the room in filled squares
    * x - x position of the room on the players screen for viewing purposes
    * y - y position of the room on the players screen for viewing purposes
    */
    Cookie cookie;
    double maxHealth;
    
    
    public BossRoom(double x, double y, Player player) {
        super(x,y,52,52, player);
    }
    
    protected void generateRoomWalls() {
        for (int i = 0; i < horizontalDimension; i++) {
            generateWall(i, 0);
            generateWall(i, verticalDimension - 1);
        }
        for (int i = 1; i < verticalDimension - 1; i++) {
            generateWall(0, i);
            generateWall(horizontalDimension - 1, i);
        }
        
        for (int i = 0; i < 12; i++) {
            generateWall(7 + i, 7);
            generateWall(33 + i, 7);
            generateWall(7 + i, 44);
            generateWall(33 + i, 44);
        }

        for (int i = 1; i < 12; i++) {
            generateWall(7, 7 + i);
            generateWall(7, 33 + i);
            generateWall(44, 7 + i);
            generateWall(44, 33 + i);
        }
        for (int i = 0; i < 6; i++) {
            generateWall(19, 23 + i);
            generateWall(32, 23 + i);
            generateWall(23 + i, 19);
            generateWall(23 + i, 32);
        }
        for (int i = 1; i < 50; i++) {
            enemyList.add(new Chocolate(100, 50*i + 50, 0, 1, this));
            enemyList.add(new Chocolate(100, 50*i + 50, 0, -1, this));
            enemyList.add(new Chocolate(2450, 50*i + 50, 0, 1, this));
            enemyList.add(new Chocolate(2450, 50*i + 50, 0, -1, this));
            enemyList.add(new Chocolate(50*i + 50, 100, 1, 0, this));
            enemyList.add(new Chocolate(50*i + 50, 100, -1, 0, this));
            enemyList.add(new Chocolate(50*i + 50, 2450, 1, 0, this));
            enemyList.add(new Chocolate(50*i + 50, 2450, -1, 0, this));
        }
        enemyList.add(new Sugar(9*50, 9*50, 0, 0, this));
        enemyList.add(new Sugar(9*50, 41*50, 0, 0, this));
        enemyList.add(new Sugar(41*50, 9*50, 0, 0, this));
        enemyList.add(new Sugar(41*50, 41*50, 0, 0, this));
        cookie = new Cookie(1300, 1300, 0, 0, this);
        maxHealth = cookie.getHealth();
        enemyList.add(cookie);
        
    }

    @Override
    protected void drawHealthBar(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(0,25, 200, 25);
        g.setColor(Color.GREEN);
        g.fillRect(0,25, (int) (200.0*(player.getHealth()/player.getMaxHealth())), 25);

        g.setColor(Color.RED);
        g.fillRect(0,100, 200, 25);
        g.setColor(Color.GREEN);
        g.fillRect(0,100, (int) (200.0*(cookie.getHealth()/maxHealth)), 25);
      }
}
