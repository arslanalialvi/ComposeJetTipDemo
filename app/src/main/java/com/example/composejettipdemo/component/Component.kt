package com.example.composejettipdemo.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.ImeOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


    @Composable
    fun CustomTextField(
     modifier: Modifier= Modifier,
     valueState: MutableState<String>,
     labelId: String,
     enabled: Boolean,
     isSingleLine: Boolean,
     keyboardType: KeyboardType = KeyboardType.Number,
     imeActions: ImeAction = ImeAction.Next,
     onActions: KeyboardActions = KeyboardActions.Default
    ){
        OutlinedTextField(value = valueState.value, onValueChange = {valueState.value=it},
            modifier= Modifier.padding(bottom = 10.dp, top= 10.dp, end = 10.dp).fillMaxWidth()
        , label = { Text(text = labelId)},
        leadingIcon = { Icon(imageVector = Icons.Rounded.AttachMoney, contentDescription ="Money Icon" )},
        singleLine = isSingleLine,
            textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground),
            enabled=enabled,
            keyboardOptions = KeyboardOptions(keyboardType= keyboardType, imeAction = imeActions),
            keyboardActions = onActions
        )

    }
