/** [WideRoom.java]
  * ICS4U1-02
  * @Richard Yang
  * @Ryan Zareh
  * @version 1.0
  * June 2022
  */

public class WideRoom extends Room {
  
  public WideRoom(double x, double y, Player player) {
    super(x,y,6,10,player);
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
 generateWall(1, 3);
 generateWall(1, 4);
 generateWall(1, 5);
 generateWall(1, 6);
 generateWall(4, 3);
 generateWall(4, 4);
 generateWall(4, 5);
 generateWall(4, 6);
  }
}