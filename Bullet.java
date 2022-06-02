/** [Bullet.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.RectangularShape;

public abstract class Bullet{
    
    protected double x;
    protected double y;
    protected double velocityX;
    protected double velocityY;
    protected double accelerationX;
    protected double accelerationY;
    protected double damage;
    protected double size;
    protected Color bulletColor;
    protected int pierceCount;
    protected int bounceCount;
    protected double bounceModifier;
    protected boolean willDisappear;
    protected double disappearDistance;
    protected double appearingTime = 0;
    
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
    */
    public Bullet(double x, double y, double velocityX, double velocityY, double accelerationX, double accelerationY, 
    double damage, double size, Color bulletColor, int pierceCount, int bounceCount, 
    double bounceModifier, boolean willDisappear, double disappearDistance) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.accelerationX = accelerationX;
        this.accelerationY = accelerationY;
        this.damage = damage;
        this.size = size;
        this.bulletColor = bulletColor;
        this.pierceCount = pierceCount;
        this.bounceCount = bounceCount;
        this.bounceModifier = bounceModifier;
        this.willDisappear = willDisappear;
        this.disappearDistance = disappearDistance;
    }
    
    public abstract void movement();
    
    public void colisionWithWall() {
        if (bounceCount <= 0) {
            bulletDisappear();
        } else {
            bounceCount--;
            //push bullet out of wall
            //change bullet x direction and y direction depending on side of wall collided with
        }
    }
    
    public abstract void draw(double addedX, double addedY, Graphics gameWindow);

    public abstract void collisionWithWall(int x1, int y1);
    
    public abstract void collisionWithEntity();

    public abstract void bulletDisappear();

    /**
    * @return double return the x
    */
    public double getX() {
        return x;
    }
    
    /**
    * @param x the x to set
    */
    public void setX(double x) {
        this.x = x;
    }
    
    /**
    * @return double return the y
    */
    public double getY() {
        return y;
    }
    
    /**
    * @param y the y to set
    */
    public void setY(double y) {
        this.y = y;
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
    * @return double return the size
    */
    public double getSize() {
        return size;
    }
    
    /**
    * @param size the size to set
    */
    public void setSize(double size) {
        this.size = size;
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
    
}
