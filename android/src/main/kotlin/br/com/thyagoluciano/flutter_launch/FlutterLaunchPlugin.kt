package br.com.thyagoluciano.flutter_launch

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import java.net.URLEncoder


/** FlutterLaunchPlugin */
class FlutterLaunchPlugin: FlutterPlugin, MethodCallHandler {
  private lateinit var channel : MethodChannel
  private lateinit var context: Context

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "br.com.thyagoluciano/flutter_launch")
    channel.setMethodCallHandler(this)
    context = flutterPluginBinding.applicationContext

  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    try {
      val pm: PackageManager = context.packageManager

      if (call.method == "launchMessenger") {

        val id: String? = call.argument("id")

        val url: String = "https://m.me/$id"

        if (appInstalledOrNot("com.facebook.orca")) {
          val intent: Intent = Intent(Intent.ACTION_VIEW)
          intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
          intent.setPackage("com.facebook.orca")
          intent.data = Uri.parse(url)

          if (intent.resolveActivity(pm) != null) {
            context.startActivity(intent)
          }
        }
      }

      if (call.method == "hasApp") {
        val app: String? = call.argument("name")

        when(app) {
          "whatsapp" -> result.success(appInstalledOrNot("com.facebook.orca"))
          else -> {
            result.error("App not found", "", null)
          }
        }
      }
    } catch (e: PackageManager.NameNotFoundException) {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  private fun appInstalledOrNot(uri: String) : Boolean {
    val pm: PackageManager = context.packageManager

    var appInstalled: Boolean = try {
      pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
      true
    } catch (e: PackageManager.NameNotFoundException) {
      false
    }

    return appInstalled
  }
}
