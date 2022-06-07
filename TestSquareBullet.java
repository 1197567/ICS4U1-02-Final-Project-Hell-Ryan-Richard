/** [TestSquareBullet.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/

//import statements
//this is the same thing as testBullet but with a different shape go read that for comments
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList; 
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

public class TestSquareBullet extends Bullet{
    
    boolean testSquare = false;
    Rectangle testingSquare;
    
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
    public TestSquareBullet(ArrayList<Bullet> bulletList) {
        super(100, 100, 10, 7, 0, 
        0, 1, 20, 40, Color.RED, 0, 
        999, 1, false, 0, 
        bulletList, new Rectangle(100, 100, 20, 40), 0);
    }
    
    
    public void collisionWithWall(Rectangle wallHitBox, Rectangle[][] hitBoxArray) {
        testingSquare = wallHitBox;
        testSquare = true;
        if (hitBox.intersects(wallHitBox)) {
            if (bounceCount <= 0) {
                bulletDisappear();
                System.out.println("EXHAUSTED BOUNCES ON TESTBULLET");
            } else {
                bounceCount--;                
                double velocityBackX;
                double velocityBackY;
                int backUpCount = 0;
                AffineTransform backTranslate = new AffineTransform();
                boolean inWall;
                boolean bothChanged = true;
                boolean notBesideOtherWall = true;
                int[] wallCoordinates = new int[]{(int) wallHitBox.getX()/50, (int) wallHitBox.getY()/50};
                do {
                    wallCollided.add(wallHitBox);
                    if (Math.abs(velocityX) > Math.abs(velocityY)) {
                        velocityBackX = (-velocityX)/Math.abs(velocityX);
                        velocityBackY = (-velocityY)/Math.abs(velocityX);
                    } else {
                        velocityBackX = (-velocityX)/Math.abs(velocityY);
                        velocityBackY = (-velocityY)/Math.abs(velocityY);
                    }
                    backTranslate.setToTranslation(velocityBackX, velocityBackY);
                    while (hitBox.intersects(wallHitBox)) {
                        backUpCount++;
                        hitBox.transform(backTranslate);
                    }
                    for (int i = 0; i < points.length; i++) {
                        points[i][0] += (backUpCount*velocityBackX);
                        points[i][1] += (backUpCount*velocityBackY);
                    }
                    backUpCount = 0;
                    inWall = false;
                    for (int i = 0; i < hitBoxArray.length; i++) {
                        for (int j = 0; j < hitBoxArray[i].length; j++) {
                            if ((hitBoxArray[i][j] != null) && 
                            (hitBox.intersects(hitBoxArray[i][j]))) {
                                wallHitBox = hitBoxArray[i][j];
                                wallCoordinates[0] = i;
                                wallCoordinates[1] = j;
                                inWall = true;
                            }
                        }
                    }
                } while (inWall);
                if ((wallCoordinates[0] - 1 > 0) && 
                (hitBoxArray[wallCoordinates[0] - 1][wallCoordinates[1]] != null) &&
                (wallHitBox.getX() + wallHitBox.getWidth()/2 >= points[0][0] + sizeX/2)) {
                    velocityY = -velocityY*bounceModifier;
                    notBesideOtherWall = false;
                } else if ((wallCoordinates[0] + 1 < hitBoxArray.length) && 
                (hitBoxArray[wallCoordinates[0] + 1][wallCoordinates[1]] != null) &&
                (wallHitBox.getX() + wallHitBox.getWidth()/2 < points[0][0] + sizeX/2)) {
                    velocityY = -velocityY*bounceModifier;
                    notBesideOtherWall = false;
                } else if ((wallCoordinates[1] - 1 > 0) && 
                (hitBoxArray[wallCoordinates[0]][wallCoordinates[1] - 1] != null) &&
                (wallHitBox.getY() + wallHitBox.getHeight()/2 >= points[0][1] + sizeY/2)) {
                    velocityX = -velocityX*bounceModifier;
                    notBesideOtherWall = false;
                } else if ((wallCoordinates[1] + 1 < hitBoxArray[0].length) &&
                (hitBoxArray[wallCoordinates[0]][wallCoordinates[1] + 1] != null) &&
                (wallHitBox.getY() + wallHitBox.getHeight()/2 < points[0][1] + sizeY/2)) {
                    velocityX = -velocityX*bounceModifier;
                    notBesideOtherWall = false;
                }
                if (notBesideOtherWall) {
                    backTranslate.setToTranslation(-velocityBackX,0);
                    hitBox.transform(backTranslate);
                    if (hitBox.intersects(wallHitBox)) {
                        velocityX = -velocityX*bounceModifier;
                        bothChanged = false;
                    }
                    backTranslate.setToTranslation(velocityBackX, -velocityBackY);
                    hitBox.transform(backTranslate);
                    if (hitBox.intersects(wallHitBox)) {
                        velocityY = -velocityY*bounceModifier;
                        bothChanged = false;
                    }
                    if (bothChanged) {
                        velocityX = -velocityX*bounceModifier;
                        velocityY = -velocityY*bounceModifier;
                    }
                    backTranslate.setToTranslation(0, velocityBackY);
                    hitBox.transform(backTranslate);
                }
                bulletTransform.setToTranslation(velocityX, velocityY);
            }
        }
    }
    
    public void draw(double addedX, double addedY, Graphics g) {
        g.setColor(bulletColor);
        g.fillRect((int) points[0][0], (int) points[0][1], (int) sizeX, (int) sizeY);
        g.setColor(Color.GREEN);
        g.drawRect((int) hitBox.getBounds().getX(), (int) hitBox.getBounds().getY(), 
        (int) hitBox.getBounds().getWidth(), (int) hitBox.getBounds().getHeight());
        if (testSquare) {
            g.fillRect((int) testingSquare.getX(), (int) testingSquare.getY(), 50, 50);
        }
        testSquare = false;
    }
    
    public void collisionWithEntity() {
        bulletDisappear();
    }
    
    public void bulletDisappear() {
        bulletList.remove(this);
    }
    
}
