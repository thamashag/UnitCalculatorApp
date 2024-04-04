package com.example.quizapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Spinner sourceUnitSpinner;
    private Spinner destUnitSpinner;
    private EditText valueEditText;
    private Button convertButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        sourceUnitSpinner = findViewById(R.id.source_unit_spinner);
        destUnitSpinner = findViewById(R.id.dest_unit_spinner);
        valueEditText = findViewById(R.id.value_edit_text);
        convertButton = findViewById(R.id.convert_button);
        resultTextView = findViewById(R.id.result_text_view);

        // Populate spinners with unit options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.unit_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceUnitSpinner.setAdapter(adapter);
        destUnitSpinner.setAdapter(adapter);

        // Set OnClickListener for convertButton
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertUnits();
            }
        });
    }

    private void convertUnits() {
        // Check if value is empty
        String valueStr = valueEditText.getText().toString().trim();
        if (valueStr.isEmpty()) {
            resultTextView.setText("Please enter a value.");
            return;
        }

        // Check if value is a valid number
        double value;
        try {
            value = Double.parseDouble(valueStr);
        } catch (NumberFormatException e) {
            resultTextView.setText("Please enter a valid number.");
            return;
        }

        // Check if source and destination units are the same
        String sourceUnit = (String) sourceUnitSpinner.getSelectedItem();
        String destUnit = (String) destUnitSpinner.getSelectedItem();
        if (sourceUnit.equals(destUnit)) {
            resultTextView.setText("Source and destination units cannot be the same.");
            return;
        }

        // Call the conversion logic function
        double convertedValue = convertUnits(sourceUnit, destUnit, value);

        // Update the result text view with the converted value
        resultTextView.setText("Result: " + convertedValue);
    }


    // Define your conversion logic function here
    private double convertUnits(String sourceUnit, String destUnit, double value) {
        // Length Conversions
        if (sourceUnit.equals("inch")) {
            if (destUnit.equals("cm")) {
                return value * 2.54;
            } else if (destUnit.equals("foot")) {
                return value / 12;
            } else if (destUnit.equals("yard")) {
                return value / 36;
            } else if (destUnit.equals("mile")) {
                return value * 1.60934 * 1000;
            }
        } else if (sourceUnit.equals("foot")) {
            if (destUnit.equals("inch")) {
                return value * 12;
            } else if (destUnit.equals("cm")) {
                return value * 30.48;
            } else if (destUnit.equals("yard")) {
                return value / 3;
            } else if (destUnit.equals("mile")) {
                return value * 1.60934 * 1000 * 5280;
            }
        } else if (sourceUnit.equals("yard")) {
            if (destUnit.equals("inch")) {
                return value * 36;
            } else if (destUnit.equals("foot")) {
                return value * 3;
            } else if (destUnit.equals("cm")) {
                return value * 91.44;
            } else if (destUnit.equals("mile")) {
                return value * 1.60934 * 1000 * 1760;
            }
        } else if (sourceUnit.equals("mile")) {
            if (destUnit.equals("inch")) {
                return value / 1.60934 / 1000;
            } else if (destUnit.equals("foot")) {
                return value / 1.60934 / 1000 / 5280;
            } else if (destUnit.equals("yard")) {
                return value / 1.60934 / 1000 / 1760;
            } else if (destUnit.equals("cm")) {
                return value * 160934;
            }
        }

        // Weight Conversions
        else if (sourceUnit.equals("pound")) {
            if (destUnit.equals("kg")) {
                return value * 0.453592;
            } else if (destUnit.equals("ounce")) {
                return value * 16;
            } else if (destUnit.equals("ton")) {
                return value * 0.000453592;
            }
        } else if (sourceUnit.equals("ounce")) {
            if (destUnit.equals("pound")) {
                return value / 16;
            } else if (destUnit.equals("kg")) {
                return value * 0.0283495;
            } else if (destUnit.equals("ton")) {
                return value * 0.0000283495;
            }
        } else if (sourceUnit.equals("ton")) {
            if (destUnit.equals("pound")) {
                return value / 0.000453592;
            } else if (destUnit.equals("ounce")) {
                return value / 0.0000283495;
            } else if (destUnit.equals("kg")) {
                return value * 907.185;
            }
        }

        // Temperature Conversions
        else if (sourceUnit.equals("celsius")) {
            if (destUnit.equals("fahrenheit")) {
                return (value * 1.8) + 32;
            } else if (destUnit.equals("kelvin")) {
                return value + 273.15;
            }
        } else if (sourceUnit.equals("fahrenheit")) {
            if (destUnit.equals("celsius")) {
                return (value - 32) / 1.8;
            } else if (destUnit.equals("kelvin")) {
                return (value + 459.67) * (5 / 9);
            }
        } else if (sourceUnit.equals("kelvin")) {
            if (destUnit.equals("celsius")) {
                return value - 273.15;
            } else if (destUnit.equals("fahrenheit")) {
                return (value * 1.8) - 459.67;
            }
        }

        // If units are incompatible, return 0
        return 0;
    }

}
