package datak

import java.util.concurrent.atomic.AtomicReference

/**
 * Atomic set with lock-free reads and atomic modifications.
 */
class AtomicSet<T>(vararg elements: T) {
    private val set = AtomicReference(elements.toMutableSet())

    val size get() = set.get().size

    fun find(predicate: (T) -> Boolean): T? {
        val set = set.get()
        for (element in set) if (predicate(element)) return element
        return null
    }

    fun add(element: T) {
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
