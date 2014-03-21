/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team_mq;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.Robot;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;
import battlecode.common.RobotType;
import static team_mq.RobotPlayer.rand;

/**
 *
 * @author ubuntu
 */
public class CowSoldierPolicy
{

    public static void run(RobotController rc) throws GameActionException
    {
        Direction[] directions =
        {
            Direction.NORTH, Direction.NORTH_EAST, Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH, Direction.SOUTH_WEST, Direction.WEST, Direction.NORTH_WEST
        };

        if (rc.isActive())
        {
            Robot[] nearbyEnemies = rc.senseNearbyGameObjects(Robot.class, 10, rc.getTeam().opponent());
            Robot[] allEnemies = rc.senseNearbyGameObjects(Robot.class, 35, rc.getTeam().opponent());
            Direction toEnemyHQ = rc.getLocation().directionTo(rc.senseEnemyHQLocation());
            double minHealth = 300;
            RobotInfo targetToAttack = null;

            if (nearbyEnemies.length > 0)
            {
                for (Robot robot : nearbyEnemies)
                {
                    RobotInfo info = rc.senseRobotInfo(robot);
                    if (info.type != RobotType.HQ)
                    {
                        if (minHealth > info.health)
                        {
                            minHealth = info.health;
                            targetToAttack = info;
                        }
                    }
                }
            }

            if (targetToAttack == null) // no target to attack, move
            {
                int minDist = 300;
                RobotInfo targetToGo = null;
                MapLocation myLocation = rc.getLocation();

                for (Robot robot : allEnemies)
                {
                    RobotInfo info = rc.senseRobotInfo(robot);
                    if (info.type != RobotType.HQ)
                    {
                        if (minDist > myLocation.distanceSquaredTo(info.location))
                        {
                            minDist = myLocation.distanceSquaredTo(info.location);
                            targetToGo = info;
                        }
                    }
                }

                if (targetToGo == null)
                {
                    if (rc.canMove(toEnemyHQ))
                    {
                        rc.move(toEnemyHQ);
                    }
                    else
                    {
                        Direction moveDirection = directions[rand.nextInt(8)];
                        if (rc.canMove(moveDirection))
                        {
                            rc.move(moveDirection);
                        }
                    }
                }
                else
                {
                    Direction toGo = rc.getLocation().directionTo(targetToGo.location);
                    if (rc.canMove(toGo))
                    {
                        rc.move(toGo);
                    }
                    else
                    {
                        Direction moveDirection = directions[rand.nextInt(8)];
                        if (rc.canMove(moveDirection))
                        {
                            rc.move(moveDirection);
                        }
                    }
                }
            }
            else // attack
            {
                rc.attackSquare(targetToAttack.location);
            }
        }
    }
}
