/** [Lolipop.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/
import java.awt.Rectangle;
import java.util.ArrayList;

public class Lolipop extends Enemy{
  
/*Constructer*/

  public Lolipop(double x, double y ,double velocityX, double velocityY,
  ArrayList<Enemy> enemyList){
    super(x,y, velocityX, velocityY, 16, 16, "Lolipop",
    170, enemyList, new Rectangle((int) x,(int) y,16,16), "lolipop-enemy.png");
  }
  
  public void movement(){
  }
  
  public void heal(){
    
  }

  public void fireBullet() {

  }
  
}
