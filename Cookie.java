/** [Lolipop.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/
import java.awt.Rectangle;
import java.util.Arrays;

public class Cookie extends Enemy{
    
    private Chocolate[] chocolateList = new Chocolate[40];
    private Lolipop[] lolipopList = new Lolipop[20];
    private double aimAngle;
    int counter = 0;
    
    
    
    /*Constructer*/ 
    
    public Cookie(double x, double y ,double velocityX, double velocityY, Room presentRoom){
        super(x,y, velocityX, velocityY, 100, 100, "Cookie",
        1, new Rectangle((int) x,(int) y,100,100), 
        "Cookie.png", 1500, presentRoom, 25);
        
        
    }
    
    public void fireBullet() {
        switch ((int) (Math.random()*4)) {
            case 0:
            presentRoom.getEnemyList().add(new Sugar(9*50, 9*50, 0, 0, presentRoom));
            break;
            case 1:
            presentRoom.getEnemyList().add(new Sugar(9*50, 41*50, 0, 0, presentRoom));
            break;
            case 2:
            presentRoom.getEnemyList().add(new Sugar(41*50, 9*50, 0, 0, presentRoom));
            break;
            case 3:
            presentRoom.getEnemyList().add(new Sugar(41*50, 41*50, 0, 0, presentRoom));
            break;
            default: 
            System.out.println("COOKIE COULD NOT GENERATE SUGAR");
        }
    }
    @Override
    public void move() {    
        aimAngle = Math.atan2(presentRoom.getPlayer().getHitBox().getY() - y, 
        presentRoom.getPlayer().getHitBox().getX() - x);
        
        velocityX = Math.cos(aimAngle)*2;
        velocityY = Math.sin(aimAngle)*2;
        x += velocityX;
        y += velocityY;
        hitBox.setLocation((int) x, (int) y);
        if (alive) {
            for (int i = 0; i < presentRoom.getEnemyList().size(); i++) {
                if ((findEnemyNull(chocolateList) != chocolateList.length) && 
                (presentRoom.getEnemyList().get(i).getName().equals("Chocolate")) &&
                (!Arrays.asList(chocolateList).contains((presentRoom.getEnemyList().get(i)))) &&
                (presentRoom.getEnemyList().get(i).getHealth() > 0)) {
                    addEnemy(chocolateList, (presentRoom.getEnemyList().get(i)));
                    presentRoom.getEnemyList().get(i).setHasWallCollision(false);
                    presentRoom.getEnemyList().get(i).setVelocityX(0);
                    presentRoom.getEnemyList().get(i).setVelocityY(0);
                } else if ((findEnemyNull(lolipopList) != lolipopList.length) && 
                (presentRoom.getEnemyList().get(i).getName().equals("Lolipop")) &&
                (!Arrays.asList(lolipopList).contains((presentRoom.getEnemyList().get(i))))&&
                (presentRoom.getEnemyList().get(i).getHealth() > 0)) {
                    addEnemy(lolipopList, (presentRoom.getEnemyList().get(i)));
                    presentRoom.getEnemyList().get(i).setHasWallCollision(false);
                    presentRoom.getEnemyList().get(i).setHealth(10);
                    presentRoom.getEnemyList().get(i).setVelocityX(0);
                    presentRoom.getEnemyList().get(i).setVelocityY(0);
                } 
            }
            
            for (int i = 0; i < chocolateList.length; i++) {
                if (chocolateList[i] != null) {
                    chocolateList[i].setVelocityX((((x + 50) + Math.cos(Math.PI*((i*1.0 + 1)/chocolateList.length) + 
                    aimAngle - Math.PI/2)*200 - 10) - chocolateList[i].getX())/10);
                    
                    chocolateList[i].setVelocityY((((y + 50) + Math.sin(Math.PI*((i*1.0 + 1)/chocolateList.length) + 
                    aimAngle - Math.PI/2)*200 - 15) - chocolateList[i].getY())/10);
                    if (chocolateList[i].getHealth() <= 0) {
                        chocolateList[i].takeDamage(1);
                        chocolateList[i] = null;
                        removeNull(chocolateList);
                    }
                }
            }
            for (int i = 0; i < lolipopList.length; i++) {
                if (lolipopList[i] != null) {
                    lolipopList[i].setVelocityX((((x + 50) + Math.cos(Math.PI*((i*1.0 + 1)/lolipopList.length) + 
                    aimAngle - Math.PI/2)*75 - 10) - lolipopList[i].getX())/10);
                    
                    lolipopList[i].setVelocityY((((y + 50) + Math.sin(Math.PI*((i*1.0 + 1)/lolipopList.length) + 
                    aimAngle - Math.PI/2)*75 - 15) - lolipopList[i].getY())/10);
                    if (lolipopList[i].getHealth() <= 0) {
                        lolipopList[i].takeDamage(1);
                        lolipopList[i] = null;
                        removeNull(lolipopList);
                    }
                }
            }
            sort(chocolateList, 0, findEnemyNull(chocolateList)- 1);
            sort(lolipopList, 0, findEnemyNull(lolipopList) - 1);
        }
    }
    
    private int partition(Enemy[] arr, int low, int high) {
        Enemy pivot = arr[high];
        int i = (low-1); // index of smaller element
        for (int j=low; j<high; j++)
        {
            // If current element is smaller than or
            // equal to pivot
            if (arr[j].getHealth() <= pivot.getHealth())
            {
                i++;
                
                // swap arr[i] and arr[j]
                Enemy temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        
        // swap arr[i+1] and arr[high] (or pivot)
        Enemy temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;
        
        return i+1;
    }
    
    
    /* The main function that implements QuickSort()
    arr[] --> Array tao be sorted,
    low  --> Starting index,
    high  --> Ending index */
    private void sort(Enemy[] arr, int low, int high)
    {
        if (low < high)
        {
            /* pi is partitioning index, arr[pi] is
            now at right place */
            int pi = partition(arr, low, high);
            
            // Recursively sort elements before
            // partition and after partition
            sort(arr, low, pi-1);
            sort(arr, pi+1, high);
        }
    }
    
    public void removeNull(Enemy[] enemyList) {
        for (int i = 0; i < enemyList.length; i++) {
            if (enemyList[i] == null) {
                for (int j = i + 1; j < enemyList.length; j++) {
                    enemyList[j - 1] = enemyList[j];
                    enemyList[j] = null;
                }
            }
        }
    }
    
    public void addEnemy(Enemy[] enemyList, Enemy enemy) {
        for (int i = 0; i < enemyList.length; i++) {
            if (enemyList[i] == null) {
                enemyList[i] = enemy;
                i = enemyList.length;
            }
        }
    }
    
    public int findEnemyNull(Enemy[] enemyList) {
        for (int i = 0; i < enemyList.length; i++) {
            if (enemyList[i] == null) {
                return i;
            }
        }
        return enemyList.length;
    }
    
    @Override
    public void takeDamage(double damage){
        health -= damage;
        if (damage > 0) {
            if (health <= 0) {
                alive = false;
                for (int i = 0; i < chocolateList.length; i++) {
                    if (chocolateList[i] != null) {
                        chocolateList[i].takeDamage(1000);
                    }
                }
                for (int i = 0; i < lolipopList.length; i++) {
                    if (lolipopList[i] != null) {
                        lolipopList[i].takeDamage(1000);
                    }
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
                for (int i = 0; i < chocolateList.length; i++) {
                    if (chocolateList[i] != null) {
                        chocolateList[i].takeDamage(1000);
                    }
                }
                for (int i = 0; i < lolipopList.length; i++) {
                    if (lolipopList[i] != null) {
                        lolipopList[i].takeDamage(1000);
                    }
                }
                presentRoom.getPlayer().kill();
            }
        }
    }
}