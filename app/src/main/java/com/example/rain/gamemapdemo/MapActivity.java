package com.example.rain.gamemapdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by rain on 2016/10/23.
 */
public class MapActivity extends Activity implements GestureDetector.OnGestureListener{

    private Button mapbtn;
    private GestureDetector detector;
    private LinearLayout con;
    private int currentX, currentY;
    private TextView mapText;
    private ImageView chicken;
    private GifImageView backGif;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        detector = new GestureDetector(this, this);
        con = (LinearLayout) findViewById(R.id.con);
        mapText = (TextView) findViewById(R.id.mapText);
        chicken = (ImageView) findViewById(R.id.chic);
        backGif = (GifImageView) findViewById(R.id.mapBg);

        chicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MapActivity.this, "X:" + currentX + " Y:" + currentY, Toast.LENGTH_SHORT).show();
            }
        });

        try {
            // 如果加载的是gif动图，第一步需要先将gif动图资源转化为GifDrawable
            // 将gif图资源转化为GifDrawable
            GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.map_background);

            // gif1加载一个动态图gif
            backGif.setImageDrawable(gifDrawable);


            // 如果是普通的图片资源，就像Android的ImageView set图片资源一样简单设置进去即可。
            // gif2加载一个普通的图片（如png，bmp，jpeg等等）
            //gifImageView2.setImageResource(R.drawable.ic_launcher);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                currentX = (int) event.getRawX();
                currentY = (int) event.getRawY();
                break;
            }
            case MotionEvent.ACTION_MOVE:
            {
                int x2 = (int) event.getRawX();
                int y2 = (int) event.getRawY();
                con.scrollBy(currentX - x2 , currentY - y2);
                currentX = x2;
                currentY = y2;
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                break;
            }
        }
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Toast.makeText(MapActivity.this, "onDown" + e.getY(), Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        //Toast.makeText(MapActivity.this, "onShowPress", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        //Toast.makeText(MapActivity.this, "onSingleTapUp", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //Toast.makeText(MapActivity.this, "onScroll", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        //Toast.makeText(MapActivity.this, "onLongPress", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //Toast.makeText(MapActivity.this, "onFling", Toast.LENGTH_SHORT).show();
        return false;
    }
}
