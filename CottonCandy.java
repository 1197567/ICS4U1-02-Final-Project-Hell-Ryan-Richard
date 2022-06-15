/** [CottonCandy.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/
import java.awt.Rectangle;

public class CottonCandy extends Enemy{

  
  /* 
(double x,double y, double velocityX,
  double velocityY,double width, double height, String name,int health,BasicRoom presentRoom,
  ArrayList<Enemy> enemyList, Rectangle hitBox, String imagePath;) */
  /*Constructer*/

/*Constructer*/
  public CottonCandy(double x, double y, double velocityX, double velocityY, Room presentRoom){
    super(x, y, velocityX, velocityY, 26,24,"Cotton Candy",80, 
    new Rectangle((int) x, (int) y, 26, 24),
    "Cotton_Candy_enemy(2).png", 30, presentRoom, 2);
  }

  public void fireBullet() {

  }
  
}
