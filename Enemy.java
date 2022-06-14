/** [Enemy.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList; 
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public abstract class Enemy{
  
  /*Variables*/  
  protected double x;
  protected double y;
  protected double velocityX;
  protected double velocityY;
  protected int width;
  protected int height;
  protected String name;
  protected  double health;
  protected ArrayList<Enemy> enemyList;
  protected Rectangle hitBox;
  protected BufferedImage sprite;
  
  /*Constructer*/
  public Enemy(double x,double y, double velocityX,
  double velocityY,int width, int height, String name,int health,
  ArrayList<Enemy> enemyList, Rectangle hitBox, String imagePath){
    this.x=x;
    this.y=y;
    this.name=name;
    this.health=health;
    this.enemyList = enemyList;
    this.hitBox = hitBox;
    try{
      sprite = ImageIO.read(new File("enemyResources/" + imagePath));
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
  
  public void setName(String name){
    this.name=name;
  }
  
  public String getName(){
    return name;
  }  
  
  public void setHealth(double health){
    this.health=health;
  }
  
  public double getHealth(){
    return health;
  }

  /*Methods*/
  public void drawEnemy(double addedX,double addedY,Graphics g) {
    g.drawImage(sprite,(int) x,(int) y,null);
  }
  
  public abstract void movement();
  
  public abstract void fireBullet();
  
  public boolean death(double health){
    boolean isDead = false;
    if(health<=0){
      isDead = true;
    }
    return isDead;
    //From here you use this as a check in the room class, and if it returns as true remove that enemy from the arraylist.
  }
}
