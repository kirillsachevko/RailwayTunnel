package by.training.railwaytunnel.junction;

import by.training.railwaytunnel.railwaydepot.RailwayDepot;
import by.training.railwaytunnel.tunnel.NorthTunnel;
import by.training.railwaytunnel.tunnel.SouthTunnel;
import by.training.railwaytunnel.tunnel.Tunnel;

import java.util.concurrent.locks.ReentrantLock;

public abstract class Junction {

    private static ReentrantLock northLock = new ReentrantLock();
    private static ReentrantLock southLock = new ReentrantLock();

    public static boolean askLock(ReentrantLock lock) {
        return lock.tryLock();
    }

    public static void dropLock(ReentrantLock lock) {
        lock.unlock();
    }

    public static ReentrantLock getLock(Tunnel tunnel) {
        ReentrantLock lock = null;

        if (tunnel instanceof NorthTunnel) {
            lock = northLock;
        } else if (tunnel instanceof SouthTunnel) {
            lock = southLock;
        }

        return lock;

    }

    public abstract void raidThroughJunction(RailwayDepot train) throws InterruptedException;
}
