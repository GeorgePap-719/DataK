package datak

import java.util.concurrent.atomic.AtomicReference

/**
 * Atomic mutableSet with lock-free reads and atomic modifications.
 */
class AtomicSet<T>(vararg elements: T) {
    private val set = AtomicReference(elements.toMutableSet())

    override fun toString(): String {
        return set.get().toString()
    }

    val size get() = set.get().size

    fun find(predicate: (T) -> Boolean): T? {
        val set = set.get()
        for (element in set) if (predicate(element)) return element
        return null
    }

    // Must not be called concurrently, e.g. always use synchronized(this) to call this function
    fun addSynchronized(element: T) {
        set.getAndUpdate {
            it.add(element)
            it
        }
    }

    fun last(): T = set.get().last()

    fun remove(element: T): Boolean {
        var removed = false
        set.getAndUpdate {
            if (it.remove(element)) removed = true
            it
        }
        return removed
    }
}
