package com.example.mealdelivery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class PhotoView extends View{  	
	
	private Listener listener; // A listener listen to on click event
	private boolean singlePress = false; // Record whether it is a single finger press or not
	// state variable for touching state 
    public static final int STATUS_INIT = 1; 
    public static final int STATUS_ZOOM_OUT = 2;   
    public static final int STATUS_ZOOM_IN = 3;  
    public static final int STATUS_MOVE = 4;  
    private int currentStatus;
    
    private Matrix matrix = new Matrix();  // Used to picture zooming
    private Bitmap sourceBitmap;  

    // Used for zooming and moving
    private int width;   
    private int height;  
    private float centerPointX;  
    private float centerPointY;  
    private float currentBitmapWidth;  
    private float currentBitmapHeight;  
    private float lastXMove = -1;  
    private float lastYMove = -1;  
    private float movedDistanceX;  
    private float movedDistanceY;  
    private float totalTranslateX;  
    private float totalTranslateY;  
    private float totalRatio;  
    private float scaledRatio;  
    private float initRatio;  
    private double lastFingerDis;  

    // Used for detect onClick behavior
    private float onClickX;
    private float onClickY;
    
    public PhotoView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        currentStatus = STATUS_INIT;  
    }  
  
    /**
     * Load image
     * @param bitmap
     */
    public void setImageBitmap(Bitmap bitmap) {  
        sourceBitmap = bitmap;  
        invalidate();  
    }  
  
    @Override  
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {  
        super.onLayout(changed, left, top, right, bottom);  
        if (changed) {  
            width = getWidth();  
            height = getHeight();  
        }  
    }  
  
    @Override  
    public boolean onTouchEvent(MotionEvent event) {  
        switch (event.getActionMasked()) {  
        case MotionEvent.ACTION_POINTER_DOWN:  
            if (event.getPointerCount() == 2) {  
            	// Record finger distance
                lastFingerDis = distanceBetweenFingers(event); 
            }  
            singlePress = false;
            break;  
        case MotionEvent.ACTION_DOWN:
        	onClickX = event.getX();  
        	onClickY = event.getY();   
        	singlePress = true;
        	break;
        case MotionEvent.ACTION_MOVE:  
            if (event.getPointerCount() == 1) {  
                float xMove = event.getX();  
                float yMove = event.getY();  
                if (lastXMove == -1 && lastYMove == -1) {  
                    lastXMove = xMove;  
                    lastYMove = yMove;  
                }  
                currentStatus = STATUS_MOVE;  
                movedDistanceX = xMove - lastXMove;  
                movedDistanceY = yMove - lastYMove;  
                // Boundary detection
                if (totalTranslateX + movedDistanceX > 0) {  
                    movedDistanceX = 0;  
                } else if (width - (totalTranslateX + movedDistanceX) > currentBitmapWidth) {  
                    movedDistanceX = 0;
                }  
                if (totalTranslateY + movedDistanceY > 0) {  
                    movedDistanceY = 0;  
                } else if (height - (totalTranslateY + movedDistanceY) > currentBitmapHeight) {  
                    movedDistanceY = 0;  
                }  
                invalidate();  
                lastXMove = xMove;  
                lastYMove = yMove;  
            } else 
            	if (event.getPointerCount() == 2) {  
                centerPointBetweenFingers(event);  
                double fingerDis = distanceBetweenFingers(event);  
                if (fingerDis > lastFingerDis) {  
                    currentStatus = STATUS_ZOOM_OUT;  
                } else {  
                    currentStatus = STATUS_ZOOM_IN;  
                }  
                if ((currentStatus == STATUS_ZOOM_OUT && totalRatio < 4 * initRatio)  
                        || (currentStatus == STATUS_ZOOM_IN && totalRatio > initRatio)) {  
                    scaledRatio = (float) (fingerDis / lastFingerDis);  
                    totalRatio = totalRatio * scaledRatio;  
                    if (totalRatio > 4 * initRatio) {  
                        totalRatio = 4 * initRatio;  
                    } else if (totalRatio < initRatio) {  
                        totalRatio = initRatio;  
                    }  
                    invalidate();  
                    lastFingerDis = fingerDis;  
                }  
            }  
            break;  
        case MotionEvent.ACTION_POINTER_UP:  
            if (event.getPointerCount() == 2) {  
                lastXMove = -1;  
                lastYMove = -1;  
            }  
            singlePress = false;
            break;  
        case MotionEvent.ACTION_UP:  
            lastXMove = -1;  
            lastYMove = -1; 
            float xMove = event.getX();  
            float yMove = event.getY();
            if (singlePress
            	&&
            	Math.abs(onClickX - xMove) < 50
            	&&
            	Math.abs(onClickY - yMove) < 50) {
            	singlePress = false;
            	if (listener != null) {
            		listener.informed();
            		currentStatus = STATUS_INIT;
            		invalidate(); 
            	}
            }
            break;  
        default:  
            break;  
        }  
        return true;  
    }  
    
    public void addListener(Listener listener) {
    	this.listener = listener;
    }
    
    /**
     * Draw picture based on curren state
     */
    @Override  
    protected void onDraw(Canvas canvas) {  
        super.onDraw(canvas);  
        switch (currentStatus) {  
        case STATUS_ZOOM_OUT:  
        case STATUS_ZOOM_IN:  
            zoom(canvas);  
            break;  
        case STATUS_MOVE:  
            move(canvas);  
            break;  
        case STATUS_INIT:  
            initBitmap(canvas);  
        default:  
            canvas.drawBitmap(sourceBitmap, matrix, null);  
            break;  
        }  
    }  
  
    /**
     * Zooming
     * @param canvas
     */
    private void zoom(Canvas canvas) {  
        matrix.reset();  
        matrix.postScale(totalRatio, totalRatio);  
        float scaledWidth = sourceBitmap.getWidth() * totalRatio;  
        float scaledHeight = sourceBitmap.getHeight() * totalRatio;  
        float translateX = 0f;  
        float translateY = 0f;  
        if (currentBitmapWidth < width) {  
            translateX = (width - scaledWidth) / 2f;  
        } else {  
            translateX = totalTranslateX * scaledRatio + centerPointX * (1 - scaledRatio);  
            if (translateX > 0) {  
                translateX = 0;  
            } else if (width - translateX > scaledWidth) {  
                translateX = width - scaledWidth;  
            }  
        }    
        if (currentBitmapHeight < height) {  
            translateY = (height - scaledHeight) / 2f;  
        } else {  
            translateY = totalTranslateY * scaledRatio + centerPointY * (1 - scaledRatio);  
            if (translateY > 0) {  
                translateY = 0;  
            } else if (height - translateY > scaledHeight) {  
                translateY = height - scaledHeight;  
            }  
        }  
        matrix.postTranslate(translateX, translateY);  
        totalTranslateX = translateX;  
        totalTranslateY = translateY;  
        currentBitmapWidth = scaledWidth;  
        currentBitmapHeight = scaledHeight;  
        canvas.drawBitmap(sourceBitmap, matrix, null);  
    }  
  
    /**
     * Moving
     * @param canvas
     */
    private void move(Canvas canvas) {  
        matrix.reset();  
        float translateX = totalTranslateX + movedDistanceX;  
        float translateY = totalTranslateY + movedDistanceY;  
        matrix.postScale(totalRatio, totalRatio);  
        matrix.postTranslate(translateX, translateY);  
        totalTranslateX = translateX;  
        totalTranslateY = translateY;  
        canvas.drawBitmap(sourceBitmap, matrix, null);  
    }  
  
    /**
     * Initially load map
     * @param canvas
     */
    private void initBitmap(Canvas canvas) {  
        if (sourceBitmap != null) {  
            matrix.reset();  
            int bitmapWidth = sourceBitmap.getWidth();  
            int bitmapHeight = sourceBitmap.getHeight();  
            if (bitmapWidth > width || bitmapHeight > height) {  
                if (bitmapWidth - width > bitmapHeight - height) {  
                    float ratio = width / (bitmapWidth * 1.0f);  
                    matrix.postScale(ratio, ratio);  
                    float translateY = (height - (bitmapHeight * ratio)) / 2f;  
                    matrix.postTranslate(0, translateY);  
                    totalTranslateY = translateY;  
                    totalRatio = initRatio = ratio;  
                } else {  
                    float ratio = height / (bitmapHeight * 1.0f);  
                    matrix.postScale(ratio, ratio);  
                    float translateX = (width - (bitmapWidth * ratio)) / 2f;  
                    matrix.postTranslate(translateX, 0);  
                    totalTranslateX = translateX;  
                    totalRatio = initRatio = ratio;  
                }  
                currentBitmapWidth = bitmapWidth * initRatio;  
                currentBitmapHeight = bitmapHeight * initRatio;  
            } else {  
                float translateX = (width - sourceBitmap.getWidth()) / 2f;  
                float translateY = (height - sourceBitmap.getHeight()) / 2f;  
                matrix.postTranslate(translateX, translateY);  
                totalTranslateX = translateX;  
                totalTranslateY = translateY;  
                totalRatio = initRatio = 1f;  
                currentBitmapWidth = bitmapWidth;  
                currentBitmapHeight = bitmapHeight;  
            }  
            canvas.drawBitmap(sourceBitmap, matrix, null);  
        }  
    }  
  
    private double distanceBetweenFingers(MotionEvent event) {  
        float disX = Math.abs(event.getX(0) - event.getX(1));  
        float disY = Math.abs(event.getY(0) - event.getY(1));  
        return Math.sqrt(disX * disX + disY * disY);  
    }  
  
    private void centerPointBetweenFingers(MotionEvent event) {  
        float xPoint0 = event.getX(0);  
        float yPoint0 = event.getY(0);  
        float xPoint1 = event.getX(1);  
        float yPoint1 = event.getY(1);  
        centerPointX = (xPoint0 + xPoint1) / 2;  
        centerPointY = (yPoint0 + yPoint1) / 2;  
    }
}  
