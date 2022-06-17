/** [CottonCandy.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/
import java.awt.Rectangle;
import java.util.LinkedList;

public class Sugar extends Enemy{

  LinkedList<Enemy> enemyQueue = new LinkedList<Enemy>();

  /* 
(double x,double y, double velocityX,
  double velocityY,double width, double height, String name,int health,BasicRoom presentRoom,
  ArrayList<Enemy> enemyList, Rectangle hitBox, String imagePath;) */
  /*Constructer*/

/*Constructer*/
  public Sugar(double x, double y, double velocityX, double velocityY, Room presentRoom){
    super(x, y, velocityX, velocityY, 100,100,"Sugar",100, 
    new Rectangle((int) x, (int) y, 100, 100),
    "Sugar.png", 200, presentRoom, 0);
  }

  public void fireBullet() {
    double aimAngle = 2*Math.PI*Math.random();
    if (enemyQueue.size() > 3)  {
      presentRoom.getEnemyList().add(enemyQueue.poll());
    }
    switch((int) (Math.random()*3)) {
        case 0:
        enemyQueue.offer(new Chocolate(x + 50, y + 50, 
        Math.cos(aimAngle)*2, Math.sin(aimAngle)*2, presentRoom));
        break;
        case 1:
        enemyQueue.offer(new Lolipop(x + 50, y + 50, 0, 0, presentRoom));
        break;
        case 2:
        enemyQueue.offer(new CottonCandy(x + 50, y + 50, 
        Math.cos(aimAngle)*2, Math.sin(aimAngle)*2, presentRoom));
        break;
        default:
        System.out.println("DID NOT PRODUCE ENEMY FROM SUGAR");
    }
  }

  @Override
  public void takeDamage(double damage){
    health -= damage;
    if (damage > 0) {
      if (health <= 0) {
        alive = false;
        int enemies = enemyQueue.size();
        for (int i = 0; i < enemies; i++) {
          presentRoom.getEnemyList().add(enemyQueue.poll());
        }
        presentRoom.getPlayer().kill();
      }
    }
  }
  @Override
  public void takeDamage(Bullet bullet){
    health -= bullet.getDamage();
    if (bullet.getDamage() > 0) {
      if (health <= 0) {
        alive = false;
        int enemies = enemyQueue.size();
        for (int i = 0; i < enemies; i++) {
          presentRoom.getEnemyList().add(enemyQueue.poll());
        }
        presentRoom.getPlayer().kill();
      }
    }
  }
  
}
