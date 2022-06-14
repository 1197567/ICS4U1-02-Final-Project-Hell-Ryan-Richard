/** [CottonCandy.java]
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

public class CottonCandy extends Enemy{
  
  private BufferedImage cottonCandySprite;
  private Graphics2D g2d;
  private File f;
  
/*Constructer*/
  public CottonCandy(ArrayList<Enemy> enemyList){
    super(20,20,"Cotton Candy",80,10,new BasicRoom(0,0));
    cottonCandySprite = new BufferedImage((int)getX(),(int)getY(),BufferedImage.TYPE_INT_ARGB);
    g2d = (Graphics2D)cottonCandySprite.getGraphics();
    f = new File ("Cotton_Candy_enemy.png");
    try{
      cottonCandySprite = ImageIO.read(f);
    }catch(Exception e){
      System.out.println("error");
    }
  }
  
/*Methods*/
  public void drawEnemy(double addedX, double addedY, Graphics g) {
    g.drawImage(cottonCandySprite,(int)getX(),(int)getY(),null,null);
  }
  
  public void movement(){
    setX(getX() + getSpeed());
    if(getX()==300){
      setSpeed(-getSpeed());
    }
    if((getX()==10)&&(getSpeed()<=0)){
      setSpeed(-getSpeed());
    }
    if(getY()==600){
      setSpeed(-getSpeed());
    }
  }
  
}
