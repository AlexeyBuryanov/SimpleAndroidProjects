/**
 * Created by Alexey on 29.11.2017.
 */

package com.example.alexey.timeswitch;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


public class OnSwipeTouchListener implements View.OnTouchListener
{
    private final GestureDetector gestureDetector;


    OnSwipeTouchListener (Context context) {
        gestureDetector = new GestureDetector(context, new GestureListener());
    } // OnSwipeTouchListener


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    } // onTouch



    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;


        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        } // onDown


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        } // if
                        result = true;
                    } // if
                } // if
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    } // if
                    result = true;
                } // if
            } catch (Exception ex) {
                ex.printStackTrace();
            } // try-catch
            return result;
        } // onFling
    } // GestureListener class

    public void onSwipeRight() {}
    public void onSwipeLeft() {}
    private void onSwipeTop() {}
    private void onSwipeBottom() {}
} // OnSwipeTouchListener