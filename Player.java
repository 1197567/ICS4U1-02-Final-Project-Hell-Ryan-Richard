/** [Player.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Player{

    private double x;
    private double y;
    private double facingAngle = 0;
    private Rectangle hitBox;
    private double health;
    

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
        hitBox = new Rectangle((int) x,(int) y,20,40);
    }

    public void draw (Graphics g) {

    }

    public void shootBullet() {

    }

    public void takeDamage() {

    }


}
