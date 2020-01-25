package me.daehokimm

data class Statistic(
    var lowerCount: Long = 0,
    var maxCount: Long = 0,
    var minCount: Long = Long.MAX_VALUE
) {
    fun calculate(count: Long) {
        when {
            maxCount < count -> maxCount = count
            minCount > count -> minCount = count
        }
    }
}