/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcode2014;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;
import battlecode.common.RobotType;
import static bcode2014.RobotPlayer.rand;

/**
 *
 * @author ubuntu
 */
public
        class PASTRPolicy
{

    public static
            void run(RobotController rc) throws GameActionException
    {
        if (rc.isActive())
        {
            double[][] cowsGrowth = rc.senseCowGrowth();
            double currMax = 0;
            MapLocation target = new MapLocation(0, 0);
            RobotInfo targetToAttack = OffensiveMethods.findTarget(rc, 10);

            if (targetToAttack == null) // no target to attack, move
            {
                int rows = rc.getMapWidth();
                int col = rc.getMapHeight();
                for (int i = 0; i < rows; ++i)
                {
                    for (int j = 0; j < col; ++j)
                    {
                        if (currMax < cowsGrowth[i][j])
                        {
                            currMax = cowsGrowth[i][j];
                            target = new MapLocation(i, j);
                        }
                    }
                }

                MapLocation myLocation = rc.getLocation();
                Direction toCows = myLocation.directionTo(target);
                int distance = myLocation.distanceSquaredTo(target);

                if (distance > 20)
                {
                    if (rc.canMove(toCows))
                    {
                        rc.move(toCows);
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
                else if (distance > 10)
                {
                    if (rc.canMove(toCows))
                    {
                        rc.sneak(toCows);
                    }
                    else
                    {
                        Direction moveDirection = GameGlobals.directions[rand.nextInt(8)];
                        if (rc.canMove(moveDirection))
                        {
                            rc.sneak(moveDirection);
                        }
                    }
                }
                else
                {
                    rc.construct(RobotType.PASTR);
                }
            }
            else // attack
            {
                rc.attackSquare(targetToAttack.location);
            }
        }
    }
}
