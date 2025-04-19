package com.example.logbook1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logbook1.ui.theme.Logbook1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Logbook1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LengthConverterScreen()
                }
            }
        }
    }
}

@Composable
fun LengthConverterScreen() {

    val units = listOf("Metre", "Centimetre", "Millimetre", "Kilometre", "Mile", "Foot")

    // Conversion factors to metres for each unit
    val toMetreFactors = mapOf(
        "Metre" to 1.0,
        "Centimetre" to 0.01,    
        "Millimetre" to 0.001,   
        "Kilometre" to 1000.0,   
        "Mile" to 1609.34,       
        "Foot" to 0.3048         
    )


    var lengthInput by remember { mutableStateOf("") }
    var sourceUnit by remember { mutableStateOf("Metre") }
    var targetUnit by remember { mutableStateOf("Metre") }
    var conversionResult by remember { mutableStateOf("Enter a value and select units to convert") }
    var inputError by remember { mutableStateOf<String?>(null) }
    var isSourceDropdownExpanded by remember { mutableStateOf(false) }
    var isTargetDropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Length Unit Converter",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

  
        OutlinedTextField(
            value = lengthInput,
            onValueChange = { lengthInput = it },
            label = { Text("Enter length") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            isError = inputError != null
        )

        
        inputError?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

       
        Text(
            text = "Convert from:",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable { isSourceDropdownExpanded = true }
                .padding(16.dp)
        ) {
            Text(text = sourceUnit)
            DropdownMenu(
                expanded = isSourceDropdownExpanded,
                onDismissRequest = { isSourceDropdownExpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                units.forEach { unit ->
                    DropdownMenuItem(
                        text = { Text(unit) },
                        onClick = {
                            sourceUnit = unit
                            isSourceDropdownExpanded = false
                        }
                    )
                }
            }
        }

     
        Text(
            text = "Convert to:",
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable { isTargetDropdownExpanded = true }
                .padding(16.dp)
        ) {
            Text(text = targetUnit)
            DropdownMenu(
                expanded = isTargetDropdownExpanded,
                onDismissRequest = { isTargetDropdownExpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                units.forEach { unit ->
                    DropdownMenuItem(
                        text = { Text(unit) },
                        onClick = {
                            targetUnit = unit
                            isTargetDropdownExpanded = false
                        }
                    )
                }
            }
        }

       
        Button(
            onClick = {
                val temp = sourceUnit
                sourceUnit = targetUnit
                targetUnit = temp
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Swap Units", color = MaterialTheme.colorScheme.onPrimary)
        }

      
        Button(
            onClick = {
                if (lengthInput.isEmpty()) {
                    inputError = "Input cannot be empty"
                    return@Button
                }

                val value = lengthInput.toDoubleOrNull()
                if (value == null || value < 0) {
                    inputError = "Please enter a valid positive number"
                    return@Button
                }

                inputError = null
                val convertedValue = convertLength(value, sourceUnit, targetUnit, toMetreFactors)
                val formattedValue = "%.2f".format(convertedValue)
                conversionResult = "$lengthInput $sourceUnit = $formattedValue $targetUnit"
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Calculate", color = MaterialTheme.colorScheme.onPrimary)
        }

        
        Text(
            text = conversionResult,
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

// Conversion function using a map of factors
private fun convertLength(value: Double, from: String, to: String, factors: Map<String, Double>): Double {
    val fromFactor = factors[from] ?: 1.0  
    val toFactor = factors[to] ?: 1.0     
    val valueInMetre = value * fromFactor  
    return valueInMetre / toFactor        
}
