package datak

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

/*
 * AtomicSet should be able to synchronize access to shared mutable state properly, for more
 * see https://kotlinlang.org/docs/shared-mutable-state-and-concurrency.html.
 */
class TestAtomicSet {

    /*
     * Passes, but the time it takes to complete is noticeable slower.
     */
    @Test
    fun `should be able to modify set`(): Unit = runBlocking {
        val set = AtomicSet(0) // initial set
        withContext(Dispatchers.Default) {
            massiveRun {
                synchronized(set) { // synchronized is required
                    var item = set.last()
                    set.add(++item)
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

    suspend fun massiveRun(action: suspend () -> Unit) {
        val n = 100  // number of coroutines to launch
        val k = 1000 // times an action is repeated by each coroutine
        val time = measureTimeMillis {
            coroutineScope { // scope for coroutines
                repeat(n) {
                    launch {
                        repeat(k) { action() }
                    }
                }
            }
        }
        println("Completed ${n * k} actions in $time ms")
    }
}