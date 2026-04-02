package h03;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.World;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * A class that controls the {@linkplain Robot robots} and their actions.
 */
public class ControlCenter {

    /**
     * Creates a new line of {@linkplain ScanRobot ScanRobots}.
     *
     * @return An array containing the newly initialised robots
     */
    @StudentImplementationRequired("H3.1.1")
    public ScanRobot[] initScanRobots() {
        // TODO: H3.1.1
        ScanRobot[] scanRobotArray = new ScanRobot[World.getWidth()-1];
        for (int i = 0; i < scanRobotArray.length; i++)
            scanRobotArray[i]=new ScanRobot(i,0,Direction.UP,0);

        return scanRobotArray;
        //return org.tudalgo.algoutils.student.Student.crash("H3.1.1 - Remove if implemented");
    }

    /**
     * Creates a new line of {@linkplain CleanRobot CleanRobots}.
     *
     * @return An array containing the newly initialised robots
     */
    @StudentImplementationRequired("H3.1.2")
    public CleanRobot[] initCleaningRobots() {
        // TODO: H3.1.2
        CleanRobot[] cleanRobotArray = new CleanRobot[World.getHeight()-1];
        for (int i = 0; i < cleanRobotArray.length; i++) {
            cleanRobotArray[i] = new CleanRobot(World.getWidth()-1,World.getHeight()-1-i,Direction.LEFT,0);
        }
        return cleanRobotArray;
        //return org.tudalgo.algoutils.student.Student.crash("H3.1.2 - Remove if implemented");
    }

    /**
     * Places coins in the world according to the provided array.
     *
     * @param coins An array detailing how many coins to place in what position
     */
    @StudentImplementationRequired("H3.2")
    public void placeCoinsInWorld(int[][] coins) {
        // TODO: H3.2
        for (int y = 1; y < coins.length; y++) {
            for (int x = 1; x < coins[y].length; x++) {
                World.putCoins(x,y,coins[y][x]);
            }
        }

        //org.tudalgo.algoutils.student.Student.crash("H3.2 - Remove if implemented");
    }

    /**
     * Inverts the given array by swapping the first and last entry, continuing with the second and second last entry
     * and so on until the entire array has been inverted.
     *
     * @param robots The array to invert
     */
    @StudentImplementationRequired("H3.3.1")
    public void reverseRobots(Robot[] robots) {
        // TODO: H3.3.1
        int leftIndex = 0;
        int rightIndex = robots.length-1;
        Robot tempRobot = robots[0];
        while (leftIndex<rightIndex){
            robots[leftIndex]=robots[rightIndex];
            robots[rightIndex]=tempRobot;
            tempRobot=robots[leftIndex+1];
            leftIndex++;
            rightIndex--;
        }

        //org.tudalgo.algoutils.student.Student.crash("H3.3.1 - Remove if implemented");
    }


    private void turn180(Robot robot){
        robot.turnLeft();
        robot.turnLeft();
    }
    /**
     * Rotates the {@linkplain Robot robots} in the given array in ascending order.
     *
     * @param robots The array of {@linkplain Robot robots} to rotate
     */
    @StudentImplementationRequired("H3.3.2")
    public void rotateRobots(Robot[] robots) {
        // TODO: H3.3.2
        for(Robot robot: robots)
            turn180(robot);
        //org.tudalgo.algoutils.student.Student.crash("H3.3.2 - Remove if implemented");
    }

    /**
     * Moves the robots to the end of the world, in ascending order and one at a time.
     *
     * @param robots The robots to move
     */
    @StudentImplementationRequired("H3.3.3")
    public void moveRobotsToEnd(Robot[] robots) {
        // TODO: H3.3.3
        for(Robot robot: robots)
            while (robot.isFrontClear())
                robot.move();
        //org.tudalgo.algoutils.student.Student.crash("H3.3.3 - Remove if implemented");
    }

    /**
     * Restores the robots to their original position by reversing/rotating them, moving them to the end of the world,
     * and then reversing/rotating them again. This is done via the methods {@link #reverseRobots(Robot[])},
     * {@link #rotateRobots(Robot[])}, and {@link #moveRobotsToEnd(Robot[])}.
     *
     * @param robots The array to perform the aforementioned actions on
     */
    @StudentImplementationRequired("H3.3.4")
    public void restoreRobots(Robot[] robots) {
        // TODO: H3.3.4
        reverseRobots(robots);
        rotateRobots(robots);
        moveRobotsToEnd(robots);
        reverseRobots(robots);
        rotateRobots(robots);
        //org.tudalgo.algoutils.student.Student.crash("H3.3.4 - Remove if implemented");
    }


    /**
     * Scans the world using the provided {@linkplain ScanRobot ScanRobots} and returns an array containing the scan results.
     *
     * @param scanRobots The robots to scan the world with
     * @return An array detailing which world fields contain at least one coin
     */
    @StudentImplementationRequired("H3.4.1")
    public boolean[][] scanWorld(ScanRobot[] scanRobots) {
        // TODO: H3.4.1
        boolean[][] coinPositions = new boolean[World.getHeight()][World.getWidth()];
        for(boolean[] y: coinPositions)
            for(boolean x: y)
                x=false;
        for (int y = 0; y < World.getHeight(); y++) {
            for(ScanRobot scanRobot: scanRobots){
                if (scanRobot.isOnACoin())
                    coinPositions[scanRobot.getY()][scanRobot.getX()] = true;
                if (scanRobot.isFrontClear())
                    scanRobot.move();
            }
        }
        return coinPositions;
        //return org.tudalgo.algoutils.student.Student.crash("H3.4.1 - Remove if implemented");
    }

    /**
     * Scans the world with {@linkplain ScanRobot ScanRobots}, and restores them to their original position.
     *
     * @param scanRobots The robots to move and scan with
     * @return An array detailing which world fields contain at least one coin
     */
    @StudentImplementationRequired("H3.4.2")
    public boolean[][] moveScanRobots(ScanRobot[] scanRobots) {
        // TODO: H3.4.2
        boolean[][] output = scanWorld(scanRobots);
        restoreRobots(scanRobots);
        return output;
        //return org.tudalgo.algoutils.student.Student.crash("H3.4.2 - Remove if implemented");
    }

    /**
     * Performs one iteration of collecting coins, using the provided arrays to clean and determine where to clean.
     *
     * @param coinPositions An array with all the coin positions to be collected
     * @param cleanRobots   An array containing the {@linkplain CleanRobot CleanRobots} to collect the coins with.
     */
    @StudentImplementationRequired("H3.4.3")
    public void cleanWorld(CleanRobot[] cleanRobots, boolean[][] coinPositions) {
        // TODO: H3.4.3

        //org.tudalgo.algoutils.student.Student.crash("H3.4.3 - Remove if implemented");
    }

    /**
     * Cleans the world using the provided {@linkplain CleanRobot CleanRobots} and restores them to their original
     * position.
     *
     * @param cleanRobots  The robots to move and clean with
     * @param coinsInWorld An array detailing which world fields contain at least one coin
     */
    @StudentImplementationRequired("H3.4.4")
    public void moveCleanRobots(CleanRobot[] cleanRobots, boolean[][] coinsInWorld) {
        // TODO: H3.4.4
        org.tudalgo.algoutils.student.Student.crash("H3.4.4 - Remove if implemented");
    }


    /**
     * Returns whether there are no coins left in the world.
     *
     * @param coins The array to search for coins
     * @return Whether the provided array contains at least one entry that is not false
     */
    @StudentImplementationRequired("H3.5.1")
    public boolean allCoinsGathered(boolean[][] coins) {
        // TODO: H3.5.1
        return org.tudalgo.algoutils.student.Student.crash("H3.5.1 - Remove if implemented");
    }


    /**
     * Collects all the coins in the world using all the previously implemented helper methods.
     * <p>
     * Each cleaning iteration starts with the {@linkplain ScanRobot ScanRobots} scanning the world and
     * returning an array with the positions of all coins. If there are still coins left in the world,
     * the {@linkplain CleanRobot CleanRobots} will move to the positions of the coins and collect them.
     * Otherwise, the program will terminate and print a message.
     */
    @StudentImplementationRequired("H3.5.2")
    public void scanAndCleanWorld() {
        // TODO: H3.5.2
        org.tudalgo.algoutils.student.Student.crash("H3.5.2 - Remove if implemented");
    }
}
