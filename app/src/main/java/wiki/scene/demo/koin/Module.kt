package wiki.scene.demo.koin

import org.koin.dsl.module
import wiki.scene.demo.adapter.RecyclerViewAdapter

//module 类似dagger2中的 @Module
//single 单例模式
//factory 每次创建新的实例
//get()

/**
 * // 更多方式
// non lazy
val firstPresenter: MySimplePresenter = get()

// lazy inject
private val homePresenter by inject<HomePresenter>()
// non lazy
private val homePresenter = get<HomePresenter>()
 */

val appModule = module {
    factory {
        RecyclerViewAdapter()
    }
}

//interface HelloRepository {
//    fun giveHello(): String
//}
//
//class HelloRepositoryImpl() : HelloRepository {
//    override fun giveHello() = "Hello Koin"
//}
//
//class MyViewModel(val repo : HelloRepository) : ViewModel() {
//    fun sayHello() = "${repo.giveHello()} from $this"
//}

//val appModule = module {
//
//    // single instance of HelloRepository
//    single<HelloRepository> { HelloRepositoryImpl() }
//
//    // MyViewModel ViewModel
//    viewModel { MyViewModel(get()) }
//}

//val firstPresenter:MySimplePresenter by inject()
//// 更多方式
//// non lazy
//val firstPresenter: MySimplePresenter = get()
//
//// lazy inject
//private val homePresenter by inject<HomePresenter>()
//// non lazy
//private val homePresenter = get<HomePresenter>()