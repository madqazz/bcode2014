/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team_mq;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.Robot;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;

/**
 *
 * @author ubuntu
 */
public class HQPolicy
{

    /**
     *
     * @param rc
     * @throws GameActionException
     */
    public static void run(RobotController rc) throws GameActionException
    {

        Direction[] directions =
        {
            Direction.NORTH, Direction.NORTH_EAST, Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH, Direction.SOUTH_WEST, Direction.WEST, Direction.NORTH_WEST
        };

        //Check if a robot is spawnable and spawn one if it is
        Robot[] nearbyEnemies = rc.senseNearbyGameObjects(Robot.class, 15, rc.getTeam().opponent());
        if (nearbyEnemies.length > 0)
        {
            RobotInfo robotInfo = rc.senseRobotInfo(nearbyEnemies[0]);
            rc.attackSquare(robotInfo.location);
            System.out.println("HQ Attack");
        }
        else if (rc.isActive() && rc.senseRobotCount() < 25)
        {
            Direction toEnemy = rc.getLocation().directionTo(rc.senseEnemyHQLocation());
            if (rc.senseObjectAtLocation(rc.getLocation().add(toEnemy)) == null)
            {
                rc.spawn(toEnemy);
            }
        }

    }
}
