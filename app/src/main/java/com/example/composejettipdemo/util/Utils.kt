package com.example.composejettipdemo.util

fun calculateTip(totalBillState: Double, tipPercentageValue: Int): Double {
   return if (totalBillState>1 && totalBillState.toString().isNotEmpty()){
       (totalBillState*tipPercentageValue)/100
    }else 0.0

}
fun calculateTipPerson(totalBill: Double, tipPercentageValue: Int, splitBy: Int): Double{
    val bill= calculateTip(totalBillState = totalBill, tipPercentageValue=tipPercentageValue)+totalBill;
    return (bill/splitBy)
}