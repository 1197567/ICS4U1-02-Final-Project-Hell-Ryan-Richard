/** [Bullet.java]
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

public abstract class Bullet{
    
    protected double[][] points; 
    protected double velocityX;
    protected double velocityY;
    protected double accelerationX;
    protected double accelerationY;
    protected double damage;
    protected double sizeX;
    protected double sizeY;
    protected Color bulletColor;
    protected int pierceCount;
    protected int bounceCount;
    protected double bounceModifier;
    protected boolean willDisappear;
    protected double disappearDistance;
    protected double appearingTime = 0;
    protected Area hitBox;
    protected ArrayList<Bullet> bulletList;
    protected AffineTransform bulletTransform = new AffineTransform();
    protected float rotationRadian;
    protected ArrayList<Rectangle> wallCollided = new ArrayList<Rectangle>();
    
    
    /*
    * constructor of bullet
    * @param x - x position
    * @param y - y position
    * @param velocityX - x velocity
    * @param velocityY - y velocity
    * @param accelerationX - x acceleration
    * @param accelerationY - y acceleration
    * @param damage - damage of bullet
    * @param size - size of bullet
    * @param bulletColor - color of bullet in a Color object
    * @param pierceCount - the amount of enemies the bullet can hit before disappearing
    * @param bounceCount - the amount of walls the bullet can bounce off of before disappearing
    * @param bounceModifier - the amount of velocity change when bouncing
    * @oaram willDisappear - boolean value indicating if the bullet will disappear by itself
    * @param disappearDistance - distance bullet travels before disappearing
    * @param bulletList - the bulletList this bullet is in
    * @param hitBoxShape - the shape of the hit box, related to collision
    * @param rotationRadian - the amount the bullet is rotated in radians from (0,1) clockwise
    */
    public Bullet(double x, double y, double velocityX, double velocityY, double accelerationX, 
    double accelerationY, double damage, double sizeX, double sizeY, Color bulletColor, int pierceCount, 
    int bounceCount, double bounceModifier, boolean willDisappear, double disappearDistance, 
    ArrayList<Bullet> bulletList, Shape hitBoxShape, float rotationRadian) {
        //points are stored to make rotation easier
        this.points = new double[][]{new double[]{x,y}, new double[]{x + sizeX,y}, 
        new double[]{x,y + sizeY}, new double[]{x + sizeX,y + sizeY}};
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.accelerationX = accelerationX;
        this.accelerationY = accelerationY;
        this.damage = damage;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.bulletColor = bulletColor;
        this.pierceCount = pierceCount;
        this.bounceCount = bounceCount;
        this.bounceModifier = bounceModifier;
        this.willDisappear = willDisappear;
        this.disappearDistance = disappearDistance;
        this.hitBox = new Area(hitBoxShape);
        this.bulletList = bulletList;
        this.bulletTransform.setToTranslation(velocityX, velocityY);
        this.rotationRadian = rotationRadian;
    }
    
    /**
    * movement
    * This method controls the movement by adding velocity to position and 
    * adding acceleration to velocity
    * it also does the same for the hit box
    */
    public void movement() {
        for (int i = 0; i < 4; i++) { //
            points[i][0] += velocityX;
            points[i][1] += velocityY;
        }
        velocityX = velocityX + accelerationX;
        velocityY = velocityY + accelerationY;
        hitBox.transform(bulletTransform);
        bulletTransform.translate(accelerationX, accelerationY);
    }
    
    public abstract void draw(double addedX, double addedY, Graphics gameWindow);
    
    public abstract void collisionWithWall(Rectangle wallHitBox, Rectangle[][] hitBoxArray);
    
    public abstract void collisionWithEntity();
    
    public abstract void bulletDisappear();
    
    /**
    * @return double return the points
    */
    public double[][] getPoints() {
        return points;
    }
    
    /**
    * @param position which point to set
    * @param x x coordinate of point
    * @param y y coordinate of point
    */
    public void setPoint(int position, int x, int y) {
        points[position][0] = x;
        points[position][1] = y;
    }
    
    /**
    * translates the bullet to a new position
    * @param x x coordinate of new position
    * @param y y coordinate of new position
    */
    public void translateBullet(int translateX, int translateY) {
        for (int i = 0; i < points.length; i++) {
            points[i][0] += translateX;
            points[i][1] += translateY;
        }
        this.bulletTransform.setToTranslation(translateX, translateY);
        this.hitBox.transform(this.bulletTransform);
        this.bulletTransform.setToTranslation(velocityX, velocityY);
    }
    
    /**
    * @return double return the velocityX
    */
    public double getvelocityX() {
        return velocityX;
    }
    
    /**
    * @param velocityX the velocityX to set
    */
    public void setvelocityX(double velocityX) {
        this.velocityX = velocityX;
    }
    
    /**
    * @return double return the velocityY
    */
    public double getvelocityY() {
        return velocityY;
    }
    
    /**
    * @param velocityY the velocityY to set
    */
    public void setvelocityY(double velocityY) {
        this.velocityY = velocityY;
    }
    
    /**
    * @return double return the accelerationX
    */
    public double getaccelerationX() {
        return accelerationX;
    }
    
    /**
    * @param accelerationX the accelerationX to set
    */
    public void setaccelerationX(double accelerationX) {
        this.accelerationX = accelerationX;
    }
    
    /**
    * @return double return the accelerationY
    */
    public double getaccelerationY() {
        return accelerationY;
    }
    
    /**
    * @param accelerationY the accelerationY to set
    */
    public void setaccelerationY(double accelerationY) {
        this.accelerationY = accelerationY;
    }
    
    /**
    * @return double return the damage
    */
    public double getDamage() {
        return damage;
    }
    
    /**
    * @param damage the damage to set
    */
    public void setDamage(double damage) {
        this.damage = damage;
    }
    
    /**
    * @return double return the sizeX
    */
    public double getSizeX() {
        return sizeX;
    }
    
    /**
    * @param sizeX the size to set
    */
    public void setSizeX(double sizeX) {
        this.sizeX = sizeX;
    }
    
    /**
    * @return double return the sizeY
    */
    public double getSizeY() {
        return sizeY;
    }
    
    /**
    * @param sizeY the size to set
    */
    public void setSizeY(double sizeY) {
        this.sizeY = sizeY;
    }
    
    /**
    * @return Color return the bulletColor
    */
    public Color getBulletColor() {
        return bulletColor;
    }
    
    /**
    * @param bulletColor the bulletColor to set
    */
    public void setBulletColor(Color bulletColor) {
        this.bulletColor = bulletColor;
    }
    
    /**
    * @return int return the pierceCount
    */
    public int getPierceCount() {
        return pierceCount;
    }
    
    /**
    * @param pierceCount the pierceCount to set
    */
    public void setPierceCount(int pierceCount) {
        this.pierceCount = pierceCount;
    }
    
    /**
    * @return int return the bounceCount
    */
    public int getBounceCount() {
        return bounceCount;
    }
    
    /**
    * @param bounceCount the bounceCount to set
    */
    public void setBounceCount(int bounceCount) {
        this.bounceCount = bounceCount;
    }
    
    /**
    * @return double return the bounceModifier
    */
    public double getBounceModifier() {
        return bounceModifier;
    }
    
    /**
    * @param bounceModifier the bounceModifier to set
    */
    public void setBounceModifier(double bounceModifier) {
        this.bounceModifier = bounceModifier;
    }
    
    /**
    * @return boolean return the willDisappear
    */
    public boolean isWillDisappear() {
        return willDisappear;
    }
    
    /**
    * @param willDisappear the willDisappear to set
    */
    public void setWillDisappear(boolean willDisappear) {
        this.willDisappear = willDisappear;
    }
    
    /**
    * @return double return the disappearDistance
    */
    public double getDisappearDistance() {
        return disappearDistance;
    }
    
    /**
    * @param disappearDistance the disappearDistance to set
    */
    public void setDisappearDistance(double disappearDistance) {
        this.disappearDistance = disappearDistance;
    }
    
    /**
    * @return double return the hitBox
    */
    public Area getHitBox() {
        return hitBox;
    }
    
    /**
    * @param hitBox the hitBox to set
    */
    public void setHitBox(Area hitBox) {
        this.hitBox = hitBox;
    }
    
    /**
    * @return double return the getWallCollided
    */
    public ArrayList<Rectangle> getWallCollided() {
        return wallCollided;
    }
    
    /**
    * @param wallCollided the getWallCollided to set
    */
    public void setWallCollided(ArrayList<Rectangle> wallCollided) {
        this.wallCollided = wallCollided;
    }
}
