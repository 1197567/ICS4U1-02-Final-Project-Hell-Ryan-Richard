/** [Caramel.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/
import java.awt.Graphics;

public class Caramel extends Enemy{
  
/*Constructer*/
  public Caramel(BasicRoom presentRoom){
    super(0,0,"Caramel",140,5,presentRoom);
  }
  
/*Methods*/
  public void drawEnemy(double addedX,double addedY,Graphics gameWindow){
  }
  
  public void movement(){
    setX(getX() + getSpeed());
    setY(getY() + getSpeed());
  }
  
}