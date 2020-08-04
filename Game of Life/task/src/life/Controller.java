package life;

import java.lang.Thread;

public class Controller {

    private int iteration;
    private long waitTime = 275;
    private final GameOfLife view;
    private final Universe universe;
    private SimState state;
    private Thread simThread;

    public Controller(GameOfLife view) {
        this.view = view;
        this.universe = new Universe(view);
        state = SimState.NEW_UNIVERSE;
        iteration = 0;
        universe.initiateNewUniverse();
    }

    void setWaitTime(int sliderValue) {
        this.waitTime = 500 - 9 * sliderValue / 2;
    }

    void playToggle() {
        view.enableResetButton(false);
        view.enableStopButton(true);
        if (state == SimState.RUNNING) {
            state = SimState.PAUSED;
        } else {
            state = SimState.RUNNING;
            continueGeneration();
        }
        //view.changePlayButton(state);
    }

    void reset() {
        state = SimState.NEW_UNIVERSE;
        try {
            if (simThread != null) simThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        iteration = 0;
        view.enableStopButton(false);
        view.enablePlayToggleButton(true);
        universe.initiateNewUniverse();
    }

    void stop() {
        state = SimState.STOPPED;
        try {
            if  (simThread != null) simThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        view.enablePlayToggleButton(false);
        view.enableStopButton(false);
        view.enableResetButton(true);
    }

    private void continueGeneration() {
        simThread = new Thread(this::run);
        simThread.start();
    }

    boolean isRunning() {
        return state == SimState.RUNNING;
    }

    private void run() {
        int totalGenerations = 499;
        while (iteration < totalGenerations) {
            try {
                //noinspection BusyWait
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (state == SimState.RUNNING) {
                iteration++;
                universe.generateNewGeneration(iteration);
            } else {
                break;
            }
        }
        if (iteration >= totalGenerations) {
            state = SimState.STOPPED;
            view.enablePlayToggleButton(false);
            view.enableStopButton(false);
            view.enableResetButton(true);
        }
    }
}

enum SimState {
    NEW_UNIVERSE, RUNNING, PAUSED, STOPPED
}