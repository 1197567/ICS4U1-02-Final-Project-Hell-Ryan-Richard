/** [BasicRoom.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/

public class PlayerBasicBullet extends Bullet {
    
    public PlayerBasicBullet(double x, double y, velocityX, velocityY, 
    ArrayList<Bullet> bulletList) {
        super(x,y, velocityX, velocityY, 0, 0, 1, 1, 6, 6, 0, 0, 0, false, 
        bulletList, new Ellipse2D.Double(x - 3, y - 3, 6, 6), "PlayerBasicBullet.png");
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