/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcode2014;

import battlecode.common.GameActionException;
import battlecode.common.Robot;
import battlecode.common.RobotController;
import battlecode.common.RobotInfo;
import battlecode.common.RobotType;

/**
 *
 * @author madqazz
 */
public
        class OffensiveMethods
{

    public static
            RobotInfo findTarget(RobotController rc, int Radius) throws GameActionException
    {
        Robot[] nearbyEnemies = rc.senseNearbyGameObjects(Robot.class, Radius, rc.getTeam().opponent());
        double minHealth = 300;
        RobotInfo target = null;

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
                        target = info;
                    }
                }
            }
        }
        return target;
    }
}
