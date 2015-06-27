package com.yasser.collect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.os.Handler;
import android.widget.Toast;

/**
 * Created by yasser on 6/23/2015.
 */
public class GameplayActivity extends Activity implements View.OnClickListener
{
    Map<String,CountDownTimer> timers;
    LinearLayout firstTop;
    LinearLayout firstBottom;
    LinearLayout firstCenter;
    LinearLayout secondTop;
    LinearLayout secondBottom;
    LinearLayout secondCenter;
    LinearLayout thirdTop;
    LinearLayout thirdBottom;
    LinearLayout thirdCenter;
    List<Button> buttons;
    Handler mHandler;
    int creationInterval=500;
    int clicks;
    int rebuildTicket=0;
    Button b11;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameplay_layout);
        clicks=0;
        firstTop=(LinearLayout)findViewById(R.id.firstTop);
        secondTop=(LinearLayout)findViewById(R.id.secondTop);
        thirdTop=(LinearLayout)findViewById(R.id.thirdTop);
        firstBottom=(LinearLayout)findViewById(R.id.firstBottom);
        secondBottom=(LinearLayout)findViewById(R.id.secondBottom);
        thirdBottom=(LinearLayout)findViewById(R.id.thirdBottom);
        firstCenter=(LinearLayout)findViewById(R.id.firstCenter);
        secondCenter=(LinearLayout)findViewById(R.id.secondCenter);
        thirdCenter=(LinearLayout)findViewById(R.id.thirdCenter);
        mHandler=new Handler();
        timers=new HashMap<String,CountDownTimer>();
        buttons=new ArrayList<Button>() ;
        firstCenter.removeAllViews();
        createButtons();
        createTargets.run();
       // testPositioning();

    }
    void reBuild()
    {
        if(rebuildTicket==0)
        {
            rebuildTicket = 1;

            mHandler.removeCallbacks(createTargets);
            Toast.makeText(this, "Game Over :P", Toast.LENGTH_SHORT).show();
            firstTop.removeAllViews();
            firstCenter.removeAllViews();
            firstBottom.removeAllViews();
            secondBottom.removeAllViews();
            secondCenter.removeAllViews();
            secondTop.removeAllViews();
            thirdBottom.removeAllViews();
            thirdCenter.removeAllViews();
            thirdTop.removeAllViews();
            if(timers.containsKey("0"))
                timers.get("0").cancel();
            if(timers.containsKey("1"))
                timers.get("1").cancel();
            if(timers.containsKey("2"))
                timers.get("2").cancel();
            if(timers.containsKey("3"))
                timers.get("3").cancel();
            if(timers.containsKey("4"))
                timers.get("4").cancel();
            if(timers.containsKey("5"))
                timers.get("5").cancel();
            if(timers.containsKey("6"))
                timers.get("6").cancel();
            if(timers.containsKey("7"))
                timers.get("7").cancel();
            if(timers.containsKey("8"))
                timers.get("8").cancel();
            timers.clear();
            Intent intent=new Intent(GameplayActivity.this,GameOverActivity.class);
            intent.putExtra("Score",clicks);
            startActivity(intent);
            /*Button retry = new Button(this);
            retry.setWidth(70);
            retry.setHeight(30);
            retry.setText("Retry");
            retry.setOnClickListener(this);
            retry.setTag(100);
            Button Exit = new Button(this);
            Exit.setWidth(70);
            Exit.setHeight(30);
            Exit.setText("Exit");
            Exit.setOnClickListener(this);
            Exit.setTag(101);
            clicks = 0;
            creationInterval = 1000;
            firstCenter.addView(retry);
            thirdCenter.addView(Exit);*/
        }
    }
    void createButtons()
    {
        for(int i=0;i<9;i++)
        {
            Button a = new Button(this);
            a.setWidth(50);
            a.setHeight(20);
            a.setText("9");
            a.setTag(i);
            a.setOnClickListener(this);
            buttons.add(a);
        }
    }
    void testPositioning()
    {
        for(int i=0;i<9;i++)
        {
            Button a=new Button(this);
            a.setWidth(50);
            a.setHeight(20);
            a.setText("9");
            buttons.add(a);
        }
        firstTop.addView(buttons.get(0));
        secondTop.addView(buttons.get(1));
        thirdTop.addView(buttons.get(2));
        firstBottom.addView(buttons.get(3));
        secondBottom.addView(buttons.get(4));
        thirdBottom.addView(buttons.get(5));
        firstCenter.addView(buttons.get(6));
        secondCenter.addView(buttons.get(7));
        thirdCenter.addView(buttons.get(8));
    }
    Runnable createTargets=new Runnable()
    {
        int n;
        Random rand;
        @Override
        public void run()
        {
            if(clicks==20)
            {
                Toast.makeText(getApplicationContext(),"The Speed is now Increasing",Toast.LENGTH_LONG).show();
                creationInterval-=200;
            }
            if(clicks==40)
            {
                Toast.makeText(getApplicationContext(),"The Speed is now Increasing",Toast.LENGTH_LONG).show();
                creationInterval-=100;
            }
            if(clicks==70)
            {
                Toast.makeText(getApplicationContext(),"The Speed is now Increasing",Toast.LENGTH_LONG).show();
                creationInterval-=100;
            }
            if(clicks==100)
            {
                Toast.makeText(getApplicationContext(),"You have achieved the maximumg speed, try to hold on :P",Toast.LENGTH_LONG).show();
                creationInterval-=50;
            }
            mHandler.postDelayed(createTargets,creationInterval);
             rand=new Random();
             n=rand.nextInt(9);
            chooseLayout();
        }
        void chooseLayout()
        {

            if(firstTop.getChildCount()>0 && secondTop.getChildCount()>0 && thirdTop.getChildCount()>0 &&
                    firstCenter.getChildCount()>0 && secondCenter.getChildCount()>0 && thirdCenter.getChildCount()>0 &&
                    firstBottom.getChildCount()>0 && secondBottom.getChildCount()>0 && thirdBottom.getChildCount()>0)
                reBuild();
            for(int i=0;i<9;i++)
            {
                if(n==i&&n==0)
                {
                    if(firstTop.getChildCount()<=0)
                    {
                        firstTop.addView(buttons.get(0));
                      CountDownTimer timer=  new CountDownTimer(9000,1000)
                        {

                            @Override
                            public void onTick(long millisUntilFinished)
                            {
                                buttons.get(0).setText(""+millisUntilFinished/1000);
                            }

                            @Override
                            public void onFinish()
                            {
                                reBuild();
                            }
                        };
                        timer.start();
                        timers.put("0",timer);
                        break;
                    }
                    else
                    {
                        n=rand.nextInt(9);
                        i=0;
                    }
                }
                else if(n==i&&n==1)
                {
                    if(secondTop.getChildCount()<=0)
                    {
                        secondTop.addView(buttons.get(1));
                        CountDownTimer timer=  new CountDownTimer(9000,1000)
                        {

                            @Override
                            public void onTick(long millisUntilFinished)
                            {
                                buttons.get(1).setText(""+millisUntilFinished/1000);
                            }

                            @Override
                            public void onFinish()
                            {
                                reBuild();
                            }
                        };
                        timer.start();
                        timers.put("1", timer);
                        break;
                    }
                    else
                    {
                        n=rand.nextInt(9);
                        i=0;
                    }
                }
                else if(n==i&&n==2)
                {
                    if(thirdTop.getChildCount()<=0)
                    {
                        thirdTop.addView(buttons.get(2));
                        CountDownTimer timer=  new CountDownTimer(9000,1000)
                        {

                            @Override
                            public void onTick(long millisUntilFinished)
                            {
                                buttons.get(2).setText(""+millisUntilFinished/1000);
                            }

                            @Override
                            public void onFinish()
                            {
                                reBuild();
                            }
                        };
                        timer.start();
                        timers.put("2", timer);
                        break;
                    }
                    else
                    {
                        n=rand.nextInt(9);
                        i=0;
                    }
                }
                else if(n==i&&n==3)
                {
                    if(firstCenter.getChildCount()<=0)
                    {
                        firstCenter.addView(buttons.get(3));
                        CountDownTimer timer=  new CountDownTimer(9000,1000)
                        {

                            @Override
                            public void onTick(long millisUntilFinished)
                            {
                                buttons.get(3).setText(""+millisUntilFinished/1000);
                            }

                            @Override
                            public void onFinish()
                            {
                                reBuild();
                            }
                        };
                        timer.start();
                        timers.put("3", timer);
                        break;
                    }
                    else
                    {
                        n=rand.nextInt(9);
                        i=0;
                    }
                }
                else if(n==i&&n==4)
                {
                    if(secondCenter.getChildCount()<=0)
                    {
                        secondCenter.addView(buttons.get(4));
                        CountDownTimer timer=  new CountDownTimer(9000,1000)
                        {

                            @Override
                            public void onTick(long millisUntilFinished)
                            {
                                buttons.get(4).setText(""+millisUntilFinished/1000);
                            }

                            @Override
                            public void onFinish()
                            {
                                reBuild();
                            }
                        };
                        timer.start();
                        timers.put("4", timer);
                        break;
                    }
                    else
                    {
                        n=rand.nextInt(9);
                        i=0;
                    }
                }
                else if(n==i&&n==5)
                {
                    if(thirdCenter.getChildCount()<=0)
                    {
                        thirdCenter.addView(buttons.get(5));
                        CountDownTimer timer=  new CountDownTimer(9000,1000)
                        {

                            @Override
                            public void onTick(long millisUntilFinished)
                            {
                                buttons.get(5).setText("" + millisUntilFinished / 1000);
                            }

                            @Override
                            public void onFinish()
                            {
                                reBuild();
                            }
                        };
                        timer.start();
                        timers.put("5", timer);
                        break;
                    }
                    else
                    {
                        n=rand.nextInt(9);
                        i=0;
                    }
                }
                else if(n==i&&n==6)
                {
                    if(firstBottom.getChildCount()<=0)
                    {
                        firstBottom.addView(buttons.get(6));
                        CountDownTimer timer=  new CountDownTimer(9000,1000)
                        {

                            @Override
                            public void onTick(long millisUntilFinished)
                            {
                                buttons.get(6).setText(""+millisUntilFinished/1000);
                            }

                            @Override
                            public void onFinish()
                            {
                                reBuild();
                            }
                        };
                        timer.start();
                        timers.put("6", timer);
                        break;
                    }
                    else
                    {
                        n=rand.nextInt(9);
                        i=0;
                    }
                }
                else if(n==i&&n==7)
                {
                    if(secondBottom.getChildCount()<=0)
                    {
                        secondBottom.addView(buttons.get(7));
                        CountDownTimer timer=  new CountDownTimer(9000,1000)
                        {

                            @Override
                            public void onTick(long millisUntilFinished)
                            {
                                buttons.get(7).setText(""+millisUntilFinished/1000);
                            }

                            @Override
                            public void onFinish()
                            {
                                reBuild();
                            }
                        };
                        timer.start();
                        timers.put("7", timer);
                        break;
                    }
                    else
                    {
                        n=rand.nextInt(9);
                        i=0;
                    }
                }
                else if(n==i&&n==8)
                {
                    if(thirdBottom.getChildCount()<=0)
                    {
                        thirdBottom.addView(buttons.get(8));
                        CountDownTimer timer=  new CountDownTimer(9000,1000)
                        {

                            @Override
                            public void onTick(long millisUntilFinished)
                            {
                                buttons.get(8).setText(""+millisUntilFinished/1000);
                            }

                            @Override
                            public void onFinish()
                            {
                                reBuild();
                            }
                        };
                        timer.start();
                        timers.put("8", timer);
                        break;
                    }
                    else
                    {
                        n=rand.nextInt(9);
                        i=0;
                    }
                }
            }
        }
    };

    @Override
    public void onClick(View v)
    {
        clicks++;
        int btn=(int)v.getTag();
        String key=v.getTag().toString();
        CountDownTimer timer;
           switch (btn)
           {
               case 0:
                   timer=timers.get(key);
                   timer.cancel();
                   timers.remove(key);
                   firstTop.removeAllViews();
                   break;
               case 1:
                   timer=timers.get(key);
                   timer.cancel();
                   timers.remove(key);
                   secondTop.removeAllViews();
                   break;
               case 2:
                   timer=timers.get(key);
                   timer.cancel();
                   timers.remove(key);
                   thirdTop.removeAllViews();
                   break;
               case 3:
                   timer=timers.get(key);
                   timer.cancel();
                   timers.remove(key);
                   firstCenter.removeAllViews();
                   break;
               case 4:
                   timer=timers.get(key);
                   timer.cancel();
                   timers.remove(key);
                   secondCenter.removeAllViews();
                   break;
               case 5:
                   timer=timers.get(key);
                   timer.cancel();
                   timers.remove(key);
                   thirdCenter.removeAllViews();
                   break;
               case 6:
                   timer=timers.get(key);
                   timer.cancel();
                   timers.remove(key);
                   firstBottom.removeAllViews();
                   break;
               case 7:
                   timer=timers.get(key);
                   timer.cancel();
                   timers.remove(key);
                   secondBottom.removeAllViews();
                   break;
               case 8:
                   timer=timers.get(key);
                   timer.cancel();
                   timers.remove(key);
                   thirdBottom.removeAllViews();
                   break;
               case 100:
                   thirdCenter.removeAllViews();
                   firstCenter.removeAllViews();
                   rebuildTicket=0;
                   createTargets.run();
                   break;
               case 101:
                   Intent intent = new Intent(Intent.ACTION_MAIN);
                   intent.addCategory(Intent.CATEGORY_HOME);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   startActivity(intent);
                   break;
           }
    }
}
