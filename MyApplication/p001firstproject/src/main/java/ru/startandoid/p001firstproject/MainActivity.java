package ru.startandoid.p001firstproject;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;


public class MainActivity extends AppCompatActivity
{
    private EditText inputWeight;
    private Spinner spinnerWeightFrom;
    private Spinner spinnerWeightTo;
    private Button buttonCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inputWeight = findViewById(R.id.InputWeight);
        spinnerWeightFrom = findViewById(R.id.spinnerWeightFrom);
        spinnerWeightTo = findViewById(R.id.spinnerWeightTo);
        buttonCalculate = findViewById(R.id.buttonCalculate);

        // Настройка адаптеров для Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.weight_units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWeightFrom.setAdapter(adapter);
        spinnerWeightTo.setAdapter(adapter);

        // Обработка нажатия кнопки "Посчитать"
        buttonCalculate.setOnClickListener(this::showDialog);
    }

    public void showDialog(View v)
    {
        // Создание и отображение диалогового окна с результатом
        MyDialogFragment dataDialog = MyDialogFragment.newInstance(calculateData());
        dataDialog.show(getSupportFragmentManager(), "custom");
    }

    private String calculateData()
    {
        String weightStr = inputWeight.getText().toString();
        if (weightStr.isEmpty())
        {
            // Обработка пустого ввода
            return "Вы ничего не ввели!";
        }

        weightStr = weightStr.replace(',', '.');

        double weight = Double.parseDouble(weightStr);
        String fromUnit = spinnerWeightFrom.getSelectedItem().toString();
        String toUnit = spinnerWeightTo.getSelectedItem().toString();
        double convertedWeight = 0;

        // Конвертация веса
        if (fromUnit.equals("Кг") && toUnit.equals("Фунты"))
        {
            convertedWeight = weight * 2.20462; // КГ в фунты
        } else if (fromUnit.equals("Фунты") && toUnit.equals("Кг"))
        {
            convertedWeight = weight / 2.20462; // Фунты в КГ
        } else
        {
            // Если выбран одинаковый тип, просто возвращаем исходный вес
            convertedWeight = weight;
        }

        return "Результат преобразования " + weightStr + " " + fromUnit + " в " + toUnit
                + " = " + convertedWeight + " " + toUnit;
    }
}