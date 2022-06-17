/** [CottonCandy.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/
import java.awt.Rectangle;

public class CottonCandy extends Enemy{

  public double aimAngle = 0;
  
  /* 
(double x,double y, double velocityX,
  double velocityY,double width, double height, String name,int health,BasicRoom presentRoom,
  ArrayList<Enemy> enemyList, Rectangle hitBox, String imagePath;) */
  /*Constructer*/

/*Constructer*/
  public CottonCandy(double x, double y, double velocityX, double velocityY, Room presentRoom){
    super(x, y, velocityX, velocityY, 45,42,"Cotton Candy",5, 
    new Rectangle((int) x, (int) y, 45, 42),
    "Cotton_Candy_Enemy.png", 10, presentRoom, 2);
  }

  public void fireBullet() {
    presentRoom.getBulletList().add(new CottonCandyBullet(x + 22.5, y + 21,
    Math.cos(Math.toRadians(aimAngle))*5, Math.sin((Math.toRadians(aimAngle)))*5, presentRoom));
    aimAngle += 20;
  }
}
