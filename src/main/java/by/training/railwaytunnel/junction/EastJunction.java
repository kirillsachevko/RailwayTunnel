package by.training.railwaytunnel.junction;

import by.training.railwaytunnel.railwaydepot.RailwayDepot;
import by.training.railwaytunnel.tunnel.NorthTunnel;
import by.training.railwaytunnel.tunnel.SouthTunnel;
import by.training.railwaytunnel.tunnel.Tunnel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantLock;

public class EastJunction extends Junction {

    private static final Logger log = LoggerFactory.getLogger(EastJunction.class);

    private ReentrantLock lock = new ReentrantLock();

    private Tunnel northTunnel = NorthTunnel.getInstance();
    private Tunnel southTunnel = SouthTunnel.getInstance();

    @Override
    public void raidThroughJunction(RailwayDepot train) throws InterruptedException {
        log.trace(Thread.currentThread().getName() + " invokes raidThroughJunction from EastJunction");

        lock.lock();
        log.debug("Lock is held by thread");

        while (Thread.currentThread().isAlive()) {
            log.trace("Entry loop started");

            if (northTunnel.isEastSemaphore() && Junction.askLock(Junction.getLock(northTunnel))) {
                log.debug("Semaphore lock is held by thread");

                setSemaphoreRedLight(northTunnel);
                log.debug("RedLight is set, semaphore lock is unlocked");

                allowEntryToTunnel(northTunnel, train);
                log.debug(train + " try to entry into NorthTunnel, Lock is unlocked");

                northTunnel.rideThroughTunnelEastWest(train);

                break;

            } else if (southTunnel.isEastSemaphore() && Junction.askLock(Junction.getLock(southTunnel))) {
                log.debug("Semaphore lock is held by thread");

                setSemaphoreRedLight(southTunnel);
                log.debug("RedLight is set, semaphore lock is unlocked");

                allowEntryToTunnel(southTunnel, train);
                log.debug(train + " try to entry into SouthTunnel, Lock is unlocked");

                southTunnel.rideThroughTunnelEastWest(train);

                break;
            }
        }
    }

    private void setSemaphoreRedLight(Tunnel tunnel) {
        try {
            tunnel.setWestSemaphore(false);
            log.trace("WestSemaphore set false");
        } finally {
            Junction.dropLock(Junction.getLock(tunnel));
            log.trace("SemaphoreLock is unlocked");
        }
    }

    private void allowEntryToTunnel(Tunnel tunnel, RailwayDepot train) {
        try {
            tunnel.rideIntoTunnelFromEast(train);
        } finally {
            lock.unlock();
            log.trace("JunctionLock is unlocked");
        }
    }
}
