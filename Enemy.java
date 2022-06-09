import java.awt.Graphics;

public abstract class Enemy{
  
  public Enemy(int x,int y,String name,int health,int speed,Room presentRoom){
    this.x=x;
    this.y=y;
    this.name=name;
    this.health=health;
    this.speed=speed;
    this.presentRoom=presentRoom;
  }
  
  private int x;
  private int y;
  private String name;
  private int health;
  private double speed;
  private Room presentRoom = new Room();
  
  public abstract void drawEnemy(double addedX,double addedY,graphics gameWindow);
  
  public void movement(){
    while (gameLoop==true){
      x +=1;
      y +=1;
    }
  }
  
  public void fireBullet(){
  }
  
  public void death(){
  }
}