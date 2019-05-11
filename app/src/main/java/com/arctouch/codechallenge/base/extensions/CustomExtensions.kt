import androidx.lifecycle.MutableLiveData
import com.arctouch.codechallenge.base.NetworkState

//add all the values from the list to the LiveData
fun <T> MutableLiveData<ArrayList<T>>.addListValues(values: List<T>) {
    val value = this.value ?: arrayListOf()
    value.addAll(values)
    this.postValue(value)
}

//clear the LiveData
fun <T> MutableLiveData<ArrayList<T>>.clear() {
    this.postValue(null)
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