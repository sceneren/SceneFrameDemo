package wiki.scene.lib_network.livedata

import androidx.lifecycle.LiveData
import retrofit2.http.POST

interface ApiLiveDataService {
    @POST
    fun post(): LiveData<String>
}