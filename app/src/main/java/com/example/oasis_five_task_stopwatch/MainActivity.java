package com.example.oasis_five_task_stopwatch;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private TextView hours,minutes,seconds,timeEpliced;
    private ImageButton play,pause,stop;

    private int hour ,min , second;

    private boolean isRunning = false;
    private List<String> elapsedTimes = new ArrayList<>();

    private Handler handler = new Handler();
    private Runnable runnable;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        hours = findViewById(R.id.hour);
        minutes = findViewById(R.id.minutes);
        seconds = findViewById(R.id.seconds);
        timeEpliced = findViewById(R.id.timeEpliced);

        play = findViewById(R.id.startbtn);
        pause = findViewById(R.id.pausebtn);
        stop = findViewById(R.id.stopbtn);

        hour = 00;
        min = 00;
        second = 00;
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTimer();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PauseTimer();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StopTimer();
            }
        });
        runnable = new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    incrementTime();
                    updateDisplay();
                    handler.postDelayed(this, 100);
                }
            }
        };

    }

    private void StartTimer(){
        if (!isRunning) {
            isRunning = true;
            handler.post(runnable);
        }
    }

    private void PauseTimer(){
        isRunning = false;

        String epliceTime = String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", second);
        elapsedTimes.add(epliceTime);
        updateElapsedTimesDisplay();


    }

    private void StopTimer(){
        isRunning = false;
        hour = 0;
        min = 0;
        second = 0;
        updateDisplay();
    }
    private void incrementTime() {
        second++;
        if (second == 60) {
            second = 0;
            min++;
        }
        if (min == 60) {
            min = 0;
            hour++;
        }
    }

    @SuppressLint("DefaultLocale")
    private void updateDisplay() {
        hours.setText(String.format("%02d", hour));
        minutes.setText(String.format("%02d", min));
        seconds.setText(String.format("%02d", second));
    }

    private void updateElapsedTimesDisplay() {
        StringBuilder elapsedTimesText = new StringBuilder();
        for (String time : elapsedTimes) {
            elapsedTimesText.append(time).append("\n");
        }
        timeEpliced.setText(elapsedTimesText.toString());
    }
}