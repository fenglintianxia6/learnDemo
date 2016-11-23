package com.demo.learndemos.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Vibrator;

/**
 * Created by zyf on 2016/10/27.
 */

public class PhoneShakeListener {

  public static final int SENSITIVITY_LIGHT = 11;
  public static final int SENSITIVITY_MEDIUM = 13;
  public static final int SENSITIVITY_HARD = 15;

  private static int DEFAULT_ACCELERATION_THRESHOLD = SENSITIVITY_MEDIUM;

  private SensorManager sensorManager;
  private SensorEventListener listener;
  private SampleQuene quene;
  private Context mContext;
  private Vibrator vibrator;
  private hearShakeListener hearShake;

  public PhoneShakeListener(Context context) {
    mContext = context;
    sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    quene = new SampleQuene();
  }

  public void setHearShake(hearShakeListener hearShake) {
    this.hearShake = hearShake;
  }

  /**
   * 设置晃动的灵敏度
   */
  public static void setDefaultAccelerationThreshold(int defaultAccelerationThreshold) {
    DEFAULT_ACCELERATION_THRESHOLD = defaultAccelerationThreshold;
  }

  public void registerSensorListener() {
    listener = new SensorEventListener();
    if (sensorManager != null) {
      sensorManager.registerListener(listener,
          sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
          SensorManager.SENSOR_DELAY_FASTEST);
    }
  }

  public void unRegisterSensorListener() {
    if (sensorManager != null) {
      sensorManager.unregisterListener(listener);
    }
  }

  public class SensorEventListener implements android.hardware.SensorEventListener {

    @Override public void onSensorChanged(SensorEvent event) {

      int type = event.sensor.getType();
      if (Sensor.TYPE_ACCELEROMETER != type) {
        return;
      }
      float[] values = event.values;
      long timestamp = event.timestamp;
      boolean accelerating = isAccelerating(values[0], values[1], values[2]);
      quene.add(timestamp, accelerating);
      if (quene.isAccelerating()) {
        quene.clear();
        if (hearShake != null) {
          hearShake.hearShake();
        }
      }
    }

    @Override public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /** 当前震动是否达到了阈值 */
    private boolean isAccelerating(float x, float y, float z) {
      return DEFAULT_ACCELERATION_THRESHOLD * DEFAULT_ACCELERATION_THRESHOLD
          < x * x + y * y + z * z;
    }
  }

  public class Sample {
    /**
     * 晃动手机发生的时间戳
     */
    long timeStamp;

    /**
     * 这个晃动加速度是否达到了阈值
     */
    boolean accelerting;
    /**
     * 存储这个Sample的队列中的下一个Sample
     */
    Sample next;
  }

  /**
   * 达到阈值的Sample的队列
   */
  public class SampleQuene {

    /** Window size in ns. Used to compute the average. */
    private static final long MAX_WINDOW_SIZE = 500000000; // 0.5s
    private static final long MIN_WINDOW_SIZE = MAX_WINDOW_SIZE >> 1; // 0.25s

    private static final int MIN_SAMPLE_SIZE = 4;

    private SamplePool pool = new SamplePool();

    Sample newest;
    Sample oldest;
    int sampleCount;
    int acceleratingCount;

    void add(long timeStamp, boolean isAccelerating) {
      purge(timeStamp - MAX_WINDOW_SIZE);

      //将这个Sample添加到队列中
      Sample sample = pool.acquire();
      sample.timeStamp = timeStamp;
      sample.accelerting = isAccelerating;
      sample.next = null;
      if (newest != null) {
        newest.next = sample;
      }
      newest = sample;
      if (oldest == null) {
        oldest = sample;
      }
      sampleCount++;
      if (isAccelerating) {
        acceleratingCount++;
      }
    }

    /**
     * 清空pool中的Sample
     */
    void clear() {
      while (oldest != null) {
        Sample removed = oldest;
        oldest = oldest.next;
        pool.release(removed);
      }
      sampleCount = 0;
      acceleratingCount = 0;
      newest = null;
    }

    /** 清除一段时间之前的Sample */
    void purge(long cutOff) {
      while (sampleCount >= MIN_SAMPLE_SIZE && oldest != null && cutOff - oldest.timeStamp > 0) {
        Sample removed = oldest;
        if (removed.accelerting) {
          acceleratingCount--;
        }
        sampleCount--;

        oldest = removed.next;
        if (oldest == null) {
          newest = null;
        }
        pool.release(removed);
      }
    }

    boolean isAccelerating() {
      return newest != null
          && oldest != null
          && newest.timeStamp - oldest.timeStamp >= MIN_WINDOW_SIZE
          && acceleratingCount > (sampleCount >> 1) + (sampleCount >> 2);
    }
  }

  /**
   * 保存所有的Sample
   */
  public class SamplePool {
    private Sample head;

    /** Acquires a sample from the pool. */
    Sample acquire() {
      Sample acquired = head;
      if (acquired == null) {
        acquired = new Sample();
      } else {
        // Remove instance from pool.
        head = acquired.next;
      }
      return acquired;
    }

    /** Returns a sample to the pool. */
    void release(Sample sample) {
      sample.next = head;
      head = sample;
    }
  }

  public interface hearShakeListener {
    void hearShake();
  }
}
