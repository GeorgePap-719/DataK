package datak

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Test
import java.util.concurrent.atomic.AtomicInteger

/*
 * AtomicSet should be able to synchronize access to shared mutable state properly, for more
 * see https://kotlinlang.org/docs/shared-mutable-state-and-concurrency.html.
 */
class TestAtomicSet {

    @Test
    fun `should be able to modify set`(): Unit = runBlocking {
        val set = AtomicSet(0) // initial set
        val atomicInt = AtomicInteger(0)
        withContext(Dispatchers.Default) {
            massiveRun {
                synchronized(set) { // synchronized is required
                    set.addSynchronized(atomicInt.incrementAndGet())
                }
            }
        }
        println("result:$set")
    }

    @Test
    fun `test atomicInteger`(): Unit = runBlocking {
        val atomicInteger = AtomicInteger(0)
        withContext(Dispatchers.Default) {
            massiveRun {
                atomicInteger.getAndIncrement()
            }
        }
        println("result:${atomicInteger.get()}")
    }
}