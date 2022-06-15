/** [DottedRoom.java]
  * ICS4U1-02
  * @Richard Yang
  * @Ryan Zareh
  * @version 1.0
  * June 2022
  */

public class DottedRoom extends Room {
  
  public DottedRoom(double x, double y, Player player) {
    super(x,y,9,9,player);
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
 generateWall(4, 8);
 generateWall(2, 3);
 generateWall(4, 6);
 generateWall(8, 7);
 generateWall(3, 7);
 generateWall(7, 6);
 generateWall(6, 4);
  }
}