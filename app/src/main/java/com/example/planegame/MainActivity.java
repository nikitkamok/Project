package com.example.planegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().getWindowInsetsController().hide(
                android.view.WindowInsets.Type.navigationBars() //спрятать меню навигации
        );

        setContentView(R.layout.activity_main);
        //объявление объектов главного меню
        Button buttonStart = (Button) findViewById(R.id.main_buttonStart);
        Button buttonAchievements = (Button) findViewById(R.id.main_buttonAchievements);
        Button buttonSettings = (Button) findViewById(R.id.main_buttonSettings);
        Button buttonStats = (Button) findViewById(R.id.main_buttonStats);

        //переход из главного меню в меню уровней
        buttonStart.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MainActivity.this, GameLevels.class);
                startActivity(intent);finish();
            } catch (Exception e) {
            }
        });

    }

    //Системная кнопка назад

    @Override
    public void onBackPressed() {

        if(backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        }
        else {
            String message = getString(R.string.exit_by_using_back);
            backToast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    //Системная кнопка назад

}