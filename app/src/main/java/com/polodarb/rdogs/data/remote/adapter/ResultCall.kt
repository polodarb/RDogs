package com.polodarb.rdogs.data.remote.adapter

import com.polodarb.rdogs.data.remote.result.NetworkResult
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import com.polodarb.rdogs.data.remote.util.Result
import retrofit2.Response

internal class ResultCall<T>(proxy: Call<T>) : CallDelegate<T, NetworkResult<T>>(proxy) {

    override fun enqueueImpl(callback: Callback<NetworkResult<T>>) {
        proxy.enqueue(ResultCallback(this, callback))
    }

    override fun cloneImpl(): ResultCall<T> {
        return ResultCall(proxy.clone())
    }

    private class ResultCallback<T>(
        private val proxy: ResultCall<T>,
        private val callback: Callback<NetworkResult<T>>
    ) : Callback<T> {

        override fun onResponse(call: Call<T>, response: Response<T>) {
            val result: NetworkResult<T> = if (response.isSuccessful) {
                Result.Success(success = response.body() as T)
            } else {
                Result.Failure(cause = HttpException(response))
            }
            callback.onResponse(proxy, Response.success(result))
        }

        override fun onFailure(call: Call<T>, error: Throwable) {
//            val result = error as? Exception ?: Exception(cause = error)
            val result = error as? Exception ?: Exception(error)
            callback.onResponse(proxy, Response.success(Result.Failure(result)))
        }
    }

    override fun timeout(): Timeout {
        return proxy.timeout()
    }

}