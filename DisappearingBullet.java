/** [TestBullet.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/

//import statements
import java.awt.Shape;

public abstract class DisappearingBullet extends Bullet{
    
    private double distanceTravelled = 0;
    private double disappearDistance;
    
    /*
    * constructor of bullet
    * @param x - x position
    * @param y - y position
    * @param velocityX - x velocity
    * @param velocityY - y velocity
    * @param maxVelocity - maximum velocity
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
    * @param imagePath - the path of the image represented by an image
    * @param hostileToPlayer - boolean indicating whether the bullet can hit the player
    * @param bulletInterval - amount of time before this bullet can be shot again
    * @param accuracy - modifier affecting the accuracy of the aiming
    * @param name - the name of the gun that shoots this bullet
    */
    public DisappearingBullet(double x, double y, double velocityX, double velocityY, double maxVelocity,
    double accelerationX, double accelerationY, double damage, double sizeX, double sizeY, 
    int pierceCount, int bounceCount, double bounceModifier, boolean hostileToPlayer, 
    Room presentRoom, Shape hitBoxShape, String imagePath, int bulletInterval,
    double accuracy, String name, double disappearDistance) {
        super(x, y, velocityX, velocityY, maxVelocity,
        accelerationX, accelerationY, damage, sizeX, sizeY, 
        pierceCount, bounceCount, bounceModifier, hostileToPlayer, 
        presentRoom, hitBoxShape, imagePath, bulletInterval,
        accuracy, name);
        this.disappearDistance = disappearDistance;
    }

    /**
    * movement
    * This method controls the movement by adding velocity to position and 
    * adding acceleration to velocity
    * it also does the same for the hit box
    */
    @Override
    public void movement() {
        for (int i = 0; i < 4; i++) { //
            points[i][0] += velocityX;
            points[i][1] += velocityY;
            pointsOriginal[i][0] += velocityX;
            pointsOriginal[i][1] += velocityY;
        }
        distanceTravelled += (Math.sqrt(velocityX*velocityX + velocityY*velocityY));
        if (distanceTravelled >= disappearDistance) {
            bulletDisappear();  
        }
        velocityX = velocityX + accelerationX;
        velocityY = velocityY + accelerationY;
        hitBox.transform(bulletTransform);
        bulletTransform.translate(accelerationX, accelerationY);
    }

    /**
     * @return double return the distanceTravelled
     */
    public double getDistanceTravelled() {
        return distanceTravelled;
    }

    /**
     * @param distanceTravelled the distanceTravelled to set
     */
    public void setDistanceTravelled(double distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
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
