/** [Explosion.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/

import java.awt.Graphics;
import java.awt.Color;

public class Explosion{
    
    private double x;
    private double y;
    private double radius;
    private double damage;
    private int smokingCountDown = 0;
    private int smokingPeriod;
    private double explosionCountDown = 0;
    private boolean hostileToPlayer;
    private Room presentRoom;
    private boolean exploded = false;
    
    public Explosion(double x, double y,
    double radius, double damage, int smokingPeriod, boolean hostileToPlayer, 
    Room presentRoom) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.damage = damage;
        this.smokingPeriod = smokingPeriod;
        this.hostileToPlayer = hostileToPlayer;
        this.presentRoom = presentRoom;
    }

    public void damageEntity() {
        if (hostileToPlayer) {
            if (Math.sqrt(Math.pow(presentRoom.getPlayer().getHitBox().getCenterX() - x, 2)+ 
            Math.pow(presentRoom.getPlayer().getHitBox().getCenterY() - y, 2))
            < radius) {
                presentRoom.getPlayer().takeDamage(damage);
            }
        } else {
            for (int i = 0; i < presentRoom.getEnemyList().size(); i++) {
                if (Math.sqrt(Math.pow(presentRoom.getEnemyList().get(i).getHitBox().getCenterX() - x, 2)+ 
                Math.pow(presentRoom.getEnemyList().get(i).getHitBox().getCenterY() - y, 2))
                < radius) {
                    presentRoom.getEnemyList().get(i).takeDamage(damage);
                }
            }
        }
    }

    public void draw(double addedX, double addedY, Graphics g) {
        g.setColor(new Color(255 - (int) (155*(1.0*smokingCountDown/smokingPeriod)),
        (int) (100*(1.0*smokingCountDown/smokingPeriod)), 
        (int) (100*(1.0*smokingCountDown/smokingPeriod))));
        g.fillOval((int) (x - (radius/Math.sqrt(2))*(explosionCountDown/3.0) + addedX), 
        (int) (y - (radius/Math.sqrt(2))*(explosionCountDown/3.0) + addedY), (int) (2*radius*(explosionCountDown/3.0)),
        (int) (2*radius*(explosionCountDown/3.0)));
    }

    public void runExplosion() {
        if (exploded) {
            smokingCountDown++;
            if (smokingCountDown >= smokingPeriod) {
                presentRoom.getExplosionList().remove(this);
            }

        } else {
            explosionCountDown += 1;
            if (explosionCountDown >= 3) {
                exploded = true;
                damageEntity();
            }
        }
    }
}
