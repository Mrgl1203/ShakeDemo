package com.gl.shakedemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ShakeManager shakeManager;
    private static final String TAG = "MainActivity";
    Vibrator vibrator;//震动
    Animation rotate1;
    private ImageView ivShake;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        shakeManager = new ShakeManager(this);
        shakeManager.setonShakeListener(new ShakeManager.onShakeListener() {
            @Override
            public void shake() {
                Log.e(TAG, "shake:-------------- ");
                startAnim();
                startVibrator();
            }
        });
    }

    private void init() {
        ivShake = findViewById(R.id.ivShake);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        rotate1 = AnimationUtils.loadAnimation(this, R.anim.shake_rotate1);
        rotate1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                showProduct();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void showProduct() {
        Intent intent = new Intent(this, ProductActivity.class);
        startActivity(intent);
    }


    private void startVibrator() {
        long[] times = new long[]{0, 500};
        vibrator.vibrate(times, -1);
    }

    private void startAnim() {
        ivShake.startAnimation(rotate1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        shakeManager.registerListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        shakeManager.unregisterListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
