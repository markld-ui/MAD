package ru.startandoid.p003thirdproject;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Animation hourAnim, minuteAnim, secondAnim;
    private ImageView hourHand, minuteHand, secondHand;
    private Button btnStart, btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация элементов
        hourHand = findViewById(R.id.hour_hand);
        minuteHand = findViewById(R.id.minute_hand);
        secondHand = findViewById(R.id.second_hand);
        btnStart = findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_stop);

        // Установка точки вращения
        setPivot(hourHand, 0.5f, 0.9f);
        setPivot(minuteHand, 0.5f, 0.9f);
        setPivot(secondHand, 0.5f, 0.9f);

        // Загрузка анимаций
        hourAnim = AnimationUtils.loadAnimation(this, R.anim.hour_rotation);
        minuteAnim = AnimationUtils.loadAnimation(this, R.anim.minute_rotation);
        secondAnim = AnimationUtils.loadAnimation(this, R.anim.second_rotation);

        // Обработчики кнопок
        btnStart.setOnClickListener(v -> startClockAnimation());
        btnStop.setOnClickListener(v -> stopClockAnimation());
    }

    private void setPivot(ImageView view, float pivotX, float pivotY) {
        view.setPivotX(view.getWidth() * pivotX);
        view.setPivotY(view.getHeight() * pivotY);
    }

    private void startClockAnimation() {
        hourHand.startAnimation(hourAnim);
        minuteHand.startAnimation(minuteAnim);
        secondHand.startAnimation(secondAnim);
    }

    private void stopClockAnimation() {
        hourHand.clearAnimation();
        minuteHand.clearAnimation();
        secondHand.clearAnimation();
    }
}