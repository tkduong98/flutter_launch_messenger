import Flutter
import UIKit

public class SwiftFlutterLaunchPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "br.com.thyagoluciano/flutter_launch", binaryMessenger: registrar.messenger())
    let instance = SwiftFlutterLaunchPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {

    if ("launchMessenger" == call.method) {
      let args = call.arguments as! Dictionary<String, String>
        let idUser = args["id"]
        let urlMessenger = "fb-messenger-public://user-thread/\(idUser)"

      let urlStringEncoded = urlMessenger.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)
      let URL = NSURL(string: urlStringEncoded!)

      if UIApplication.shared.canOpenURL(URL! as URL) {
        UIApplication.shared.openURL(URL! as URL)
      }
    }

    if ("hasApp" == call.method) {
      let args = call.arguments as! Dictionary<String, String>
      let name = args["name"]

       switch name ?? "0" {
         case "messenger":
           result(schemeAvailable(scheme: "fb-messenger-public://user-thread/"))
           break
         default:
             result(false)
         break
       }
    }
  }

  public func schemeAvailable(scheme: String) -> Bool {
    let urlStringEncoded = scheme.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)
    let URL = NSURL(string: urlStringEncoded!)

    if UIApplication.shared.canOpenURL(URL! as URL) {
      return true
    }

    return false
  }
}
