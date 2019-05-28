package m.tri.facedetectcamera.model;

import android.graphics.PointF;

/**
 * Created by Nguyen on 5/20/2016.
 */

public class FaceResult extends Object {

    private PointF midEye;
    private float eyeDist;
    private float confidence;
    private float pose_x;
    private float pose_y;
    private float pose_z;
    private int id;
    private long time;

    public FaceResult() {
        id = 0;
        midEye = new PointF(0.0f, 0.0f);
        eyeDist = 0.0f;
        confidence = 0.4f;
        pose_x = 0.0f;
        pose_y = 0.0f;
        pose_z = 0.0f;
        time = System.currentTimeMillis();
    }


    public void setFace(int id, PointF midEye, float eyeDist, float confidence, float pose_x,float pose_y,float pose_z, long time) {
        set(id, midEye, eyeDist, confidence, pose_x, pose_y, pose_z, time);
    }

    public void clear() {
        set(0, new PointF(0.0f, 0.0f), 0.0f, 0.4f, 0.0f,0.0f, 0.0f, System.currentTimeMillis());
    }

    public synchronized void set(int id, PointF midEye, float eyeDist, float confidence, float pose_x,float pose_y,float pose_z, long time) {
        this.id = id;
        this.midEye.set(midEye);
        this.eyeDist = eyeDist;
        this.confidence = confidence;
        this.pose_x = pose_x;
        this.pose_y = pose_y;
        this.pose_z = pose_z;
        this.time = time;
    }

    public float eyesDistance() {
        return eyeDist;
    }

    public void setEyeDist(float eyeDist) {
        this.eyeDist = eyeDist;
    }

    public void getMidPoint(PointF pt) {
        pt.set(midEye);
    }

    public PointF getMidEye() {
        return midEye;
    }

    public void setMidEye(PointF midEye) {
        this.midEye = midEye;
    }

    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }

    public float getPoseX() {
        return pose_x;
    }

    public void setPoseX(float pose) {
        this.pose_x = pose_x;
    }

    public float getPoseY() {
        return pose_y;
    }

    public void setPoseY(float pose) {
        this.pose_y = pose_y;
    }

    public float getPoseZ() {
        return pose_z;
    }

    public void setPoseZ(float pose) {
        this.pose_z = pose_z;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
