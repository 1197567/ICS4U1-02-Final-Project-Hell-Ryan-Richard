/** [Enemy.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Area;
import java.awt.geom.AffineTransform;
import java.awt.Shape;
import java.awt.Rectangle;
import java.util.ArrayList; 


public abstract class Enemy{

/*Variables*/  
  private double x;
  private double y;
  private String name;
  private double health;
  private double speed;
  private BasicRoom presentRoom = new BasicRoom(0,0);
  
/*Constructer*/
  public Enemy(int x,int y,String name,int health,int speed,BasicRoom presentRoom){
    this.x=x;
    this.y=y;
    this.name=name;
    this.health=health;
    this.speed=speed;
    this.presentRoom=presentRoom;
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
  
  public void setSpeed(double speed){
    this.speed=speed;
  }
  
  public double getSpeed(){
    return speed;
  }
  
  public void setPresentRoom(BasicRoom setPresentRoom){
    this.presentRoom=presentRoom;
  }
  
  public BasicRoom getPresentRoom(){
    return presentRoom;
  }
  
/*Methods*/
  public abstract void drawEnemy(double addedX,double addedY,Graphics gameWindow);
  
  public abstract void movement();
  
  public void fireBullet(){
  }
  
  public boolean death(double health){
    boolean isDead = false;
    if(health<=0){
      isDead = true;
    }
    return isDead;
    //From here you use this as a check in the room class, and if it returns as true remove that enemy from the arraylist.
  }
}
