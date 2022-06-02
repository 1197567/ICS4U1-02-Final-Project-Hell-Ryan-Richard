/** [BasicRoom.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/

public class BasicRoom extends Room {
    
    /*class variables
    * backgroundColor - color of the floor;
    * wallColor - color of the walls
    * horizontalDimension - horizontal dimension of room in squares
    * verticalDimension - verticalDimension of room in squares
    * roomFilling - 2d array showing a map of the room in filled squares
    * x - x position of the room on the players screen for viewing purposes
    * y - y position of the room on the players screen for viewing purposes
    */
    
    public BasicRoom(double x, double y) {
        super(x,y,10,10);
    }
    
    protected void generateRoomWalls() {
        for (int i = 0; i < verticalDimension; i++) {
            roomFilling[i][0] = true;
            roomFilling[i][horizontalDimension - 1] = true;
        }
        for (int i = 1; i < horizontalDimension - 1; i++) {
            roomFilling[0][i] = true;
            roomFilling[verticalDimension - 1][i] = true;
        }
        roomFilling[4][4] = true;
    }
}
