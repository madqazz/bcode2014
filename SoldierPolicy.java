/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcode2014;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.Robot;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;
import battlecode.common.RobotType;
import static bcode2014.RobotPlayer.rand;

/**
 *
 * @author ubuntu
 */
public class SoldierPolicy
{

    public static void run(RobotController rc) throws GameActionException
    {
        if (rc.isActive())
        {
            Robot[] allEnemies = rc.senseNearbyGameObjects(Robot.class, 35, rc.getTeam().opponent());
            Direction toEnemyHQ = rc.getLocation().directionTo(rc.senseEnemyHQLocation());
            RobotInfo targetToAttack = OffensiveMethods.findTarget(rc, 10);

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
                        Direction moveDirection = GameGlobals.directions[rand.nextInt(8)];
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
                        Direction moveDirection = GameGlobals.directions[rand.nextInt(8)];
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
