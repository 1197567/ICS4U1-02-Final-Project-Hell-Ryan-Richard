/** [Room.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/

//import statements
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.util.ArrayList; 
import java.awt.Rectangle;

public abstract class Room extends JPanel {
  /*
  * backgroundColor - color of the floor;
  * wallColor - color of the walls
  * horizontalDimension - horizontal dimension of room in squares
  * verticalDimension - verticalDimension of room in squares
  * roomFilling - 2d array showing a map of the room in filled squares
  * x - x position of the room on the players screen for viewing purposes
  * y - y position of the room on the players screen for viewing purposes
  * bulletList - arrayList of bullets
  * add - testing variable used to add an amount of bullets when starting
  */
  private Color backgroundColor;
  private Color wallColor;
  protected int horizontalDimension;
  protected int verticalDimension;
  protected boolean[][] roomFilling;
  protected Rectangle[][] roomHitBox;
  private double x;
  private double y;
  private ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
  private boolean add = true;
  private Player player;
  
  public Room(double x, double y, int horizontalDimension, int verticalDimension) {
    //temporary variable placeholders;
    this.backgroundColor = Color.WHITE;
    this.wallColor = Color.BLACK;
    this.horizontalDimension = horizontalDimension;
    this.verticalDimension = verticalDimension;
    this.roomFilling = new boolean[this.horizontalDimension][this.verticalDimension];
    this.roomHitBox = new Rectangle[this.horizontalDimension][this.verticalDimension];
    this.x = x;
    this.y = y;
    //maybe make a generateRoom too because all rooms will have walls?
    generateRoomWalls();
    
    //JPanel settings
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
    //draws all the bullets in the bulletList
    for (int i = 0; i < bulletList.size(); i++) {
      bulletList.get(i).draw(x,y,g);
    }
  }
  
  /**
  * drawRoom
  * used to draw the wals of the room, maybe used for more later
  * @param g - idk what this is but it's important
  */
  public void drawRoom(Graphics g) {
    g.setColor(backgroundColor);
    g.fillRect((int) x,(int) y, horizontalDimension*50, verticalDimension*50);
    g.setColor(wallColor);
    //nested for loops draw all of the rooms that exist
    for (int i = 0; i < roomFilling.length; i++) {
      for (int j = 0; j < roomFilling[i].length; j++) {
        if (roomFilling[i][j]) {
          g.fillRect((int) (x + i*50), (int) (y + j*50), 50, 50);
        }
      }
    }
  }  
  
  /**
  * runGame
  * method actually used to make things happen
  */
  public void runGame() {
    for (int i = 0; i < bulletList.size(); i++) {
      //moves all the bullets in the bulletList arrayList
      bulletList.get(i).movement();
      bulletCollision(bulletList.get(i));
    }
    //testing by adding bullets
    if (add) {
      for (int i = 0; i < 1; i++) {
        bulletList.add(new TestSquareBullet(bulletList));
        bulletList.add(new TestBullet(bulletList));
      }
      add = false;
    }
  }
  
  /**
  * bulletCollision
  * method used to check if any bullets are colliding
  * @note - this uses the bounds of the bullet, that is the rectangle surrounding it
  * more accurate measurements are used in the bullet when it calls the bullet's collision method
  * @param bullet - the bullet being checked for collision
  */
  public void bulletCollision(Bullet bullet) {
    ArrayList<Rectangle> wallCollidedNew = new ArrayList<Rectangle>();
    for (int i = 0; i < bullet.getPoints().length; i++) {
      //if the bullet is outside of the map, make it disappear
      if ((bullet.getPoints()[i][0]/50 >= horizontalDimension) || 
      (bullet.getPoints()[i][1]/50 >= verticalDimension) ||
      (bullet.getPoints()[i][0]/50 < 0) || 
      (bullet.getPoints()[i][1]/50 < 0) ) {
        bullet.bulletDisappear();
        i = bullet.getPoints().length;
      } else if (roomFilling[(int) bullet.getPoints()[i][0]/50][(int) bullet.getPoints()[i][1]/50]) {
        //wallsCollided are the walls the bullet has recently collided with and are included
        //so the bullet doesn't immediately collide again after bouncing off
        //this section of code essentially removes any walls on the wallsCollided array that the bullet
        //has left the hitBox of
        if (!bullet.getWallCollided().contains
        (roomHitBox[(int) bullet.getPoints()[i][0]/50][(int) bullet.getPoints()[i][1]/50])) {
          bullet.collisionWithWall(
          roomHitBox[(int) bullet.getPoints()[i][0]/50][(int) bullet.getPoints()[i][1]/50], roomHitBox);
          i = bullet.getPoints().length;
        }
      }
    }
    //this chunk of code is just calling the bullet's collision method
    //is long because a case during testing revealed a case where the bullet was able to 
    //call the collision method despite being outside of the map so this checks for that
    //maybe related to how division works in the section removing bullets when they
    //are outside the map but idk I'm not taking any chances
    //in addition it also checks if the wall being collided has been recently collided by the 
    //bullet before to make sure no double collisions occur
    for (int i = 0; i < bullet.getPoints().length; i++) {
      for (int j = 0; j < bullet.getWallCollided().size(); j++) {
        if (((bullet.getPoints()[i][0]/50) > 0) && ((bullet.getPoints()[i][0]/50) < roomHitBox.length) &&
        ((bullet.getPoints()[i][1]/50) > 0) && ((bullet.getPoints()[i][1]/50) < roomHitBox[0].length) &&
        (bullet.getWallCollided().get(j))
        .equals(roomHitBox[(int) bullet.getPoints()[i][0]/50][(int) bullet.getPoints()[i][1]/50])) {
          wallCollidedNew.add(roomHitBox
          [(int) bullet.getPoints()[i][0]/50][(int) bullet.getPoints()[i][1]/50]);
          bullet.getWallCollided().remove(j);
        }
      }
    }
    //
    bullet.setWallCollided(wallCollidedNew);
  }
  /**
  * generateWall
  * method not used outside of this class and subclasses to make things easier when adding walls
  * @param horizontalPosition - the horizontal coordinate of the wall on the 2d array
  * @param verticalPosition - the vertical coordinate of the wall on the 2d array
  */
  protected void generateWall(int horizontalPosition, int verticalPosition) {
    roomFilling[horizontalPosition][verticalPosition] = true;
    roomHitBox[horizontalPosition][verticalPosition] = new Rectangle(horizontalPosition*50, 
    verticalPosition*50, 50,50);
  }
}