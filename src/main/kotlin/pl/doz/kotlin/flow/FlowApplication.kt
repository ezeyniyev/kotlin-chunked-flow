package pl.doz.kotlin.flow

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() = runBlocking<Unit> {
	val channel = Channel<Int>(100)
	launch {
		for (i in 1..1000) {
			println("${Thread.currentThread().name}: sends $i")
			channel.send(i)
			delay(7)
		}
		channel.close()
		println("finished sending...")
	}

	launch {
		channel.asChunkedFlow(4, 130).collect { items ->
			println("${Thread.currentThread().name}: processing $items")
			delay(100)
		}
	}
}
