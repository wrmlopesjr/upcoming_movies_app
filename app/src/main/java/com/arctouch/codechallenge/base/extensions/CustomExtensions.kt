import androidx.lifecycle.MutableLiveData
import com.arctouch.codechallenge.base.NetworkState

fun <T> MutableLiveData<MutableList<T>>.addListValues(values: List<T>) {
    val value = this.value ?: mutableListOf()
    value.addAll(values)
    this.postValue(value)
}

fun <T> MutableLiveData<MutableList<T>>.clear() {
    this.postValue(mutableListOf())
}

fun MutableLiveData<NetworkState>.running() {
    this.postValue(NetworkState.RUNNING)
}

fun MutableLiveData<NetworkState>.success() {
    this.postValue(NetworkState.SUCCESS)
}

fun MutableLiveData<NetworkState>.error() {
    this.postValue(NetworkState.ERROR)
}

fun MutableLiveData<NetworkState>.empty() {
    this.postValue(NetworkState.EMPTY)
}