package team_mq;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.RobotType;
import java.util.*;

public class RobotPlayer
{

    static Random rand;

    public static void run(RobotController rc)
    {
        rand = new Random();

        while (true)
        {
            if (rc.getType() == RobotType.HQ)
            {
                try
                {
                    HQPolicy.run(rc);
                }
                catch (GameActionException e)
                {
                    System.out.println("HQ Exception");
                }
            }

            if (rc.getType() == RobotType.SOLDIER)
            {
                try
                {
                    CowboyPolicy.run(rc);
                }
                catch (GameActionException e)
                {
                    System.out.println("Soldier Exception");
                }
            }
            rc.yield();
        }
    }
}
