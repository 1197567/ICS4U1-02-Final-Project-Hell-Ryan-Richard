/** [Caramel.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/
import java.awt.Rectangle;
import java.util.ArrayList; 

public class Caramel extends Enemy{
  
  /*Constructer*/
  public Caramel(double x, double y, double velocityX, double velocityY, ArrayList<Enemy> enemyList){
    super(x,y, velocityX, velocityY, 32, 34, "Caramel",140, enemyList,
    new Rectangle((int) x, (int) y, 32, 34), "Caramel_Enemy.png");
  }

  
  public void movement(){
    x += velocityX;
    y += velocityY;
  }

  public void fireBullet() {

  }
}
