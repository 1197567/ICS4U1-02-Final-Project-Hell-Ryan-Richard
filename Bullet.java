/** [Bullet.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.AffineTransform;
import java.awt.Shape;
import java.awt.Rectangle;
import java.util.ArrayList; 
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;

import java.io.File;

public abstract class Bullet{
    
    protected double[][] points;
    protected double[][] pointsOriginal;
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
    protected boolean hostileToPlayer;
    protected double disappearDistance;
    protected double appearingTime = 0;
    protected Area hitBox;
    protected ArrayList<Bullet> bulletList;
    protected AffineTransform bulletTransform = new AffineTransform();
    protected ArrayList<Rectangle> wallCollided = new ArrayList<Rectangle>();
    protected BufferedImage bulletImage;
    protected BufferedImage bulletImageOriginal;
    protected AffineTransform bulletRotation = new AffineTransform();
    boolean testSquare = false;
    Rectangle testingSquare;
    double counter = 0;
    
    
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
    * @param imagePath - the path of the image represented by an image
    * @param hostileToPlayer - boolean indicating whether the bullet can hit the player
    */
    public Bullet(double x, double y, double velocityX, double velocityY, double accelerationX, 
    double accelerationY, double damage, double sizeX, double sizeY, Color bulletColor, int pierceCount, 
    int bounceCount, double bounceModifier, boolean hostileToPlayer,
    double disappearDistance, ArrayList<Bullet> bulletList, Shape hitBoxShape, String imagePath) {
        //points are stored to make rotation easier
        this.points = new double[][]{new double[]{x - sizeX/2,y - sizeY/2}, 
        new double[]{x + sizeX/2,y - sizeY/2}, 
        new double[]{x - sizeX/2,y + sizeY/2}, 
        new double[]{x + sizeX/2,y + sizeY/2}};
        this.pointsOriginal = new double[this.points.length][];
        for(int i = 0; i < this.points.length; i++) {
            double[] coordinates = this.points[i];
            this.pointsOriginal[i] = new double[2];
            System.arraycopy(coordinates, 0, this.pointsOriginal[i], 0, 2);
        }
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
        this.hostileToPlayer = hostileToPlayer;
        this.disappearDistance = disappearDistance;
        this.hitBox = new Area(hitBoxShape);
        this.bulletList = bulletList;
        this.bulletTransform.setToTranslation(velocityX, velocityY);
        try {
            bulletImageOriginal = ImageIO.read(new File(imagePath)); 
        } catch (Exception e) {
            System.out.println("FILE OF " + imagePath + " NOT FOUND");
        }
        
        //bulletImage = bulletImageOriginal;
        bulletImage = rotateBullet(bulletImageOriginal, Math.atan2(velocityY, velocityX) + Math.PI*3/2);
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
            pointsOriginal[i][0] += velocityX;
            pointsOriginal[i][1] += velocityY;
        }
        velocityX = velocityX + accelerationX;
        velocityY = velocityY + accelerationY;
        hitBox.transform(bulletTransform);
        bulletTransform.translate(accelerationX, accelerationY);
    }
    
    /**
    * draw
    * responsible for drawing the bullet
    * @param addedX - added x-coordinate for viewing purposes
    * @param addedY - added y-coordinate for viewing purposes
    * @param g - graphics
    */
    public void draw(double addedX, double addedY, Graphics g) {
        //bulletImage = rotateBullet(bulletImage, Math.toRadians(counter += 0.01));
        //g.drawImage(bulletImage, (int) points[0][0], (int) points[0][1], null);
        g.drawImage(bulletImage, 
        (int) ((points[0][0] + points[3][0])/2 - (sizeX/2) - (bulletImage.getWidth() - sizeX)/2), 
        (int) ((points[0][1] + points[3][1])/2 - (sizeY/2) - (bulletImage.getHeight() - sizeY)/2)
        ,null);
        
        g.setColor(Color.GREEN);
        
        int[] pointsIntX = new int[4];
        int[] pointsIntY = new int[4];
        pointsIntX[0] = (int) points[0][0];
        pointsIntY[0] = (int) points[0][1];
        pointsIntX[1] = (int) points[1][0];
        pointsIntY[1] = (int) points[1][1];
        pointsIntX[2] = (int) points[3][0];
        pointsIntY[2] = (int) points[3][1];
        pointsIntX[3] = (int) points[2][0];
        pointsIntY[3] = (int) points[2][1];
        
        g.drawPolygon(pointsIntX, pointsIntY, 4);
         /* 
        g.drawRect((int) hitBox.getBounds().getX(), (int) hitBox.getBounds().getY(), 
        (int) hitBox.getBounds().getWidth(), (int) hitBox.getBounds().getHeight());
        */
        if (testSquare) { //for testing purposes
            g.fillRect((int) testingSquare.getX(), (int) testingSquare.getY(), 50, 50);
        }
        testSquare = false;
    }
    
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
    
    public BufferedImage rotateBullet(BufferedImage givenBulletImage, double angle) {
        
        double centerX = (points[0][0] + points[3][0])/2;
        double centerY = (points[0][1] + points[3][1])/2;
        BufferedImage rotatedImage = new BufferedImage(
        givenBulletImage.getWidth(), givenBulletImage.getHeight(), 
        givenBulletImage.getType());
        Graphics2D g2d = rotatedImage.createGraphics();
        g2d.rotate(angle, givenBulletImage.getWidth()/2, givenBulletImage.getHeight()/2);
        g2d.drawImage(givenBulletImage, null, 0, 0);
        g2d.dispose();
        for (int i = 0; i < points.length; i++) {
            
            double x1 = pointsOriginal[i][0] - centerX;
            double y1 = pointsOriginal[i][1] - centerY;
            
            
            double x2 = x1 * Math.cos(angle) - y1 * Math.sin(angle);
            double y2 = x1 * Math.sin(angle) + y1 * Math.cos(angle);
            
            points[i][0] = x2 + centerX;
            points[i][1] = y2 + centerY;
        }
        bulletRotation.setToRotation(angle, centerX, centerY);
        hitBox.transform(bulletRotation);
        return rotatedImage;
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
