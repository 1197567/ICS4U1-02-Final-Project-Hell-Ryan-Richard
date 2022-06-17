/** [Enemy.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public abstract class Enemy implements Entity{
  
  /*Variables*/  
  protected double x;
  protected double y;
  protected double velocityX;
  protected double velocityY;
  protected int width;
  protected int height;
  protected String name;
  protected double health;
  protected Rectangle hitBox;
  protected BufferedImage sprite;
  private int bulletTimer = 0;
  private int bulletInterval;
  private int deathTimer = 27;
  protected boolean alive = true;
  protected Room presentRoom;
  private double meleeDamage;
  protected boolean hasWallCollision = true;
  
  
  /*Constructer*/
  public Enemy(double x,double y, double velocityX,
  double velocityY,int width, int height, String name,int health, 
  Rectangle hitBox, String imagePath, int bulletInterval, Room presentRoom, 
  double meleeDamage){
    this.x=x;
    this.y=y;
    this.velocityX = velocityX;
    this.velocityY = velocityY;
    this.name=name;
    this.health=health;
    this.hitBox = hitBox;
    this.bulletInterval = bulletInterval;
    this.presentRoom = presentRoom;
    this.meleeDamage = meleeDamage;
    try{
      sprite = ImageIO.read(new File("enemyResources/" + imagePath));
    }catch(Exception e){
      System.out.println("error");
    }
  }
  
  public void move() {
    
    x += velocityX;
    y += velocityY;
    hitBox.setLocation((int) x, (int) y);
  }
  
  public void shootingBullet() {
    if (bulletTimer <= 0) {
      bulletTimer = bulletInterval;
      fireBullet();
    } else {
      bulletTimer--;
    }
  }
  
  public void wallCollision (Rectangle rectangleHitBox) {
    if (hasWallCollision) {
      boolean collidedX = false;
      boolean collidedY = false;
      x -= velocityX;
      hitBox.setLocation((int) x,(int) y);
      if (!collidingWithRectangle(rectangleHitBox)) {
        collidedX = true;
      }
      x += velocityX;
      y -= velocityY;
      hitBox.setLocation((int) x,(int) y);
      if (!collidingWithRectangle(rectangleHitBox)) {
        collidedY = true;
      }
      y += velocityY;
      if (collidedX) {
        velocityX = -velocityX;
      }
      if (collidedY) {
        velocityY = -velocityY;
      }
      hitBox.setLocation((int) x,(int) y);
    }
  }
  
  public boolean collidingWithRectangle(Rectangle rectangleHitBox) {
    if ((rectangleHitBox.contains(hitBox.getX() ,hitBox.getY())) || 
    (rectangleHitBox.contains(hitBox.getX() + hitBox.getWidth(), hitBox.getY())) || 
    (rectangleHitBox.contains(hitBox.getX(), hitBox.getY() + hitBox.getHeight())) || 
    (rectangleHitBox.contains(hitBox.getX() + hitBox.getWidth(), hitBox.getY() + hitBox.getHeight()))){
      return true;
    }
    return false;
  }
  
  /*Setters and Getters*/
  public void setX(double x){
    this.x=x;
    hitBox.setLocation((int) x, (int) y);
  }
  
  public double getX(){
    return x;
  }
  
  public void setY(double y){
    this.y=y;
    hitBox.setLocation((int) x, (int) y);
  }
  
  public double getY(){
    return y;
  }

  public void setVelocityX(double velocityX){
    this.velocityX=velocityX;
  }
  
  public double getVelocityX(){
    return velocityX;
  }

  public void setVelocityY(double velocityY){
    this.velocityY = velocityY;
  }
  
  public double getVelocityY(){
    return velocityY;
  }
  
  public void setName(String name){
    this.name=name;
  }
  
  public String getName(){
    return name;
  }  
  
  public void setHealth(double health){
    this.health=health;
  }
  
  public double getHealth(){
    return health;
  }
  
  public void setAlive(boolean alive){
    this.alive = alive;
  }
  
  public boolean getAlive(){
    return alive;
  }
  
  public Rectangle getHitBox(){
    return hitBox;
  }
  
  public void setMeleeDamage(double meleeDamage) {
    this.meleeDamage =
    meleeDamage;
  }
  public double getMeleeDamage() {
    return meleeDamage;
  }

  public void setHasWallCollision(boolean hasWallCollision) {
    this.hasWallCollision = hasWallCollision;
  }
  public boolean getWallCollision() {
    return hasWallCollision;
  }
  
  /*Methods*/
  public void draw(double addedX,double addedY,Graphics g) {
    if (alive) {
      g.drawImage(sprite,(int) (x + addedX),(int) (y + addedY),null);
    } else if (!alive) {
      if (deathTimer%5 > 3) {
        g.drawImage(sprite,(int) (x + addedX),(int) (y + addedY),null);
      }
      deathTimer--;
      if (deathTimer <= 0) {
        presentRoom.getEnemyList().remove(this);
      }
    }
  }
  
  public abstract void fireBullet();
  
  public void takeDamage(Bullet bullet){
    health -= bullet.getDamage();
    if (bullet.getDamage() > 0) {
      if (health <= 0) {
        alive = false;
        double deathAngle = Math.atan2(bullet.getVelocityY(), bullet.getVelocityX());
        velocityX = Math.cos(deathAngle);
        velocityY = Math.sin(deathAngle);
        presentRoom.getPlayer().kill();
      }
    }
  }
  
  public void takeDamage(double damage){
    health -= damage;
    if (damage > 0) {
      if (health <= 0) {
        alive = false;
        double deathAngle = Math.atan2(velocityY, velocityX);
        velocityX = Math.cos(deathAngle);
        velocityY = Math.sin(deathAngle);
        presentRoom.getPlayer().kill();
      }
    }
  }
}
