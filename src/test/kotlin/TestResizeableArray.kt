package datak

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Test
import java.util.concurrent.atomic.AtomicInteger

class TestResizeableArray {

    @Test
    fun `should be able to modify array`(): Unit = runBlocking {
        val array = ResizableAtomicArray<Int>(1)
        val atomicInt = AtomicInteger(0)
        withContext(Dispatchers.Default) {
            massiveRun {
                synchronized(array) { // synchronized is required
                    val value = atomicInt.getAndIncrement()
                    array.setSynchronized(value, value)
                }
            }
        }
    }
}