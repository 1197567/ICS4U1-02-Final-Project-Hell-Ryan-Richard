/** [Bullet.java]
  * ICS4U1-02
  * @Richard Yang
  * @Ryan Zareh
  * @version 1.0
  * June 2022
  */

import java.awt.Graphics;
import java.awt.Color;

public abstract class Bullet{
  
  private double x;
  private double y;
  private double velocityx;
  private double velocityy;
  private double accelerationx;
  private double accelerationy;
  private double speed;
  private double damage;
  private double size;
  private Color bulletColor;
  private int pierceCount;
  private int bounceCount;
  private double bounceModifier;
  private boolean willDisappear;
  private float disappearDistance;
  
  /*
   * constructor of bullet
   * @param x - x position
   * @param y - y position
   * @param velocityx - x velocity
   * @param velocityy - y velocity
   * @param accelerationx - x acceleration
   * @param accelerationy - y acceleration
   * @param speed - speed of bullet
   * @param damage - damage of bullet
   * @param size - size of bullet
   * @param bulletColor - color of bullet in a Color object
   * @param pierceCount - the amount of enemies the bullet can hit before disappearing
   * @param bounceCount - the amount of walls the bullet can bounce off of before disappearing
   * @param bounceModifier - the amount of velocity change when bouncing
   * @oaram willDisappear - boolean value indicating if the bullet will disappear by itself
   * @param disappearDistance - distance bullet travels before disappearing
   */
  public Bullet(double x, double y, double velocityx, double velocityy, double accelerationx, double accelerationy, 
                double speed, double damage, double size, Color bulletColor, int pierceCount, int bounceCount, 
                double bounceModifier, boolean willDisappear, float disappearDistance) {
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
    this.willDisappear = willDisappear;
    this.disappearDistance = disappearDistance;
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
