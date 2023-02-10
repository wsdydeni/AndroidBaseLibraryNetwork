package wsdydeni.library.android_network.network


abstract class BaseResponse<T> {
    abstract fun isSuccess(): Boolean
    abstract fun getResponseData(): T
    abstract fun getResponseCode(): String
    abstract fun getResponseMsg(): String
}