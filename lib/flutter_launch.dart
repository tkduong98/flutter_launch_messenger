import 'dart:async';

import 'package:flutter/services.dart';

class FlutterLaunch {
  static const MethodChannel _channel =
      MethodChannel('br.com.thyagoluciano/flutter_launch');

  static Future<void> launchMessenger({
    required String id,
  }) async {
    final Map<String, dynamic> params = <String, dynamic>{
      'id': id,
    };
    await _channel.invokeMethod('launchMessenger', params);
  }

  static Future<bool> hasApp({required String name}) async {
    final Map<String, dynamic> params = <String, dynamic>{
      'name': name,
    };
    return await _channel.invokeMethod('hasApp', params);
  }
}
