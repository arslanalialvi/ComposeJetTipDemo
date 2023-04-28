package com.example.composejettipdemo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composejettipdemo.component.CustomTextField
import com.example.composejettipdemo.ui.theme.ComposeJetTipDemoTheme
import com.example.composejettipdemo.util.calculateTip
import com.example.composejettipdemo.util.calculateTipPerson
import com.example.composejettipdemo.widgets.RoundCornerIcon

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeJetTipDemoTheme {
                MyApp {
                  //  TopHeader(134.0)
                    MainContent()
                }
            }


        }
    }
}

@Composable
fun MyApp(content:@Composable ()->Unit){
    // A surface container using the 'background' color from the theme
    Surface(
      //  modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        content()
    }


}

@Composable
fun TopHeader(totalPerPerson: Double =134.0 ){
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .padding(15.dp)
        //.clip(shape = RoundedCornerShape(corner = CornerSize(12.dp)))
        .clip(shape = CircleShape.copy(all = CornerSize(12.dp))),
        color = Color(0xFFE9D7F7)

    ) {
        Column(modifier = Modifier.padding(all = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            val total= "%.2f".format(totalPerPerson)
            Text(text = "Total Per Person", style = MaterialTheme.typography.h5)
            Text(text = "$$total", style = MaterialTheme.typography.h4, fontWeight = FontWeight.Bold)


        }
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun MainContent(){
    val totalBillState= remember {
        mutableStateOf("")
    }
    val validState= remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }
    val sliderPositionState= remember {
        mutableStateOf(0f)
    }
    val totalTipState= remember {
        mutableStateOf(0.0)
    }
    var splitBy= remember {
        mutableStateOf(1)
    }
    val totalPerPerson= remember {
        mutableStateOf(0.0)
    }
    val range= IntRange(start = 1, endInclusive = 100)
   BillForm(modifier = Modifier,validState= validState,sliderPositionState=sliderPositionState,totalTipState=totalTipState,splitBy=splitBy,totalPerPerson=totalPerPerson,range=range, totalBillState = totalBillState){
       billAmount->
       Log.d("Compose_val",billAmount)

   }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(modifier: Modifier= Modifier,totalBillState: MutableState<String>, validState: Boolean,totalTipState: MutableState<Double>,splitBy: MutableState<Int> ,sliderPositionState : MutableState<Float>,totalPerPerson: MutableState<Double>,range: IntRange= 1..100,onValChanged:(String)->Unit){

    val tipPercentageValue= (sliderPositionState.value*100).toInt()
    val keyboardController= LocalSoftwareKeyboardController.current
    Column {
        TopHeader(totalPerPerson.value)

        Surface(
            modifier = modifier
                .padding(6.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(corner = CornerSize(12.dp)),
            border = BorderStroke(width = 1.dp, color = Color.LightGray)
        ) {
            Column(
                modifier = modifier.padding(all = 6.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                CustomTextField(valueState = totalBillState, labelId = "Enter Bill",
                    enabled = true, isSingleLine = true,
                    onActions = KeyboardActions {
                        if (!validState) return@KeyboardActions
                        onValChanged(totalBillState.value)
                        keyboardController?.hide()
                    }
                )
                if (validState) {
                    Row(horizontalArrangement = Arrangement.Start) {
                        Text(
                            text = "Split",
                            modifier = modifier.align(alignment = Alignment.CenterVertically)
                        )
                        Spacer(modifier = modifier.width(120.dp))
                        Row(
                            modifier = modifier.padding(horizontal = 3.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            RoundCornerIcon(imageVector = Icons.Default.Remove, onClick = {
                                splitBy.value=if (splitBy.value>1) splitBy.value-1 else 1
                                totalPerPerson.value= calculateTipPerson(totalBill = totalBillState.value.toDouble(),tipPercentageValue=tipPercentageValue, splitBy=splitBy.value)
                            })
                            Text(
                                text = "${splitBy.value}", modifier = modifier
                                    .align(alignment = Alignment.CenterVertically)
                                    .padding(start = 9.dp, end = 9.dp)
                            )
                            RoundCornerIcon(imageVector = Icons.Default.Add, onClick = {
                                 if (splitBy.value<range.last)splitBy.value= splitBy.value+1
                                totalPerPerson.value= calculateTipPerson(totalBill = totalBillState.value.toDouble(),tipPercentageValue=tipPercentageValue, splitBy=splitBy.value)

                            })

                        }
                    }
                    Row(modifier = modifier.padding(vertical = 12.dp, horizontal = 3.dp)) {
                        Text(
                            text = "Tip",
                            modifier = modifier.align(alignment = Alignment.CenterVertically)
                        )
                        Spacer(modifier.width(200.dp))
                        Text(text = "$ ${totalTipState.value}")
                    }
                    Column(
                        modifier = modifier.padding(5.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "$tipPercentageValue %")
                        Spacer(modifier = modifier.padding(vertical = 14.dp))
                        Slider(
                            value = sliderPositionState.value, onValueChange = { newVal ->
                                sliderPositionState.value = newVal
                                totalTipState.value= calculateTip(totalBillState=totalBillState.value.toDouble(),tipPercentageValue=tipPercentageValue)
                                totalPerPerson.value= calculateTipPerson(totalBill = totalBillState.value.toDouble(),tipPercentageValue=tipPercentageValue, splitBy=splitBy.value)
                            },
                            modifier = modifier.padding(start = 16.dp, end = 16.dp), steps = 5
                        )

                    }
                } else {
                    Box() {

                    }
                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeJetTipDemoTheme {
    }
}