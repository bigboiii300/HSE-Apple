package hseapp.app.data.di

import hseapp.app.data.retrofit.AuthService
import hseapp.app.data.retrofit.CoursesService
import hseapp.app.data.retrofit.ParticipantService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("http://109.68.214.48:8080/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }
    single { get<Retrofit>().create(AuthService::class.java) }
    single { get<Retrofit>().create(ParticipantService::class.java) }
    single { get<Retrofit>().create(CoursesService::class.java) }
}