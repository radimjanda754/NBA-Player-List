package net.rjanda.casestudy.nba.di

import android.content.Context
import com.squareup.moshi.Moshi
import net.rjanda.casestudy.nba.network.AuthInterceptor
import net.rjanda.casestudy.nba.player.repository.InMemoryPlayerStorage
import net.rjanda.casestudy.nba.player.repository.LocalPlayerRepository
import net.rjanda.casestudy.nba.player.repository.PlayerApi
import net.rjanda.casestudy.nba.player.repository.PlayerRepository
import net.rjanda.casestudy.nba.player.repository.PlayerStorage
import net.rjanda.casestudy.nba.player.screen.detail.PlayerDetailViewModel
import net.rjanda.casestudy.nba.player.screen.list.PlayerListViewModel
import net.rjanda.casestudy.nba.player.screen.team.PlayerTeamViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val dataModule = module {
    single<OkHttpClient> {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(logging)
            .build()
    }
    single<Moshi> { Moshi.Builder().build() }
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }
    single<PlayerApi> { get<Retrofit>().create(PlayerApi::class.java) }
    single<PlayerStorage> { InMemoryPlayerStorage() }
    single<PlayerRepository> {
        LocalPlayerRepository(get(), get())
    }
}

val viewModelModule = module {
    viewModelOf(::PlayerListViewModel)
    viewModel { (itemId: Int) -> PlayerDetailViewModel(itemId, get()) }
    viewModel { (itemId: Int) -> PlayerTeamViewModel(itemId, get()) }
}

val koinModules = listOf(
    dataModule,
    viewModelModule,
)

fun initKoin(context: Context) {
    startKoin {
        modules(
            module {
                single { context }
            } + koinModules
        )
    }
}

const val BASE_API_URL = "https://api.balldontlie.io/v1/"