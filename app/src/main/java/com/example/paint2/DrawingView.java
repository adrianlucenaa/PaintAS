package com.example.paint2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {

    private Paint paint = new Paint();
    private float lastX, lastY;
    private Bitmap bitmap;
    private Canvas canvas;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
    }

    //Metodo para inicializar el canvas en el tamaño correcto
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }

    // Logica del pincel para pintar una linea cuando pulsemos
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        // Obtener la ubicación del dedo en el lienzo

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                return true;
            case MotionEvent.ACTION_MOVE:
                // Dibujar una línea desde (lastX, lastY) hasta (x, y) en el lienzo
                drawLineOnCanvas(lastX, lastY, x, y);
                lastX = x;
                lastY = y;
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    //Metodo para dibujar una línea
    private void drawLineOnCanvas(float startX, float startY, float endX, float endY) {
        canvas.drawLine(startX, startY, endX, endY, paint);
        invalidate();  // Notificar al sistema que la vista ha cambiado y necesita ser redibujada
    }


    //Metodo para ajustar el color del pincel
    public void setBrushColor(int progress) {
        int color = Color.HSVToColor(255, new float[]{progress, 1.0f, 1.0f});
        paint.setColor(color);
    }



    //Metodo para ajustar el tamaño del pincel
    public void setBrushSize(int progress) {
        paint.setStrokeWidth(progress);
    }
    //Metodo para limpiar el cambas
    public void clearCanvas() {
        canvas.drawColor(Color.WHITE);  // Limpiar el lienzo dibujando un fondo blanco
        invalidate();  // Notificar al sistema que la vista ha cambiado y necesita ser redibujada
    }

    // Metodo para ajustar el color del pincel
    /*
    public void setBrushColor(int colorValue) {
        int red = (colorValue >> 16) & 0xFF;
        int green = (colorValue >> 8) & 0xFF;
        int blue = colorValue & 0xFF;
        paint.setColor(Color.rgb(red, green, blue));
    }
     */
}