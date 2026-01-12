package com.quickthought.cryptotrack

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

class CustomTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        // Specify the generated Hilt test application class name
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}

