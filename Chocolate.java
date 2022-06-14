/** [Chocolate.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/

import java.awt.Rectangle;
import java.util.ArrayList; 


public class Chocolate extends Enemy{
/*Constructer*/
  public Chocolate(double x, double y, double velocityX, double velocityY,
  Room presentRoom, ArrayList<Enemy> enemyList){
    super(x, y, velocityX, velocityY, 20, 30,"Chocolate",100,
    enemyList, new Rectangle((int) x, (int) y, 20, 30), "Chocolate_Enemy.png");
  }
  public void movement(){
    x += velocityX;
    y += velocityY;
  }

  public void fireBullet() {

  }
}