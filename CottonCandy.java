/** [CottonCandy.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/
import java.awt.Rectangle;
import java.util.ArrayList; 

public class CottonCandy extends Enemy{

  
  /* 
(double x,double y, double velocityX,
  double velocityY,double width, double height, String name,int health,BasicRoom presentRoom,
  ArrayList<Enemy> enemyList, Rectangle hitBox, String imagePath;) */
  /*Constructer*/

/*Constructer*/
  public CottonCandy(double x, double y, double velocityX, double velocityY,
  ArrayList<Enemy> enemyList){
    super(x, y, velocityX, velocityY, 26,24,"Cotton Candy",80, enemyList, 
    new Rectangle((int) x, (int) y, 26, 24), "Cotton_Candy_enemy(2).png");
  }
  
  public void movement(){
    x += velocityX;
    y += velocityY;
  }

  public void fireBullet() {

  }
  
}
