import java.awt.Graphics;

public abstract class Bullet{
  
  private int speed;
  private int damage;
  private int size;
  
  public abstract void movement();
  
  public void colisionWithWall(){
  }
  
}import java.awt.Graphics;
import java.awt.Color;

public abstract class Bullet{
  
  private double speed;
  private double damage;
  private double size;
  private Color bulletColor;
  private double x;
  private double y;
  private double velocityx;
  private double velocityy;
  private double accelerationx;
  private double accelerationy;
  private int pierceCount;
  private int bounceCount;
  private double bounceModifier;
  
  public Bullet(double x, double y, double velocityx, double velocityy, double accelerationx, double accelerationy, 
                double speed, double damage, double size, Color bulletColor, int pierceCount, int bounceCount, 
                double bounceModifier) {
    this.x = x;
    this.y = y;
    this.velocityx = velocityx;
    this.velocityy = velocityy;
    this.accelerationx = accelerationx;
    this.accelerationy = accelerationy;
    this.speed = speed;
    this.damage = damage;
    this.size = size;
    this.bulletColor = bulletColor;
    this.pierceCount = pierceCount;
    this.bounceCount = bounceCount;
    this.bounceModifier = bounceModifier;
  }
  
  public abstract void movement();
  
  public void colisionWithWall() {
    if (bounceCount <= 0) {
      bulletDisappear();
    } else {
      bounceCount--;
      //push bullet out of wall
      //change bullet x direction and y direction depending on side of wall collided with
    }
  }
  
  public void collisionWithEntity() {
  }
  
  public void bulletDisappear() {
    
  }
  
}
