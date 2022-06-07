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
        super(x,y,11,10);
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
        generateWall(4, 4);
        generateWall(5, 5);
        generateWall(7, 3);
    }
}
