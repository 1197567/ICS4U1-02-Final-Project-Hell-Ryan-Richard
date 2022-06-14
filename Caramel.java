/** [Caramel.java]
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

public class Caramel extends Enemy{
  
  private BufferedImage caramelSprite;
  private Graphics2D g2d;
  private File f;
  
  /*Constructer*/
  public Caramel(ArrayList<Enemy> enemyList){
    super(20,20,"Caramel",140,5,new BasicRoom(0,0));
    caramelSprite = new BufferedImage((int)getX(),(int)getY(),BufferedImage.TYPE_INT_ARGB);
    g2d = (Graphics2D)caramelSprite.getGraphics();
    f = new File ("Caramel_Enemy.png");
    try{
      caramelSprite = ImageIO.read(f);
    }catch(Exception e){
      System.out.println("error");
    }
  }
  
/*Methods*/
  public void drawEnemy(double addedX, double addedY, Graphics g){
    g.drawImage(caramelSprite,(int)getX(),(int)getY(),Color.RED,null);
  }
  
  public void movement(){
    setX(getX() + getSpeed());
    setY(getY() + getSpeed());
    if((getX()==400)||(getY()==300)){
      setSpeed(-getSpeed());
    }
    if((getX()==5)&&(getY()==5)&&(getSpeed()<=-5)){
      setSpeed(-getSpeed());
    }
  }
}
