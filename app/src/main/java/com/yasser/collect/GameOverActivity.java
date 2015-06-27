package com.yasser.collect;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.net.URI;

/**
 * Created by yasser on 6/25/2015.
 */
    public class GameOverActivity extends Activity implements View.OnClickListener
    {
        Button shareOnTwitterButton,retryButton,exitButton,tweetButton,cancelTweetButton;
        EditText tweetEditText;
        LinearLayout tweetLayout;
        RelativeLayout scoreLayout;
        TextView scoreTextView;
        public final static String TWIT_KEY = "BCspgws0qFTLDEFKxYmDcEtSz";
        public final static String TWIT_SECRET = "uJgqkWrrBtD9r0Whod2ya25y8b0NAnZgAUYA6BTkmPoYcgmBCg";
        public final static String TWIT_URL = "oauth://t4jsample";
        private Twitter collectTwitter;
        private Twitter tweetTwitter;
        private RequestToken collectRequestToken;
        private SharedPreferences collectPrefs;
        String tweetToSend;
        private String LOG_TAG = "TwitNiceActivity";//alter for your Activity name
        int score;
        String oaVerifier;
        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.gameover_layout);
            shareOnTwitterButton=(Button)findViewById(R.id.shareOnTwitterButton);
            retryButton=(Button)findViewById(R.id.retryButton);
            exitButton=(Button)findViewById(R.id.exitButton);
            cancelTweetButton=(Button)findViewById(R.id.cancelTweetButton);
            cancelTweetButton.setTag("cancelTweet");
            scoreTextView=(TextView)findViewById(R.id.scoreTextView);
            tweetButton=(Button)findViewById(R.id.tweetButton);
            tweetEditText=(EditText)findViewById(R.id.tweetEditText);
            tweetLayout=(LinearLayout)findViewById(R.id.tweetLayout);
            retryButton.setTag("retry");
            scoreLayout=(RelativeLayout)findViewById(R.id.scoreLayout);
            exitButton.setTag("exit");
            shareOnTwitterButton.setTag("share");
            retryButton.setOnClickListener(this);
            exitButton.setOnClickListener(this);
            shareOnTwitterButton.setOnClickListener(this);
            score=getIntent().getIntExtra("Score",0);
            scoreTextView.setText("Your Score is " + score);
            tweetButton.setTag("tweet");
            tweetButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            String buttonClicked=(String)v.getTag();
            switch (buttonClicked)
            {
                case "cancelTweet":
                    scoreLayout.setVisibility(View.VISIBLE);
                    tweetLayout.setVisibility(View.INVISIBLE);

                case "tweet":
                     tweetToSend=tweetEditText.getText().toString();
                    if(tweetToSend.length()>140)
                        tweetToSend=tweetToSend.substring(0,140);
                    new AsyncTask<Void, Void, Void>()
                    {
                        @Override
                        protected Void doInBackground(Void... params)
                        {
                            try
                            {
                                tweetTwitter.updateStatus(tweetToSend);
                            } catch (TwitterException e)
                            {
                                Log.e(LOG_TAG, "TE " + e.getMessage());
                                e.printStackTrace();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid)
                        {
                            super.onPostExecute(aVoid);
                            scoreLayout.setVisibility(View.VISIBLE);
                            tweetLayout.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "Tweet Composed", Toast.LENGTH_LONG);
                        }
                    }.execute();
                    break;
                case "retry":
                    Intent intent=new Intent(GameOverActivity.this,GameplayActivity.class);
                    startActivity(intent);
                    break;
                case "exit":Intent intent2 = new Intent(Intent.ACTION_MAIN);
                    intent2.addCategory(Intent.CATEGORY_HOME);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent2);
                    break;
                case "share":
                    collectPrefs=getSharedPreferences("collectPrefs",0);
                    if(collectPrefs.getString("user_token",null)==null)
                    {
                        collectTwitter=new TwitterFactory().getInstance();
                        collectTwitter.setOAuthConsumer(TWIT_KEY, TWIT_SECRET);
                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... params)
                            {
                                try
                                {


                                    collectRequestToken = collectTwitter.getOAuthRequestToken(TWIT_URL);
                                    String authURL = collectRequestToken.getAuthenticationURL();
                                    collectPrefs.edit().putInt("score", score).commit();
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(authURL)));
                                }
                                catch (TwitterException e) {
                                    Log.e(LOG_TAG, "TE " + e.getMessage());
                                    e.printStackTrace();
                                }
                                return null;
                            }
                        }.execute();
                            //get authentication request token
                    }
                    else
                    {
                        tweetSetup();
                    }
                    break;
            }
        }
        @Override
        protected void onNewIntent(Intent intent)
        {
            super.onNewIntent(intent);
            score=getSharedPreferences("collectPrefs",0).getInt("score",0);
            scoreTextView.setText("Your Score is " + score);
            //get the retrieved data
            Uri twitURI = intent.getData();
            //make sure the url is correct
            if(twitURI!=null && twitURI.toString().startsWith(TWIT_URL))
            {
                //is verifcation - get the returned data
                oaVerifier = twitURI.getQueryParameter("oauth_verifier");
                new AsyncTask<Void,Void,Void>()
                {

                    @Override
                    protected Void doInBackground(Void... params)
                    {

                        try
                        {
                            //try to get an access token using the returned data from the verification page
                            AccessToken accToken = collectTwitter.getOAuthAccessToken(collectRequestToken, oaVerifier);

                            //add the token and secret to shared prefs for future reference
                            collectPrefs.edit()
                                    .putString("user_token", accToken.getToken())
                                    .putString("user_secret", accToken.getTokenSecret())
                                    .commit();
                            //display the timeline
                            //setupTimeline();
                        }
                        catch (TwitterException te)
                        {
                            Log.e(LOG_TAG, "Failed to get access token: " + te.getMessage());
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid)
                    {
                        super.onPostExecute(aVoid);
                        tweetSetup();
                    }
                }.execute();
            }

        }
        private void tweetSetup()
        {
            collectPrefs=getSharedPreferences("collectPrefs",0);
            String userToken=collectPrefs.getString("user_token",null);
            String userSecret=collectPrefs.getString("user_secret",null);
            Configuration twitConf = new ConfigurationBuilder()
                    .setOAuthConsumerKey(TWIT_KEY)
                    .setOAuthConsumerSecret(TWIT_SECRET)
                    .setOAuthAccessToken(userToken)
                    .setOAuthAccessTokenSecret(userSecret)
                    .build();
            tweetTwitter=new TwitterFactory(twitConf).getInstance();
            tweetEditText.setText("Wow i've scored " + score + " in this wonderful game, get it from here:https://twitter.com/Yasser7ossam");
            tweetLayout.setVisibility(View.VISIBLE);
            scoreLayout.setVisibility(View.INVISIBLE);
        }
    }
