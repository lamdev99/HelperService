package com.lamdev99.helperservice;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.FrameLayout;

public class HelperService extends AccessibilityService {
    FrameLayout mLayout;

    @Override
    protected void onServiceConnected() {
        // Create an overlay and display the action bar
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        mLayout = new FrameLayout(this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY;
        lp.format = PixelFormat.TRANSLUCENT;
        lp.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.TOP;
        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(R.layout.action_bar, mLayout);
        wm.addView(mLayout, lp);
        configurePowerButton();
        configureVolumeButtonUp();
        configureVolumeButtonDown();
        configureSwipeButtonDown();
        configureSwipeButtonRight();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onInterrupt() {

    }

    private void configurePowerButton() {
        Button powerButton = mLayout.findViewById(R.id.power);
        powerButton.setOnClickListener(view -> performGlobalAction(GLOBAL_ACTION_POWER_DIALOG));
    }

    private void configureVolumeButtonUp() {
        Button volumeUpButton = mLayout.findViewById(R.id.volume_up);
        volumeUpButton.setOnClickListener(view -> {
            AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
        });
    }

    private void configureVolumeButtonDown() {
        Button volumeUpButton = mLayout.findViewById(R.id.volume_down);
        volumeUpButton.setOnClickListener(view -> {
            AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
        });
    }
    private void configureSwipeButtonDown() {
        Button swipeButton = (Button) mLayout.findViewById(R.id.swipe_down);
        swipeButton.setOnClickListener(view -> {
            Path swipePath = new Path();
            swipePath.moveTo(100, 1000);
            swipePath.lineTo(100, 100);
            GestureDescription.Builder gestureBuilder = new GestureDescription.Builder();
            gestureBuilder.addStroke(new GestureDescription.StrokeDescription(swipePath, 0, 500));
            dispatchGesture(gestureBuilder.build(), null, null);
        });
    }

    private void configureSwipeButtonRight() {
        Button swipeButton = (Button) mLayout.findViewById(R.id.swipe_right);
        swipeButton.setOnClickListener(view -> {
            Path swipePath = new Path();
            swipePath.moveTo(1000, 1000);
            swipePath.lineTo(100, 1000);
            GestureDescription.Builder gestureBuilder = new GestureDescription.Builder();
            gestureBuilder.addStroke(new GestureDescription.StrokeDescription(swipePath, 0, 500));
            dispatchGesture(gestureBuilder.build(), null, null);
        });
    }
}

