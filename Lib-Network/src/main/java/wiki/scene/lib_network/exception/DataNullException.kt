package wiki.scene.lib_network.exception

class DataNullException(msg: String) : Exception() {
    override var message: String = ""

    init {
        message = msg
    }
}