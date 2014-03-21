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
import static team_mq.RobotPlayer.rand;

/**
 *
 * @author ubuntu
 */
public class CowboyPolicy
{

    /**
     *
     * @param rc
     * @throws GameActionException
     */
    public static void run(RobotController rc) throws GameActionException
    {
        Robot self = rc.getRobot();
        if (self.getID() % 3 == 0)
        {
            CowPASTRPolicy.run(rc);
        }
        else
        {
            CowSoldierPolicy.run(rc);
        }
    }
}
