/** [TestBullet.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/

//import statements
import java.util.ArrayList; 
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.AffineTransform;

public class TestBullet extends Bullet{
    
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
    * @param pierceCount - the amount of enemies the bullet can hit before disappearing
    * @param bounceCount - the amount of walls the bullet can bounce off of before disappearing
    * @param bounceModifier - the amount of velocity change when bouncing
    * @oaram willDisappear - boolean value indicating if the bullet will disappear by itself
    * @param disappearDistance - distance bullet travels before disappearing
    * @param bulletList - the bulletList this bullet is in
    * @param hitBoxShape - the shape of the hit box, related to collision
    * @param rotationRadian - the amount the bullet is rotated in radians from (0,1) clockwise
    */
    public TestBullet(ArrayList<Bullet> bulletList) {
        super(150, 100, 0, 0, 0, 
        0, 1, 20, 20, 0, 
        999, 1, false, 
        bulletList, new Ellipse2D.Double(140, 90, 20, 20), "TestBulletImage.png");
    }

    /*
    * collisionWithWall
    * method responsible for detecting collision between this bullet and a wall 
    * @param wallHitBox - initial rectangle object from wall used for collisions
    * @param hitBoxArray - the array of hitBoxes from rectangles from the room
    */
    public void collisionWithWall(Rectangle wallHitBox, Rectangle[][] hitBoxArray) {
        //the first two lines are for testing
        testingSquare = wallHitBox;
        testSquare = true;
        if (hitBox.intersects(wallHitBox)) { //tests if the two hitboxes really intercept
            if (bounceCount <= 0) { //deletes the bullet if it has no bounces
                bulletDisappear();
                //print statement for testing purposes
                System.out.println("EXHAUSTED BOUNCES ON TESTBULLET");
            } else {
                bounceCount--;         
                //to "bounce" the bullet the method does these steps
                //1. go backwards in small increments until outside of the wall hitbox
                //2. check to see if it's still inside a box after that and repeat step 1
                //3. if the wall it was last in has walls next to it, bounce off perpendicularly
                //to them as you can't bounce off a wall in a direction which already has walls in it
                //4. if the wall does not have walls beside it, increment once in each direction to 
                //determine which axis is closest to the wall and use that to determine the direction
                //the bullet will bounce off and change the velocity accordingly
                double velocityBackX; //velocityBack is the incremental velocity for going backwards
                double velocityBackY;
                int backUpCount = 0;
                AffineTransform backTranslate = new AffineTransform();
                boolean inWall;
                boolean bothChanged = true;
                boolean notBesideOtherWall = true;
                int[] wallCoordinates = new int[]{(int) wallHitBox.getX()/50, (int) wallHitBox.getY()/50};
                do {
                    //wallCollided ensures that the bullet doesn't 
                    //immediately collide again after bouncing
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
                //the following large chunk of code is to determine if 
                //the wall has one beside it
                if (((wallCoordinates[0] - 1 > 0) && 
                (hitBoxArray[wallCoordinates[0] - 1][wallCoordinates[1]] != null) &&
                (wallHitBox.getX() + wallHitBox.getWidth()/2 >= points[0][0] + sizeX/2)) ||
                ((wallCoordinates[0] + 1 < hitBoxArray.length) && 
                (hitBoxArray[wallCoordinates[0] + 1][wallCoordinates[1]] != null) &&
                (wallHitBox.getX() + wallHitBox.getWidth()/2 < points[0][0] + sizeX/2))) {
                    velocityY = -velocityY*bounceModifier;
                    notBesideOtherWall = false;
                } else if (((wallCoordinates[1] - 1 > 0) && 
                (hitBoxArray[wallCoordinates[0]][wallCoordinates[1] - 1] != null) &&
                (wallHitBox.getY() + wallHitBox.getHeight()/2 >= points[0][1] + sizeY/2)) ||
                ((wallCoordinates[1] + 1 < hitBoxArray[0].length) &&
                (hitBoxArray[wallCoordinates[0]][wallCoordinates[1] + 1] != null) &&
                (wallHitBox.getY() + wallHitBox.getHeight()/2 < points[0][1] + sizeY/2))) {
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
                    //niche case where the bullet bounces off a corner and cannot intersect with
                    //the wall by moving in only one direction
                    if (bothChanged) {
                        velocityX = -velocityX*bounceModifier;
                        velocityY = -velocityY*bounceModifier;
                    }
                    backTranslate.setToTranslation(0, velocityBackY);
                    hitBox.transform(backTranslate);
                }
                //changing the velocity of the hitbox
                bulletTransform.setToTranslation(velocityX, velocityY);
            }
        }
    }
    
    /**
    * collisionWithEntity
    * what happens when the bullet collides with an entity
    */
    public void collisionWithEntity() {
        bulletDisappear();
    }
    
    /**
    * bulletDisappear
    * responsible for what happens when the bullet disappears or "dies"
    */
    public void bulletDisappear() {
        bulletList.remove(this);
    }
    
}
