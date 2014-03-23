/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcode2014;

import battlecode.common.GameActionException;
import battlecode.common.Robot;
import battlecode.common.RobotController;


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
            PASTRPolicy.run(rc);
        }
        else
        {
            SoldierPolicy.run(rc);
        }
    }
}
