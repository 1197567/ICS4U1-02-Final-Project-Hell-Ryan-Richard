import java.awt.Graphics;

public abstract class Enemy{
  
  private int x;
  private int y;
  private String name;
  private int health;
  private double speed;
  
  public abstract void movement();
  
  public void death(){
  }
}