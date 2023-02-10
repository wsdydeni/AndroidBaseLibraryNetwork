package wsdydeni.library.android_network.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

suspend fun <T> associatedView(
  request: suspend () -> BaseResponse<T>,
  onRequestBefore: (suspend () -> Unit)? = null,
  onRequestSuccess: (suspend () -> Unit)? = null,
  onRequestError: (suspend (errorMsg: String, errorCode: String) -> Unit)? = null
): Flow<T> {
  return flow {
    if (onRequestBefore != null) {
      onRequestBefore()
    }
    executeResponse(request()).suspendOnSuccess {
      if (onRequestSuccess != null) {
        onRequestSuccess()
      }
      emit(data)
    }.suspendOnFailure {
      if (onRequestError != null) {
        onRequestError(errorMsg, errorCode)
      }
    }
  }.catch {
    if (onRequestError != null) {
      onRequestError(it.message ?: "网络请求异常", "")
    }
  }
}