/** [Chocolate.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Area;
import java.awt.geom.AffineTransform;
import java.awt.Shape;
import java.awt.Rectangle;
import java.util.ArrayList; 
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.File;
import javax.imageio.ImageIO;

public class Chocolate extends Enemy{
  
  private BufferedImage chocolateSprite;
  private Graphics2D g2d;
  private File f;
  
/*Constructer*/
  public Chocolate(ArrayList<Enemy> enemyList){
    super(300,300,"Chocolate",100,4,new BasicRoom(0,0));
    chocolateSprite = new BufferedImage((int)getX(),(int)getY(),BufferedImage.TYPE_INT_ARGB);
    g2d = (Graphics2D)chocolateSprite.getGraphics();
    f = new File ("Chocolate_Enemy.png");
    try{
      chocolateSprite = ImageIO.read(f);
    }catch(Exception e){
      System.out.println("error");
    }
  }
  
  /*Methods*/
  public void drawEnemy(double addedX, double addedY, Graphics g){
    g.drawImage(chocolateSprite,(int)getX(),(int)getY(),null,null);
  }
  public void movement(){
    setY(getY() - getSpeed());
    if(getY()==28){
      setSpeed(-getSpeed());
    }
    if((getY()==300)&&(getSpeed()<=0)){
      setSpeed(-getSpeed());
    }
  }
}