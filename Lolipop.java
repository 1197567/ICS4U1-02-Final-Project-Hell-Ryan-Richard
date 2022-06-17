/** [Lolipop.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/
import java.awt.Rectangle;

public class Lolipop extends Enemy{
  
  /*Constructer*/
  
  public Lolipop(double x, double y ,double velocityX, double velocityY, Room presentRoom){
    super(x,y, velocityX, velocityY, 40, 40, "Lolipop",
    2, new Rectangle((int) x,(int) y,40,40), 
    "lolipop-enemy.png", 75, presentRoom, 10);
  }
  
  public void fireBullet() {
    double aimAngle = -Math.atan2(presentRoom.getPlayer().getHitBox().getCenterX() - x, 
    presentRoom.getPlayer().getHitBox().getCenterY() - y) + Math.PI/2;
    presentRoom.getBulletList().add(new LolipopBullet(x + 20, y + 20,
    Math.cos(aimAngle)*4, Math.sin(aimAngle)*4, presentRoom));
  }
  @Override
  public void move() {
    
    if (hasWallCollision) {
      double moveAngle = Math.atan2(presentRoom.getPlayer().getHitBox().getY() - y, 
      presentRoom.getPlayer().getHitBox().getX() - x);
      velocityX = Math.cos(moveAngle)*2;
      velocityY = Math.sin(moveAngle)*2;
    }
    x += velocityX;
    y += velocityY;
    hitBox.setLocation((int) x, (int) y);
    
  }
}
