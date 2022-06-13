/** [Player.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player implements KeyListener{
  
/*Variables*/
  private double x;
  private double y;
  private double health;
  private double damage;
  private int speed;
  private Room presentRoom;
  private BufferedImage mainCharacterSprite;
  private Graphics2D g2d;
  private File f;
  
/*Constructer*/  
  public Player(int x,int y,int health,int speed,Room presentRoom){
    this.x=x;
    this.y=y;
    this.health=health;
    this.speed=speed;
    this.presentRoom=presentRoom;
    mainCharacterSprite = new BufferedImage(x,y,BufferedImage.TYPE_INT_ARGB);
    g2d = (Graphics2D)mainCharacterSprite.getGraphics();
    f = new File ("Main_Character.png");
    try{
      mainCharacterSprite = ImageIO.read(f);
    }catch(Exception e){
      System.out.println("error");
    }
  }
  
/*Setters and Getters*/  
  public void setX(double x){
    this.x=x;
  }
  
  public double getX(){
    return x;
  }
  
  public void setY(double y){
    this.y=y;
  }
  
  public double getY(){
    return y;
  }
  
  public void setHealth(double health){
    this.health=health;
  }
  
  public double getHealth(){
    return health;
  }
  
  public void setSpeed(int speed){
    this.speed=speed;
  }
  
  public int getSpeed(){
    return speed;
  }
  
  public void setPresentRoom(Room setPresentRoom){
    this.presentRoom=presentRoom;
  }
  
  public Room getPresentRoom(){
    return presentRoom;
  }

/*Methods*/
  public void keyTyped(KeyEvent e){
  }
  
  public void keyPressed(KeyEvent e){
    int keyCode = e.getKeyCode();
    
    if(keyCode==KeyEvent.VK_W){
      y -= 5;
    }
    if(keyCode==KeyEvent.VK_S){
      y += 5;
    }
    if(keyCode==KeyEvent.VK_D){
      x += 5;
    }
    if(keyCode==KeyEvent.VK_A){
      x -= 5;
    }
  }
  
  public void keyReleased(KeyEvent e){
  }
  
  public void drawPlayer(Graphics g){
    g.drawImage(mainCharacterSprite,(int)x,(int)y,null,null);
  }
  
}