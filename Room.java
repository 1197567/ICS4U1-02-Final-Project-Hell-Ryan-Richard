/** [Room.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/

//import statements
import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.util.ArrayList; 
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

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
  protected int horizontalDimension;
  protected int verticalDimension;
  protected int[][] roomFilling;
  protected Rectangle[][] roomHitBox;
  protected int[][] roomDoors;
  private double x;
  private double y;
  private ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
  protected ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
  private ArrayList<Explosion> explosionList = new ArrayList<Explosion>();
  protected Player player;
  private BufferedImage candyFloor;
  private BufferedImage candyWall;
  private BufferedImage candyDoorClosed;
  private BufferedImage candyDoorOpen;
  private JLabel textFieldHealth;
  private JLabel textFieldPrimaryWeapon;
  private JLabel textFieldSecondaryWeapon;
  
  public Room(double x, double y, int horizontalDimension, int verticalDimension, Player player) {
    //temporary variable placeholders;
    Font displayFont = new Font("Comic Sans MS", Font.PLAIN, 20);
    this.horizontalDimension = horizontalDimension;
    this.verticalDimension = verticalDimension;
    this.roomFilling = new int[this.horizontalDimension][this.verticalDimension];
    this.roomHitBox = new Rectangle[this.horizontalDimension][this.verticalDimension];
    this.roomDoors = new int[this.horizontalDimension][this.verticalDimension];
    this.x = x;
    this.y = y;
    this.player = player;
    this.setLayout(null);
    textFieldHealth = new JLabel("Health");
    textFieldHealth.setFont(displayFont);
    textFieldHealth.setBounds(0,0,75,25);
    textFieldPrimaryWeapon = new JLabel("Primary Weapon: ");
    textFieldPrimaryWeapon.setFont(displayFont);
    textFieldPrimaryWeapon.setBounds(0,50,600,30);
    textFieldSecondaryWeapon = new JLabel("Secondary Weapon: ");
    textFieldSecondaryWeapon.setBounds(0,80, 300, 30);
    this.add(textFieldHealth);
    this.add(textFieldPrimaryWeapon);
    this.add(textFieldSecondaryWeapon);
    
    try{
      candyFloor = ImageIO.read(new File("Resources/Candy-Floor_Tile.png"));
    }catch(Exception e){
      System.out.println("FILEPATH OF CANDY FLOOR TILE WAS NOT FOUND");
    }
    try{
      candyWall = ImageIO.read(new File("Resources/Candy-Wall-Tile.png"));
    }catch(Exception e){
      System.out.println("FILEPATH OF CANDY WALL TILE WAS NOT FOUND");
    }
    try{
      candyDoorClosed = ImageIO.read(new File("Resources/DoorClosed.png"));
    }catch(Exception e){
      System.out.println("FILEPATH OF CANDY DOOR OPEN TILE WAS NOT FOUND");
    }
    try{
      candyDoorOpen = ImageIO.read(new File("Resources/DoorOpen.png"));
    }catch(Exception e){
      System.out.println("FILEPATH OF CANDY DOOR CLOSED TILE WAS NOT FOUND");
    }
    
    //maybe make a generateRoom too because all rooms will have walls?
    generateRoomWalls();
    
    //JPanel settings
    setFocusable(true);
    requestFocusInWindow();
    setOpaque(true);
    setPreferredSize(new Dimension(800, 600));
    setVisible(true);
    addMouseMotionListener(player.getMouseMotionListener());
    addMouseListener(player.getMouseListener());
    addKeyListener(player.getKeyListener());
    revalidate();
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
      bulletList.get(i).draw(x, y, g);
    }
    for (int i = 0; i < enemyList.size(); i++) {
      enemyList.get(i).draw(x,y,g);
    }
    player.draw(g);
    drawHealthBar(g);
    for (int i = 0; i < explosionList.size(); i++) {
      explosionList.get(i).draw(x, y, g);
    }
  }
  
  protected void drawHealthBar(Graphics g) {
    g.setColor(Color.RED);
    g.fillRect(0,25, 200, 25);
    g.setColor(Color.GREEN);
    g.fillRect(0,25, (int) (200.0*(player.getHealth()/player.getMaxHealth())), 25);
  }
  
  /**
  * drawRoom
  * used to draw the wals of the room, maybe used for more later
  * @param g - idk what this is but it's important
  */
  public void drawRoom(Graphics g) {
    //nested for loops draw all of the rooms that exist
    for (int i = 0; i < roomFilling.length; i++) {
      for (int j = 0; j < roomFilling[i].length; j++) {
        if (roomFilling[i][j] == 0) {
          g.drawImage(candyFloor, (int) (x + i*50), (int) (y + j*50), null);
        } else if (roomFilling[i][j] == 1){
          //g.drawRect((int)(i*50), (int) (j*50), 50, 50);
          g.drawImage(candyWall,(int) (x + i*50), (int) (y + j*50), null);
        } else if (roomFilling[i][j] == 2){
          g.drawImage(candyDoorClosed,(int) (x + i*50), (int) (y + j*50), null);
        } else if (roomFilling[i][j] == 3){
          g.drawImage(candyDoorOpen,(int) (x + i*50), (int) (y + j*50), null);
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
      if (bulletList.get(i).getHostileToPlayer()) {
        if ((player.getHitCountDown() <= 0) && 
        (bulletList.get(i).getHitBox().intersects(player.getHitBox()))) {
          bulletList.get(i).damageEntity(player);
        }
      } else {
        for (int j = 0; j < enemyList.size(); j++) {
          if ((enemyList.get(j).getAlive()) && (j < enemyList.size()) && (i < bulletList.size()) &&
          (bulletList.get(i).getHitBox().intersects(enemyList.get(j).getHitBox()))){
            bulletList.get(i).damageEntity(enemyList.get(j));
          }
        }
      }
      if (i < bulletList.size()) {
        bulletList.get(i).movement();
      }
      if (i < bulletList.size()) {
        bulletCollision(bulletList.get(i));
      }
      //moves all the bullets in the bulletList arrayList
    }
    player.move();
    player.shootingBullet();
    for (int i = 0; i < roomFilling.length; i++) {
      for (int j = 0; j < roomFilling[i].length; j++) {
        
        if ((roomFilling[i][j] == 1) || (roomFilling[i][j] == 2)) {
          if (roomHitBox[i][j] != null) {
            if (player.collidingWithRectangle(roomHitBox[i][j])) {
              player.wallCollision(roomHitBox[i][j]);
            }
            for (int k = 0; k < enemyList.size(); k++) {
              if (enemyList.get(k).collidingWithRectangle(roomHitBox[i][j])) {
                enemyList.get(k).wallCollision(roomHitBox[i][j]);
              }
            }
          }
        } else if (roomFilling[i][j] == 3) {
          if (roomHitBox[i][j] != null) {
            if (player.collidingWithRectangle(roomHitBox[i][j])) {
              placePlayerInNewRoom(roomDoors[i][j], i, j);
            }
          }
        }
        if (enemyList.size() <= 0) {
          if (roomFilling[i][j] == 2) {
            roomFilling[i][j] = 3;
          }
        }
      }
    }
    for (int i = 0; i < enemyList.size(); i++) {
      if ((player.collidingWithRectangle(enemyList.get(i).getHitBox())) && 
      (player.getHitCountDown() <= 0) && (enemyList.get(i).getAlive())) {
        player.takeDamage(enemyList.get(i).getMeleeDamage());
      }
      enemyList.get(i).move();
      if (i < enemyList.size()) {
        enemyList.get(i).shootingBullet();
      }
    }
    for (int i = 0; i < explosionList.size(); i++) {
      explosionList.get(i).runExplosion();
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
      } else if (roomFilling[(int) bullet.getPoints()[i][0]/50]
      [(int) bullet.getPoints()[i][1]/50] == 1) {
        //wallsCollided are the walls the bullet has recently collided with and are included
        //so the bullet doesn't immediately collide again after bouncing off
        //this section of code essentially removes any walls on the wallsCollided array that the bullet
        //has left the hitBox of
        if (!bullet.getWallCollided().contains
        (roomHitBox[(int) bullet.getPoints()[i][0]/50]
        [(int) bullet.getPoints()[i][1]/50])) {
          bullet.collisionWithWall(
          roomHitBox[(int) bullet.getPoints()[i][0]/50]
          [(int) bullet.getPoints()[i][1]/50], roomHitBox, 
          (int) bullet.getPoints()[i][0]/50,
          (int) bullet.getPoints()[i][1]/50);
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
        if (((bullet.getPoints()[i][0]/50) > 0) && 
        ((bullet.getPoints()[i][0]/50) < roomHitBox.length) &&
        ((bullet.getPoints()[i][1]/50) > 0) && 
        ((bullet.getPoints()[i][1]/50) < roomHitBox[0].length) &&
        (bullet.getWallCollided().get(j))
        .equals(roomHitBox[(int) bullet.getPoints()[i][0]/50]
        [(int) bullet.getPoints()[i][1]/50])) {
          wallCollidedNew.add(roomHitBox
          [(int)bullet.getPoints()[i][0]/50][(int) bullet.getPoints()[i][1]/50]);
          bullet.getWallCollided().remove(j);
        }
      }
    }
    //
    bullet.setWallCollided(wallCollidedNew);
  }
  
  public void placePlayerInNewRoom(int roomType, int doorXPosition, int doorYPosition) {
    Room newRoom;
    switch(roomType) {
      case 0:
      newRoom = new BasicRoom(0,0, player);
      break;  
      case 1:
      newRoom = new WideRoom(0,0, player); 
      break;
      default:
      System.out.println("COULD NOT FIND ROOMTYPE");
      newRoom = new BasicRoom(0,0,player);
    }
    player.setPresentRoom(newRoom);
    if (doorXPosition == 0) {
      newRoom.setX(x - ((newRoom.horizontalDimension - 2)*50 - 34));
      newRoom.setY(y);
      player.getHitBox().setLocation((int) (newRoom.horizontalDimension - 2)*50 + 12,
      (int) player.getHitBox().getY());
    } else if (doorXPosition == roomFilling.length - 1) {
      newRoom.setX(x + ((newRoom.horizontalDimension - 2)*50 - 34));
      newRoom.setY(y);
      player.getHitBox().setLocation((int) 50 + 12,
      (int) player.getHitBox().getY());
    } else if (doorYPosition == 0) { 
      newRoom.setX(x);
      newRoom.setY(y - (newRoom.verticalDimension - 3)*50 - 10);
      player.getHitBox().setLocation((int) player.getHitBox().getX(),
      (int) (newRoom.verticalDimension - 2)*50 + 7);
    } else if (doorYPosition == roomFilling[0].length - 1) {
      newRoom.setX(x);
      newRoom.setY(y + (newRoom.verticalDimension - 3)*50 + 10);
      System.out.println(newRoom.y);
      player.getHitBox().setLocation((int) player.getHitBox().getX(),
      (int) 50 + 7);
    } else {
      System.out.println("COULD NOT SET NEW POSITION");
    }
    BulletHellMain.setRoom(newRoom);
    newRoom.requestFocusInWindow();
  }
  
  /**
  * generateWall
  * method not used outside of this class and subclasses to make things easier when adding walls
  * @param horizontalPosition - the horizontal coordinate of the wall on the 2d array
  * @param verticalPosition - the vertical coordinate of the wall on the 2d array
  */
  protected void generateWall(int horizontalPosition, int verticalPosition) {
    roomFilling[horizontalPosition][verticalPosition] = 1;
    roomHitBox[horizontalPosition][verticalPosition] = new Rectangle(horizontalPosition*50, 
    verticalPosition*50, 50,50);
  }
  
  /**
  * generateDoor
  * method used when adding doors
  * @param horizontalPosition - the horizontal coordinate of the door on the 2d array
  * @param verticalPosition - the vertical coordinate of the door on the 2d array
  */
  protected void generateDoor(int horizontalPosition, int verticalPosition, int subsequentRoom) {
    roomFilling[horizontalPosition][verticalPosition] = 2;
    roomDoors[horizontalPosition][verticalPosition] = subsequentRoom;
    roomHitBox[horizontalPosition][verticalPosition] = new Rectangle(horizontalPosition*50, 
    verticalPosition*50, 50,50);
  }
  
  public void setPrimaryWeapon(Bullet bullet) {
    textFieldPrimaryWeapon.setText("Primary Weapon: " + bullet.getName());
  }
  
  public void setSecondaryWeapon(Bullet bullet) {
    textFieldSecondaryWeapon.setText("Secondary Weapon: " + bullet.getName());
  }
  
  public void switchWeapons() {
    String tempText = textFieldPrimaryWeapon.getText();
    textFieldPrimaryWeapon.setText(textFieldSecondaryWeapon.getText());
    textFieldSecondaryWeapon.setText(tempText);
  }
  
  public Player getPlayer() {
    return player;
  }
  
  public void setPlayer(Player playerToSet) {
    this.player = playerToSet;
  }
  
  public ArrayList<Bullet> getBulletList() {
    return bulletList;
  }
  
  public ArrayList<Enemy> getEnemyList() {
    return enemyList;
  }
  
  public ArrayList<Explosion> getExplosionList() {
    return explosionList;
  }
  
  public void addX(double additionX) {
    x += additionX;
  }
  
  public void addY(double additionY) {
    y += additionY;
  }
  
  public void setX(double x) {
    this.x = x;
  }
  
  public void setY(double y) {
    this.y = y;
  }
}
