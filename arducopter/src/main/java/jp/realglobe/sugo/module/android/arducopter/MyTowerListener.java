package jp.realglobe.sugo.module.android.arducopter;

import android.os.Handler;
import android.util.Log;

import com.o3dr.android.client.ControlTower;
import com.o3dr.android.client.interfaces.TowerListener;

import java.util.Collection;
import java.util.List;

import jp.realglobe.sugo.actor.Emitter;

/**
 * ドローンコントローラー。
 * Created by fukuchidaisuke on 16/12/06.
 */
final class MyTowerListener implements TowerListener {

    private static final String LOG_TAG = MyTowerListener.class.getName();

    private final ControlTower tower;
    private final DroneWrapper drone;
    private final Handler handler;
    private final MyDroneListener droneListener;

    MyTowerListener(ControlTower tower, DroneWrapper drone, Handler handler, Emitter emitter) {
        this.tower = tower;
        this.drone = drone;
        this.handler = handler;
        this.droneListener = new MyDroneListener(this.drone, emitter);
    }

    @Override
    public void onTowerConnected() {
        Log.d(LOG_TAG, "Drone tower connected");
        this.tower.registerDrone(this.drone.getRaw(), this.handler);
        this.drone.getRaw().registerDroneListener(this.droneListener);
    }

    @Override
    public void onTowerDisconnected() {
        Log.d(LOG_TAG, "Drone tower disconnected");
        //this.tower.unregisterDrone(this.drone.getRaw());
        this.drone.getRaw().unregisterDroneListener(this.droneListener);
    }

    void enableEvents(Collection<Event> events) {
        this.droneListener.enableEvents(events);
    }

    void disableEvents(Collection<Event> events) {
        this.droneListener.disableEvents(events);
    }

    List<Event> getEnableEvents() {
        return this.droneListener.getEnableEvents();
    }

}
