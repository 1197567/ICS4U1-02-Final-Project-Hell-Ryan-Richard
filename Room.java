/** [Room.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.util.ArrayList; 

public abstract class Room extends JPanel {
  
  /*class variables
  * backgroundColor - color of the floor;
  * wallColor - color of the walls
  * horizontalDimension - horizontal dimension of room in squares
  * verticalDimension - verticalDimension of room in squares
  * roomFilling - 2d array showing a map of the room in filled squares
  * x - x position of the room on the players screen for viewing purposes
  * y - y position of the room on the players screen for viewing purposes
  */
  private Color backgroundColor;
  private Color wallColor;
  protected int horizontalDimension;
  protected int verticalDimension;
  protected boolean[][] roomFilling;
  private double x;
  private double y;
  private ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
  private boolean add = true;
  
  public Room(double x, double y, int horizontalDimension, int verticalDimension) {
    //temporary variable placeholders;
    this.backgroundColor = Color.WHITE;
    this.wallColor = Color.BLACK;
    this.horizontalDimension = horizontalDimension;
    this.verticalDimension = verticalDimension;
    this.roomFilling = new boolean[this.horizontalDimension][this.verticalDimension];
    this.x = x;
    this.y = y;
    generateRoomWalls();
    
    this.setFocusable(true);
    this.setOpaque(true);
    this.setPreferredSize(new Dimension(800, 600));
    this.setBackground(Color.GREEN);
    this.setVisible(true);
    this.revalidate();
  }
  
  protected abstract void generateRoomWalls();
  
  /**
  * paintComponent
  * method used to repeatedly update the JPanel
  * @param g - idk what this is but it's important
  */
  @Override
  public void paintComponent(Graphics g){ 
    super.paintComponent(g); //required
    drawRoom(g);
    for (int i = 0; i < bulletList.size(); i++) {
      bulletList.get(i).draw(x,y,g);
    }
  }
  
  
  public void drawRoom(Graphics g) {
    g.setColor(backgroundColor);
    g.fillRect((int) x,(int) y, horizontalDimension*50, verticalDimension*50);
    g.setColor(wallColor);
    for (int i = 0; i < roomFilling.length; i++) {
      for (int j = 0; j < roomFilling[i].length; j++) {
        if (roomFilling[i][j]) {
          g.fillRect((int) (x + j*50), (int) (y + i*50), 50, 50);
        }
      }
    }
  }  
  
  public void runGame() {
    for (int i = 0; i < bulletList.size(); i++) {
      bulletList.get(i).movement();
    }
    bulletCollision();
    //testing collision
    if (add) {
      for (int i = 0; i < 10; i++) {
        bulletList.add(new TestBullet());
      }
      add = false;
    }
  }
  
  public void bulletCollision() {
    for (int i = 0; i < bulletList.size(); i++) {
      /*
      if (((bulletList.get(i).getX()) < 50) || (bulletList.get(i).getX() > 250) || 
      (bulletList.get(i).getY() < 50) || (bulletList.get(i).getY() > 250)) {
        bulletList.get(i).collisionWithWall();
      }
      */
      if (roomFilling[(int) bulletList.get(i).getY()/50][(int) bulletList.get(i).getX()/50]) {
        bulletList.get(i).collisionWithWall((int) bulletList.get(i).getX()/50, 
        (int) bulletList.get(i).getY()/50);
      }
    }
  }
  
  
}
