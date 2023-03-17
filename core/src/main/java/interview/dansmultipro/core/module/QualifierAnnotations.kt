package interview.dansmultipro.core.module

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HeaderInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ChuckInterceptor