# flutter_social_share_plugin

[![pub package](https://img.shields.io/pub/v/flutter_social_share_plugin.svg)](https://pub.dartlang.org/packages/flutter_social_share_plugin)
[![style: lint](https://img.shields.io/badge/style-lint-4BC0F5.svg)](https://pub.dev/packages/lint)

Flutter Plugin for sharing contents to social media.

You can use it share to Facebook , Instagram , WhatsApp(WhatsAppBusiness) , Twitter, Telegram,Sms,Mails And System Share UI. 
Support Url and Text.

support:
 - Android & iOS :  Facebook,WhatsApp(WhatsAppBusiness),Twitter,Instagram,System Share,Sms,Mails

**Note: This plugin is still under development, and some APIs might not be available yet.  
Feedback and Pull Requests are most welcome!**

## Getting Started

add `flutter_social_share_plugin` as a [dependency in your pubspec.yaml file](https://flutter.io/platform-plugins/).

Please check the latest version before installation.
```
dependencies:
  flutter:
    sdk: flutter
  # add flutter_social_share_plugin
  flutter_social_share_plugin: ^2.1.3
```
## Setup 

#### Android

### Setup Whatsapp

Make sure you add whatsapp in AndroidManifest.xml.

````
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
...
    <queries>
        <package android:name="com.whatsapp" />
    </queries>
...
</manifest>
````
### Setup SMS

Make sure you add SMS in AndroidManifest.xml.

````
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
...
    <uses-permission android:name="android.permission.SEND_SMS" />
...
</manifest>
````

### Setup Instagram

Make sure you add SMS in AndroidManifest.xml.

````
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
...
    <application>
...
   <provider
            android:name="androidx.core.content.FileProvider"
            android:authorities="${applicationId}.provider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/file_paths" />
        </provider>
...
    </application>
...
</manifest>
````

#### IOS
    
##### setup facebook

Add below value in url scheme(Refer to example).


```
<key>LSApplicationQueriesSchemes</key>
	<array>
		<string>fbauth2</string>
		<string>fb</string>
		<string>fbapi</string>
		<string>fbapi20130214</string>
		<string>fbapi20130410</string>
		<string>fbapi20130702</string>
		<string>fbapi20131010</string>
		<string>fbapi20131219</string>
		<string>fbapi20140410</string>
		<string>fbapi20140116</string>
		<string>fbapi20150313</string>
		<string>fbapi20150629</string>
		<string>fbapi20160328</string>
		<string>fbauth</string>
		<string>fb-messenger-share-api</string>
		<string>fbauth2</string>
		<string>fbshareextension</string>
		<string>tg</string>
	</array>
```

### Setup Whatsapp

Make sure you add whatsapp in plist.

````
<key>LSApplicationQueriesSchemes</key>
        <array>
            <string>whatsapp</string>
        </array>
````

#### Setup Twiter

````
<key>LSApplicationQueriesSchemes</key>
        <array>
            <string>twitter</string>
        </array>
````
#### Setup SMS

````
<key>LSApplicationQueriesSchemes</key>
        <array>
            <string>sms</string>
        </array>
````

## Usage

#### Add the following imports to your Dart code:

```
import 'package:flutter_social_share_plugin/flutter_social_share_plugin.dart';
```


## Methods

### facebook
#### shareToFacebook({String msg, String imagePath})

### Instagram
#### shareToInstagram({String filePath})   

### twitter
#### shareToTwitter({String msg, String url})   

### whatsapp
#### shareToWhatsApp({String msg,String imagePath})  
#### shareToWhatsApp4Biz({String msg,String imagePath})  (only android)

### telegram
#### shareToTelegram({String msg})

### Sms
#### shareToSms({String msg})

### mail
#### shareToMail({required String mailBody,String mailSubject,List<String> mailRecipients})

### system
#### shareToSystem({String msg})   use system share ui

These methods will return "success" if they successfully jump to the corresponding app.

| Parameter  | Description                        |
| :------------ |:-----------------------------------|
| String msg  | Text message                       |
| String url  | Url url                            |
| String imagePath  | The local path of the image        |
| String mailBody  | Text mail Body                     |
| String mailSubject  | Text Mail subject                  |
| List<String> mailRecipients  | List of string for mail recipients |

## Example
```
   Container(
          width: double.infinity,
          child: Column(
            children: <Widget>[
              const SizedBox(height: 30),
              ElevatedButton(onPressed: pickImage, child: Text('Pick Image')),
              ElevatedButton(onPressed: pickVideo, child: Text('Pick Video')),
              ElevatedButton(
                  onPressed: () => onButtonTap(Share.twitter),
                  child: const Text('share to twitter')),
              ElevatedButton(
                onPressed: () => onButtonTap(Share.whatsapp),
                child: const Text('share to WhatsApp'),
              ),
              ElevatedButton(
                onPressed: () => onButtonTap(Share.whatsapp_business),
                child: const Text('share to WhatsApp Business'),
              ),
              ElevatedButton(
                onPressed: () => onButtonTap(Share.facebook),
                child: const Text('share to  FaceBook'),
              ),
              ElevatedButton(
                onPressed: () => onButtonTap(Share.share_instagram),
                child: const Text('share to Instagram'),
              ),
              ElevatedButton(
                onPressed: () => onButtonTap(Share.share_telegram),
                child: const Text('share to Telegram'),
              ),
              ElevatedButton(
                onPressed: () => onButtonTap(Share.share_system),
                child: const Text('share to System'),
              ),
              ElevatedButton(
                onPressed: () => onButtonTap(Share.share_sms),
                child: const Text('share to sms'),
              ),
              ElevatedButton(
                onPressed: () => onButtonTap(Share.share_mail),
                child: const Text('share to mail'),
              ),
            ],
          ),
        ),
```
## Contributor

<a href="https://github.com/KhilanVitthani/flutter_social_share_plugin/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=KhilanVitthani/flutter_social_share_plugin" />
</a>

## 

### Checkout the full example [here](https://github.com/khilanvitthani/flutter_social_share_plugin/blob/main/example/lib/main.dart) 
