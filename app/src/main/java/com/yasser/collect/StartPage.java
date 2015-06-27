package com.yasser.collect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by yasser on 6/25/2015.
 */
public class StartPage extends Activity implements View.OnClickListener
{
    Button playButton,howToPlayButton;
    RelativeLayout mainLayout;
    LinearLayout playButtonLayout;
    TextView howToPlayTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startpage_layout);
        mainLayout=(RelativeLayout)findViewById(R.id.mainLayout);
        playButtonLayout=(LinearLayout)findViewById(R.id.playButtonLayout);
        howToPlayTextView=(TextView)findViewById(R.id.howToPlayTextView);
        playButton=(Button)findViewById(R.id.playButton);
        howToPlayButton=(Button)findViewById(R.id.howToPlayButton);
        playButton.setTag(0);
        playButton.setOnClickListener(this);
        howToPlayButton.setTag(1);
        howToPlayButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        int tag=(int)v.getTag();
        switch (tag)
        {
            case 0:
                Intent intent=new Intent(StartPage.this,GameplayActivity.class);
                startActivity(intent);
                break;
            case 1:
                mainLayout.removeView(playButton);
                mainLayout.removeView(howToPlayButton);
                howToPlayTextView.setVisibility(View.VISIBLE);
                playButtonLayout.addView(playButton);
                break;
        }
    }
}
