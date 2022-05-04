package datak

import kotlin.reflect.KClass

abstract class ParameterizedTypeReference<T : Any> {

    init {
        val kClass: KClass<T>
    }

//    private val type: Any


}