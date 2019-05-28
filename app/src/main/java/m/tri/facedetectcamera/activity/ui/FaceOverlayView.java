// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package m.tri.facedetectcamera.activity.ui;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import m.tri.facedetectcamera.R;

import java.io.IOException;
import java.text.DecimalFormat;

import m.tri.facedetectcamera.activity.FaceDetectGrayActivity;
import m.tri.facedetectcamera.model.FaceResult;

/**
 * Created by Nguyen on 5/20/2016.
 */

/**
 * This class is a simple View to display the faces.
 */
public class FaceOverlayView extends View {

    private Paint mPaint;
    private Paint mTextPaint;
    private int mDisplayOrientation;
    private int mOrientation;
    private int previewWidth;
    private int previewHeight;
    private FaceResult[] mFaces;
    private double fps;
    private boolean isFront = true;
    public int which_glass_string = R.drawable.glass10;


    public FaceOverlayView(Context context) {
        super(context);
        initialize();

    }

    private void initialize() {
        // We want a green box around the face:
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        int stroke = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, metrics);
        int imageResource;
        ImageView firstImage;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(stroke);
        mPaint.setStyle(Paint.Style.STROKE);


    //    mBitmap = BitmapFactory.decodeFile("E:/detection/FaceDetectCamera-master/app/src/main/assets/glass10.png");
    //    mBitmap = BitmapFactory.decodeFile("E:\\detection\\FaceDetectCamera-master\\app\\src\\main\\assets\\glass10.png");
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, metrics);
        mTextPaint.setTextSize(size);
        mTextPaint.setColor(Color.GREEN);
        mTextPaint.setStyle(Paint.Style.FILL);


    }

    public void setFPS(double fps) {
        this.fps = fps;
    }

    public void setFaces(FaceResult[] faces) {
        mFaces = faces;
        invalidate();
    }

    public void setOrientation(int orientation) {
        mOrientation = orientation;
    }

    public void setDisplayOrientation(int displayOrientation) {
        mDisplayOrientation = displayOrientation;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mFaces != null && mFaces.length > 0) {

            float scaleX = (float) getWidth() / (float) previewWidth;
            float scaleY = (float) getHeight() / (float) previewHeight;

            switch (mDisplayOrientation) {
                case 90:
                case 270:
                    scaleX = (float) getWidth() / (float) previewHeight;
                    scaleY = (float) getHeight() / (float) previewWidth;
                    break;
            }

            canvas.save();
            canvas.rotate(-mOrientation);
            RectF rectF = new RectF();
            RectF glassRect =  new RectF();
            for (FaceResult face : mFaces) {
                PointF mid = new PointF();
                face.getMidPoint(mid);

                if (mid.x != 0.0f && mid.y != 0.0f) {
                    float eyesDis = face.eyesDistance();

                    rectF.set(new RectF(
                            (mid.x - eyesDis * 1.2f) * scaleX,
                            (mid.y - eyesDis * 0.65f) * scaleY,
                            (mid.x + eyesDis * 1.2f) * scaleX,
                            (mid.y + eyesDis * 1.75f) * scaleY));

                    if (isFront) {
                        float left = rectF.left;
                        float right = rectF.right;
                        rectF.left = getWidth() - right;
                        rectF.right = getWidth() - left;
                    }
                    canvas.drawRect(rectF, mPaint);
           //         canvas.drawCircle(rectF.left + eyesDis * 0.7f, rectF.top + eyesDis * 0.8f,eyesDis * 0.3f ,mPaint)
               //     canvas.drawCircle(rectF.right - eyesDis * 0.7f, rectF.top + eyesDis * 0.8f,eyesDis * 0.3f ,mPaint);
                    float headPos_x = face.getPoseX();
                    float headPos_y = face.getPoseY();
                    float headPos_z = face.getPoseZ();

                    glassRect.set(new RectF(
                            rectF.left + eyesDis * 0.2f + headPos_x,
                            rectF.top + eyesDis * 0.4f + headPos_y,
                            rectF.right - eyesDis * 0.2f,
                            rectF.top + eyesDis * 1.2f
                    ));
                    Resources res = getResources();
                    Bitmap bitmap = BitmapFactory.decodeResource(res,which_glass_string);

                    canvas.drawBitmap(bitmap, null, glassRect, mPaint);

        //            canvas.drawBitmap(mBitmap, null ,rectF,  mPaint);


          //          canvas.drawText("ID " + face.getId(), rectF.left, rectF.bottom + mTextPaint.getTextSize(), mTextPaint);
          //          canvas.drawText("Confidence " + face.getConfidence(), rectF.left, rectF.bottom + mTextPaint.getTextSize() * 2, mTextPaint);
                    canvas.drawText("Pose " + face.getPoseX() + ": " + face.getPoseY() + ": " + face.getPoseZ()  , rectF.left, rectF.bottom + mTextPaint.getTextSize() * 3, mTextPaint);
                }
            }
            canvas.restore();
        }

       // DecimalFormat df2 = new DecimalFormat(".##");
       // canvas.drawText("Detected_Frame/s: " + df2.format(fps) + " @ " + previewWidth + "x" + previewHeight, mTextPaint.getTextSize(), mTextPaint.getTextSize(), mTextPaint);
    }

    public void setPreviewWidth(int previewWidth) {
        this.previewWidth = previewWidth;
    }

    public void setPreviewHeight(int previewHeight) {
        this.previewHeight = previewHeight;
    }

    public void setFront(boolean front) {
        isFront = front;
    }
}