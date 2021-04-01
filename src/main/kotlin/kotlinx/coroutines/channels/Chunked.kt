package kotlinx.coroutines.channels

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withTimeoutOrNull

/**
 * Takes size items from channel if available. Waits for it at most timeout millis.
 * If during this timeout
 * @param result result collection to load items from channel
 * @param size max items to load to collection
 * @param timeout max millis to wait to load all requested items
 * @return result
 */
suspend fun <T, C: MutableCollection<in T>> ReceiveChannel<T>.receiveTo(result: C, size: Int, timeout: Long) : C {
    // try to poll already available in channel
    var leftToTake = size
    while (leftToTake > 0) {
        poll()?.also {
            result.add(it)
            leftToTake--
        } ?: break
    }

    if (leftToTake > 0) {
        withTimeoutOrNull(timeout) {
            receiveAsFlow().take(leftToTake).toCollection(result)
        }
    }
    return result
}

/**
 * Adds receiver flow to channel that emits chunked data
 */
fun <T> ReceiveChannel<T>.asChunkedFlow(chunkSize: Int, timeout: Long) : Flow<List<T>> {
    val channel = this
    return flow {
        for (item in channel) {
            val buffer = receiveTo(mutableListOf(item), chunkSize-1, timeout)
            emit(buffer)
        }
    }
}
