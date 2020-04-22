package by.training.railwaytunnel.tunnel;

import by.training.railwaytunnel.railwaydepot.RailwayDepot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class NorthTunnel implements Tunnel {

    private static final NorthTunnel instance = new NorthTunnel(true, true);
    private static final Logger log = LoggerFactory.getLogger(NorthTunnel.class);

    private final BlockingQueue<RailwayDepot> NORTH_TUNNEL = new LinkedBlockingQueue<>(2);

    private final int CAPACITY = 2;
    private final long TIME_TO_RIDE_THROUGH = 3000;

    private Timer switchSemaphore = new Timer(true);

    private boolean westSemaphore;
    private boolean eastSemaphore;
    private int eastCounter = 0;
    private int westCounter = 0;

    private NorthTunnel(boolean westSemaphore, boolean eastSemaphore) {
        this.westSemaphore = westSemaphore;
        this.eastSemaphore = eastSemaphore;
    }

    public static NorthTunnel getInstance() {
        return instance;
    }

    @Override
    public void rideThroughTunnelEastWest(RailwayDepot train) {
        log.debug(train + " invokes rideThroughTunnelEastWest");

        rideThroughTunnel();
        eastCounter++;

        switchSemaphore.schedule(new TimerTask() {
            @Override
            public void run() {
                westSemaphore = NORTH_TUNNEL.isEmpty();
            }
        }, 200);

        eastSemaphore = NORTH_TUNNEL.size() < CAPACITY;

        log.trace("Semaphore lights are switched");
    }

    @Override
    public void rideThroughTunnelWestEast(RailwayDepot train) {
        log.debug(train + " invokes rideThroughTunnelWestEast");

        rideThroughTunnel();
        westCounter++;

        switchSemaphore.schedule(new TimerTask() {
            @Override
            public void run() {
                eastSemaphore = NORTH_TUNNEL.isEmpty();
            }
        }, 200);

        westSemaphore = NORTH_TUNNEL.size() < CAPACITY;

        log.trace("Semaphores lights are switched");
    }

    @Override
    public void rideIntoTunnelFromWest(RailwayDepot train) {
        log.debug(train + " invokes rideIntoTunnelFromWest");

        rideIntoTunnel(train);
        westSemaphore = NORTH_TUNNEL.size() < CAPACITY;
    }

    @Override
    public void rideIntoTunnelFromEast(RailwayDepot train) {
        log.debug(train + " invokes rideIntoTunnelFromEast");

        rideIntoTunnel(train);
        eastSemaphore = NORTH_TUNNEL.size() < CAPACITY;
    }

    private void rideIntoTunnel(RailwayDepot train) {
        try {
            NORTH_TUNNEL.put(train);
            log.debug(train + " entries into NorthTunnel");

        } catch (InterruptedException e) {
            log.error("rideIntoTunnel error", e);
            Thread.currentThread().interrupt();
        }
    }

    private void rideThroughTunnel() {
        try {
            NORTH_TUNNEL.poll(TIME_TO_RIDE_THROUGH, TimeUnit.MILLISECONDS);
            log.debug("Train raid away from NorthTunnel");
        } catch (InterruptedException e) {
            log.error("rideThroughTunnel error", e);
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public boolean isWestSemaphore() {
        return westSemaphore;
    }

    @Override
    public boolean isEastSemaphore() {
        return eastSemaphore;
    }

    @Override
    public void setWestSemaphore(boolean westSemaphore) {
        this.westSemaphore = westSemaphore;
    }

    @Override
    public void setEastSemaphore(boolean eastSemaphore) {
        this.eastSemaphore = eastSemaphore;
    }

    @Override
    public BlockingQueue<RailwayDepot> getTunnel() {
        return NORTH_TUNNEL;
    }

    @Override
    public int getEastCounter() {
        return eastCounter;
    }

    @Override
    public int getWestCounter() {
        return westCounter;
    }
}