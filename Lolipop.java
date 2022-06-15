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
    super(x,y, velocityX, velocityY, 16, 16, "Lolipop",
    170, new Rectangle((int) x,(int) y,16,16), 
    "lolipop-enemy.png", 30, presentRoom, 10);
  }

  public void heal(){
    
  }

  public void fireBullet() {

  }
  
}
