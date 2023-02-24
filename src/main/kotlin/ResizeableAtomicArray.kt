package datak

import java.util.concurrent.atomic.AtomicReferenceArray

/**
 * Atomic array with lock-free reads and synchronized modifications.
 *
 * Note: implementation is practically copy-pasted from `kotlinx.coroutines.internal` package.
 * It is used only for testing purposes.
 */
internal class ResizableAtomicArray<T>(initialLength: Int) {
    @Volatile
    private var array = AtomicReferenceArray<T>(initialLength)

    // for debug output
    public fun currentLength(): Int = array.length()

    public operator fun get(index: Int): T? {
        val array = this.array // volatile read
        return if (index < array.length()) array[index] else null
    }

    // Must not be called concurrently, e.g. always use synchronized(this) to call this function
    fun setSynchronized(index: Int, value: T?) {
        val curArray = this.array
        val curLen = curArray.length()
        if (index < curLen) {
            curArray[index] = value
        } else {
            val newArray = AtomicReferenceArray<T>((index + 1).coerceAtLeast(2 * curLen))
            for (i in 0 until curLen) newArray[i] = curArray[i]
            newArray[index] = value
            array = newArray // copy done
        }
    }
}