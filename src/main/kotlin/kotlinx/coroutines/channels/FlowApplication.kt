package kotlinx.coroutines.channels

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
			println("1: processing $items")
			delay(100)
		}
	}
	launch {
		channel.asChunkedFlow(4, 130).collect { items ->
			println("2: processing $items")
			delay(100)
		}
	}
	launch {
		channel.asChunkedFlow(4, 130).collect { items ->
			println("3: processing $items")
			delay(100)
		}
	}
}
