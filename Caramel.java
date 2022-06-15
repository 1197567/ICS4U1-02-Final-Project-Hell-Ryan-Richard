/** [Caramel.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/
import java.awt.Rectangle;

public class Caramel extends Enemy{
  
  /*Constructer*/
  public Caramel(double x, double y, double velocityX, double velocityY, Room presentRoom){
    super(x,y, velocityX, velocityY, 32, 34, "Caramel",140,
    new Rectangle((int) x, (int) y, 32, 34), 
    "Caramel_Enemy.png", 30, presentRoom, 10);
  }

  public void fireBullet() {

  }
}
