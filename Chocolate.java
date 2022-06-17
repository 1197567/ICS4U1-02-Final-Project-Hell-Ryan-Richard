/** [Chocolate.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/

import java.awt.Rectangle;

public class Chocolate extends Enemy{
/*Constructer*/
  public Chocolate(double x, double y, double velocityX, double velocityY, Room presentRoom){
    super(x, y, velocityX, velocityY, 20, 30,"Chocolate",30,
    new Rectangle((int) x, (int) y, 20, 30), 
    "Chocolate_Enemy.png",100, presentRoom, 5);
  }

  public void fireBullet() {
    double aimAngle = -Math.atan2(presentRoom.getPlayer().getHitBox().getCenterX() - x, 
    presentRoom.getPlayer().getHitBox().getCenterY() - y) + Math.PI/2;
    presentRoom.getBulletList().add(new ChocolateBullet(x + 10, y + 15,
    Math.cos(aimAngle)*1.5, Math.sin(aimAngle)*1.5, presentRoom));
  }

  @Override
  public void move() {
    x += velocityX;
    y += velocityY;
    hitBox.setLocation((int) x, (int) y);
    if (!hasWallCollision) {
      //System.out.println("MOVING");
    }
  }
}