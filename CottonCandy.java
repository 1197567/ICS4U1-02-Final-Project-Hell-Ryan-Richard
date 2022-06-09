/** [CottonCandy.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/
import java.awt.Graphics;

public class CottonCandy extends Enemy{
  
/*Constructer*/
  public CottonCandy(BasicRoom presentRoom){
    super(0,0,"Cotton Candy",80,15,presentRoom);
  }
  
/*Methods*/
  public void drawEnemy(double addedX,double addedY,Graphics gameWindow){
  }
  
  public void movement(){
    setX(getX() + getSpeed());
    if(getX()==800){
      setX(getX() - getSpeed());
    }
    if((getX()-getSpeed()) == -15){
      setY(getY() + getSpeed());
    }
    if(getY()==600){
      setY(getY() - getSpeed());
    }
  }
  
}