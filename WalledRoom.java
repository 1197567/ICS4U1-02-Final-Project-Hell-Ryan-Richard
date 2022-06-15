/** [WalledRoom.java]
  * ICS4U1-02
  * @Richard Yang
  * @Ryan Zareh
  * @version 1.0
  * June 2022
  */

public class WalledRoom extends Room {
  
  public WalledRoom(double x, double y, Player player) {
    super(x,y,15,8, player);
  }
  
  protected void generateRoomWalls() {
    for (int i = 0; i < horizontalDimension; i++) {
      generateWall(i, 0);
      generateWall(i, verticalDimension - 1);
    }
    for (int i = 1; i < verticalDimension - 1; i++) {
      generateWall(0, i);
      generateWall(horizontalDimension - 1, i);
    }
 generateWall(0, 2);
 generateWall(1, 2);
 generateWall(2, 2);
 generateWall(3, 2);
 generateWall(4, 2);
 generateWall(5, 2);
 generateWall(6, 2);
 generateWall(14, 5);
 generateWall(13, 5);
 generateWall(12, 5);
 generateWall(11, 5);
 generateWall(10, 5);
 generateWall(8, 0);
 generateWall(8, 1);
 generateWall(8, 2);
 generateWall(8, 4);
 generateWall(8, 5);
 generateWall(8, 6);
  }
}