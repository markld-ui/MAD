package ru.startandroid.p004fourthproject;

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
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private EditText inputWeight;
    private Spinner spinnerWeightFrom;
    private Spinner spinnerWeightTo;
    private Button buttonCalculate;
    private Button buttonSave;
    private Button buttonLoad;
    private static final String FILE_NAME = "conversion_history.txt";

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

        inputWeight = findViewById(R.id.InputWeight);
        spinnerWeightFrom = findViewById(R.id.spinnerWeightFrom);
        spinnerWeightTo = findViewById(R.id.spinnerWeightTo);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonSave = findViewById(R.id.buttonSave);
        buttonLoad = findViewById(R.id.buttonLoad);

        // Настройка адаптеров для Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.weight_units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWeightFrom.setAdapter(adapter);
        spinnerWeightTo.setAdapter(adapter);

        // Обработка нажатия кнопки "Посчитать"
        buttonCalculate.setOnClickListener(this::showDialog);

        // Обработка нажатия кнопки "Сохранить"
        buttonSave.setOnClickListener(v -> saveData());

        // Обработка нажатия кнопки "Загрузить"
        buttonLoad.setOnClickListener(v -> loadData());
    }

    public void showDialog(View v) {
        // Создание и отображение диалогового окна с результатом
        MyDialogFragment dataDialog = MyDialogFragment.newInstance(calculateData());
        dataDialog.show(getSupportFragmentManager(), "custom");
    }

    private String calculateData() {
        String weightStr = inputWeight.getText().toString();
        if (weightStr.isEmpty()) {
            return "Вы ничего не ввели!";
        }

        weightStr = weightStr.replace(',', '.');

        try {
            double weight = Double.parseDouble(weightStr);
            String fromUnit = spinnerWeightFrom.getSelectedItem().toString();
            String toUnit = spinnerWeightTo.getSelectedItem().toString();
            double convertedWeight = 0;

            // Конвертация веса
            if (fromUnit.equals("Кг") && toUnit.equals("Фунты")) {
                convertedWeight = weight * 2.20462;
            } else if (fromUnit.equals("Фунты") && toUnit.equals("Кг")) {
                convertedWeight = weight / 2.20462;
            } else {
                convertedWeight = weight;
            }

            return "Результат преобразования " + weightStr + " " + fromUnit + " в " + toUnit
                    + " = " + String.format("%.2f", convertedWeight) + " " + toUnit;
        } catch (NumberFormatException e) {
            return "Ошибка ввода числа!";
        }
    }

    private void saveData() {
        String data = inputWeight.getText().toString() + ";" +
                spinnerWeightFrom.getSelectedItemPosition() + ";" +
                spinnerWeightTo.getSelectedItemPosition();

        try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE)) {
            fos.write(data.getBytes());
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Ошибка сохранения: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void loadData() {
        try (FileInputStream fis = openFileInput(FILE_NAME);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {

            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    inputWeight.setText(parts[0]);
                    spinnerWeightFrom.setSelection(Integer.parseInt(parts[1]));
                    spinnerWeightTo.setSelection(Integer.parseInt(parts[2]));
                    Toast.makeText(this, "Данные загружены", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (IOException e) {
            Toast.makeText(this, "Ошибка загрузки: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}