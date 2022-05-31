import java.awt.Graphics;

public abstract class Bullet{
  
  private int speed;
  private int damage;
  private int size;
  
  public abstract void movement();
  
  public void colisionWithWall(){
  }
  
}