package com.nplusone.m4.datalayer

// Remove the answer from the list
fun <T> MutableList<T>.purge(ans:T):List<T>  {
    this.removeIf { it == ans }
    return this
}

fun Int.units():Int{
    return this%10
}

fun Int.tens():Int{
    return (this/10)%10
}

fun Int.hund():Int{
    return (this/100)%10
}
fun <T> copyElements(source:List<T>,
                     target:MutableList<T>){
    for(item in source){
        target.add(item)
    }
}
// Return Marathi translation of spoofs
fun <T> List<Int>.toMr():List<String>{
    var targetIntermediate = mutableListOf<Int>()
    copyElements(this,targetIntermediate)
    return targetIntermediate.map { Util.gimmeMr(it) }.toList()
}

// Extension function for list
//fun MutableList<Int>.purge(ans:Int):List<Int>  {
//    this.removeIf { it == ans }
//    return this
//}