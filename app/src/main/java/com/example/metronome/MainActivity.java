package com.example.metronome;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButtonToggleGroup;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView value;
    private ImageButton plus;
    private ImageButton minus;
    private MaterialButtonToggleGroup materialButtonToggleGroup;
    private Button button;
    private MediaPlayer click;
    private Handler handler = new Handler();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = findViewById(R.id.seekbar);
        value = findViewById(R.id.value);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        materialButtonToggleGroup = findViewById(R.id.materialButtonToggleGroup);
        materialButtonToggleGroup.setSingleSelection(true);
        button = findViewById(R.id.button);
        click = MediaPlayer.create(getApplicationContext(), R.raw.sample);

        seekBar.setMin(40);
        seekBar.setMax(200);
        seekBar.setProgress(Integer.parseInt((String) value.getText()));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                value.setText(""+ i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((Integer.parseInt((String) value.getText())) < 200) {
                    value.setText((Integer.parseInt((String) value.getText()) + 5) + "");
                    seekBar.setProgress(Integer.parseInt((String) value.getText()));
                }
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((Integer.parseInt((String) value.getText())) > 40) {
                    value.setText((Integer.parseInt((String) value.getText()) - 5) + "");
                    seekBar.setProgress(Integer.parseInt((String) value.getText()));
                }
            }
        });

        materialButtonToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    if (checkedId == R.id.button) {
                        button.setText("Pause");
                        handler.post(run);
                    }
                }
                else {
                    button.setText("Play");
                    handler.removeCallbacks(run);
                }
            }
        });

    }
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            click.start();
            int delay = 60000 / Integer.parseInt((String) value.getText());
            handler.postDelayed(this, delay);
        }
    };


}