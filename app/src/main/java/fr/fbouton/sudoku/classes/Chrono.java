package fr.fbouton.sudoku.classes;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Chronometer;

/**
 * simple extend of Chronometer to expose the boolean isRunning to know if the timer is stop or not.
 */
public class Chrono extends Chronometer {

        private boolean isRunning = false;

        public Chrono(Context context) {
            super(context);
        }

        public Chrono(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public Chrono(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        public void start() {
            super.start();
            isRunning = true;
        }

        @Override
        public void stop() {
            super.stop();
            isRunning = false;
        }

        public boolean isRunning() {
            return isRunning;
        }
}
