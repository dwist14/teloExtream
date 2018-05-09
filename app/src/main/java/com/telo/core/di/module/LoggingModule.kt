package com.telo.core.di.module


import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LoggingModule {

//    @Provides
//    @Singleton
//    fun provideLogger(crash: Crashlytics): Logger = Logger.apply {
//        if (BuildConfig.DEBUG) {
//            add(AndroidLogger)
//        } else {
//            add(CrashlyticsLogger(crash))
//        }
//    }

//    @Provides
//    @Singleton
//    fun provideIssueReporter(logger: Logger, crash: Crashlytics): IssueReporter =
//            CrashlyticsIssueReporter(logger, crash)
}