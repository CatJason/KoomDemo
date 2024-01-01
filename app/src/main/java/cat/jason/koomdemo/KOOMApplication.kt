package cat.jason.koomdemo

import android.app.Application
import android.os.Build
import com.kwai.koom.base.CommonConfig
import com.kwai.koom.base.DefaultInitTask
import com.kwai.koom.base.MonitorManager

class KOOMApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val config = CommonConfig.Builder()
            .setApplication(this) // Set application
            .setVersionNameInvoker { "1.0.0" } // Set version name, java leak feature use it
            .setSdkVersionMatch(
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
            )  // Set if current sdk version is supported
            .build()

        MonitorManager.initCommonConfig(config)
            .apply { onApplicationCreate() }
    }
}