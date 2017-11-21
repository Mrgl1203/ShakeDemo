package com.gl.shakedemo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by gl152 on 2017/11/20.
 */

public class ShakeManager implements SensorEventListener {
    private static final String TAG = "ShakeManager";
    private Context context;
    private SensorManager sensorManager;
    private Sensor sensor;
    private int miniValue = 15;
    /**
     * 检测的时间间隔
     */
    private static final int UPDATE_INTERVAL = 1000;
    /**
     * 上一次检测的时间
     */
    private long mLastUpdateTime;

    private onShakeListener onShakeListener;
    private boolean isShake = false;

    public ShakeManager(Context context) {
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        /**
         * 传感器类型列表：

         Sensor.TYPE_ACCELEROMETER:
         加速计传感器

         Sensor.TYPE_GYROSCOPE:
         回转仪传感器

         Sensor.TYPE_LIGHT:
         光传感器，动态控制屏幕亮度

         Sensor.TYPE_MAGNETIC_FIELD:
         磁场传感器

         Sensor.TYPE_ORIENTATION:
         方向传感器

         Sensor.TYPE_PRESSURE:
         压力传感器

         Sensor.TYPE_PROXIMIY:
         邻近距离传感器

         Sensor.TYPE_TEMPERATURE:
         温度传感器
         */
        if (sensorManager != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        registerListener();
    }

    public void registerListener() {

        /**
         传感器更新速率：
         SensorManager.SENSOR_DELAY_FASTEST:
         指定可能最快的传感器更新速率
         SensorManager.SENSOR_DELAY_GAME:
         指定适合在游戏中使用的更新速率
         SensorManager.SENSOR_DELAY_NORMAL:
         指定默认的更新速率
         SensorManager.SENSOR_DELAY_UI:
         指定适合于更新UI的更新速率
         */
        if (sensor != null && sensorManager != null)
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    public void unregisterListener() {
        if (sensor != null && sensorManager != null)
            sensorManager.unregisterListener(this, sensor);
    }

    //传感器的值改变调用此方法
    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];//x轴变化的值
        float y = event.values[1];//y轴变化的值
        float z = event.values[2];//z轴变化的值,记得减去9.8重力加速度

        if (Math.abs(x) > miniValue || Math.abs(y) > miniValue || Math.abs(z - 9.8) > miniValue) {
            long currentTime = System.currentTimeMillis();
            long diffTime = currentTime - mLastUpdateTime;
            if (diffTime < UPDATE_INTERVAL) {
                return;
            }
            //传感器太灵敏，防止摇一摇触发多次,UPDATE_INTERVAL效果执行多久就设置多久
            mLastUpdateTime = currentTime;
            Log.i(TAG, "onSensorChanged: ---x" + x + "--y" + y + "--z" + z);
            if (onShakeListener != null) {
                onShakeListener.shake();
            }
        }

    }

    //传感器的精确度改变调用此方法
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void IsShake(boolean isShake) {
        this.isShake = isShake;
    }


    public void setonShakeListener(onShakeListener onShakeListener) {
        this.onShakeListener = onShakeListener;
    }

    interface onShakeListener {
        void shake();
    }

}
