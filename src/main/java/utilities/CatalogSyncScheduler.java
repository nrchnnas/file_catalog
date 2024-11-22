package utilities;

import java.util.Timer;
import java.util.TimerTask;

public class CatalogSyncScheduler {
    public static void startSyncScheduler() {
        Timer timer = new Timer(true); // Run as a daemon thread
        TimerTask syncTask = new TimerTask() {
            @Override
            public void run() {
                FileCatalogSync.syncCatalogWithDisk();
            }
        };

        // Schedule to run every 5 minutes (300,000 milliseconds)
        timer.scheduleAtFixedRate(syncTask, 0, 300000);
    }
}

