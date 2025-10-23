package com.example.flutter_social_share;

import java.util.List;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

public class FlutterSocialSharePlugin implements MethodCallHandler, FlutterPlugin, ActivityAware {

  private static final String TAG = "FlutterSocialSharePlugin";
  private Activity activity;
  private MethodChannel methodChannel;
  private CallbackManager callbackManager;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
    onAttachedToEngine(binding.getBinaryMessenger());
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    methodChannel.setMethodCallHandler(null);
  }

  private void onAttachedToEngine(BinaryMessenger messenger) {
    methodChannel = new MethodChannel(messenger, "flutter_social_share");
    methodChannel.setMethodCallHandler(this);
    callbackManager = CallbackManager.Factory.create();
  }

  @Override
  public void onMethodCall(MethodCall call, @NonNull Result result) {
    switch (call.method) {
      case "facebook_share":
        shareToFacebook(call.argument("imagePath"), call.argument("msg"), result);
        break;
      case "twitter_share":
        shareToTwitter(call.argument("url"), call.argument("msg"), result);
        break;
      case "whatsapp_share":
        shareWhatsApp(call.argument("url"), call.argument("msg"), result, false);
        break;
      case "whatsapp_business_share":
        shareWhatsApp(call.argument("url"), call.argument("msg"), result, true);
        break;
      case "system_share":
        shareSystem(call.argument("msg"), result);
        break;
      case "instagram_share":
        shareInstagramStory(call.argument("url"), result);
        break;
      case "telegram_share":
        shareToTelegram(call.argument("msg"), result);
        break;
      case "sms_share":
        shareToSms(call.argument("msg"), result);
        break;
      case "mail_share":
        shareToMail(call.argument("msg"), call.argument("subject"), call.argument("recipients"), result);
        break;
      default:
        result.notImplemented();
    }
  }

  private void shareSystem(String msg, Result result) {
    try {
      Intent intent = new Intent(Intent.ACTION_SEND);
      intent.setType("text/plain");
      intent.putExtra(Intent.EXTRA_TEXT, msg);
      activity.startActivity(Intent.createChooser(intent, "Share via"));
      result.success("success");
    } catch (Exception e) {
      result.error("ERROR", e.toString(), null);
    }
  }

  private void shareToTwitter(String url, String msg, Result result) {
    try {
      URL twitterUrl = new URL("https://twitter.com/intent/tweet?text=" + msg + "&url=" + url);
      Intent twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterUrl.toString()));
      activity.startActivity(twitterIntent);
      result.success("success");
    } catch (MalformedURLException e) {
      result.error("ERROR", e.toString(), null);
    }
  }

  private void shareToFacebook(String imagePath, String message, Result result) {
    try {
      Intent intent = new Intent(Intent.ACTION_SEND);

      // Set the type to handle both text and images
      if (imagePath != null && !imagePath.isEmpty()) {
        // If an image URL is provided, share the image
        File file = new File(imagePath);
        Uri fileUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", file);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
      } else {
        // If no image is provided, share the message as text
        intent.setType("text/plain");
      }

      // Add the message if provided
      if (message != null && !message.isEmpty()) {
        intent.putExtra(Intent.EXTRA_TEXT, message);
      }

      // Set the package to Facebook
      intent.setPackage("com.facebook.katana");

      activity.startActivity(intent);
      result.success("success");
    } catch (ActivityNotFoundException e) {
      result.error("FACEBOOK_NOT_INSTALLED", "Facebook is not installed on this device", null);
    } catch (Exception e) {
      result.error("ERROR", e.toString(), null);
    }
  }

  private void shareWhatsApp(String imagePath, String msg, Result result, boolean isBusiness) {
    try {
      Intent intent = new Intent(Intent.ACTION_SEND);
      intent.setPackage(isBusiness ? "com.whatsapp.w4b" : "com.whatsapp");
      intent.putExtra(Intent.EXTRA_TEXT, msg);

      if (!TextUtils.isEmpty(imagePath)) {
        android.util.Log.d(TAG, "shareWhatsApp: " + imagePath);
        File file = new File(imagePath);
        Uri fileUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", file);
        android.util.Log.d(TAG, "shareWhatsAppFileURl: " + fileUri);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
      } else {
        android.util.Log.d(TAG, "shareWhatsAppText: " + imagePath);
        intent.setType("text/plain");
      }
      activity.startActivity(intent);
      result.success("success");
    } catch (Exception e) {
      result.error("ERROR", e.toString(), null);
    }
  }

  private void shareInstagramStory(String url, Result result) {
    try {
      File file = new File(url);
      Uri fileUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", file);

      Intent intent = new Intent(Intent.ACTION_SEND);
      intent.setType("image/*");
      intent.putExtra(Intent.EXTRA_STREAM, fileUri);
      intent.setPackage("com.instagram.android");
      activity.startActivity(intent);
      result.success("success");
    } catch (ActivityNotFoundException e) {
      result.error("INSTAGRAM_NOT_INSTALLED", "Instagram is not installed on this device", null);
    } catch (Exception e) {
      result.error("ERROR", e.toString(), null);
    }
  }

  private void shareToTelegram(String msg, Result result) {
    try {
      Intent intent = new Intent(Intent.ACTION_SEND);
      intent.setType("text/plain");
      intent.setPackage("org.telegram.messenger");
      intent.putExtra(Intent.EXTRA_TEXT, msg);
      activity.startActivity(intent);
      result.success("success");
    } catch (ActivityNotFoundException e) {
      result.error("TELEGRAM_NOT_INSTALLED", "Telegram is not installed on this device", null);
    }
  }

  private void shareToSms(String msg, Result result) {
    try {
      Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"));
      intent.putExtra("sms_body", msg);
      activity.startActivity(intent);
      result.success("SMS app opened successfully.");
    } catch (Exception e) {
      result.error("ERROR", e.toString(), null);
    }
  }

  private void shareToMail(String msg, String subject, List<String> recipients, Result result) {
    try {
      Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
      intent.putExtra(Intent.EXTRA_SUBJECT, subject);
      intent.putExtra(Intent.EXTRA_TEXT, msg);
      if (recipients != null && !recipients.isEmpty()) {
        intent.putExtra(Intent.EXTRA_EMAIL, recipients.toArray(new String[0]));
      }
      activity.startActivity(Intent.createChooser(intent, "Choose an email client"));
      result.success("Email app opened successfully.");
    } catch (Exception e) {
      result.error("ERROR", e.toString(), null);
    }
  }

  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
    this.activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivity() {
    this.activity = null;
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {
    this.activity = null;
  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
    this.activity = binding.getActivity();
  }
}
