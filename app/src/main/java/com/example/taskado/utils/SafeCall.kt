package com.example.taskado.utlis

import il.co.syntax.finalkotlinproject.utils.Resource

inline fun <T> safeCall(action: () -> Resource<T>): Resource<T>{
    return try{
        action()
    }catch (e:Exception){
        Resource.error(e.message ?: "An unknown error occurred")
    }
}