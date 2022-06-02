/** [Bullet.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/

import java.awt.Color;
import java.awt.Graphics;

public class TestBullet extends Bullet{
    
    
    /*
    * constructor of bullet
    * @param x - x position
    * @param y - y position
    * @param velocityx - x velocity
    * @param velocityy - y velocity
    * @param accelerationx - x acceleration
    * @param accelerationy - y acceleration
    * @param speed - speed of bullet
    * @param damage - damage of bullet
    * @param size - size of bullet
    * @param bulletColor - color of bullet in a Color object
    * @param pierceCount - the amount of enemies the bullet can hit before disappearing
    * @param bounceCount - the amount of walls the bullet can bounce off of before disappearing
    * @param bounceModifier - the amount of velocity change when bouncing
    * @oaram willDisappear - boolean value indicating if the bullet will disappear by itself
    * @param disappearDistance - distance bullet travels before disappearing
    */
    public TestBullet() {
        super(100, 100, Math.random()*10 - 20, Math.random()*10 - 20, 0, 
        0, 1, 20, Color.RED, 0, 
        999, 1, false, 0);
    }
    
    public void movement() {
        x = x + velocityX;
        y = y + velocityY;
        velocityX = velocityX + accelerationX;
        velocityY = velocityY + accelerationY;
    }
    
    public void collisionWithWall(int x1, int y1) {
        if (bounceCount <= 0) {
            bulletDisappear();
            System.out.println("EXHAUSTED BOUNCES");
        } else {
            bounceCount--;
            
            if (x < 50) {
                x = 50;
                velocityX = -velocityX*bounceModifier;
            } else if (x > 450) {
                x = 450;
                velocityX = -velocityX*bounceModifier;
            }
            
            if (y < 50) {
                y = 50;
                velocityY = -velocityY*bounceModifier;
            } else if (y > 450) {
                y = 450;
                velocityY = -velocityY*bounceModifier;
            }
            
        }
    }
    
    public void collisionWithSquare() {
            /* 
            circleDistance.x = abs(x+size/2 - rect.x);
            circleDistance.y = abs(y+size/2 - rect.y);
            
            if (circleDistance.x > (rect.width/2 + circle.r)) { return false; }
            if (circleDistance.y > (rect.height/2 + circle.r)) { return false; }
            
            if (circleDistance.x <= (rect.width/2)) { return true; } 
            if (circleDistance.y <= (rect.height/2)) { return true; }
            
            cornerDistance_sq = (circleDistance.x - rect.width/2)^2 +
            (circleDistance.y - rect.height/2)^2;
            
            return (cornerDistance_sq <= (circle.r^2));
            */
    }
    
    
    public void draw(double addedX, double addedY, Graphics g) {
        g.setColor(bulletColor);
        g.fillOval((int) (x - size/2), (int) (y - size/2), (int) size, (int) size);
    }
    
    public void collisionWithEntity() {
        bulletDisappear();
    }

    public void bulletDisappear() {
        
    }
    
}
