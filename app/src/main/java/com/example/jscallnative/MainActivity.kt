package com.example.jscallnative

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val preWeb = AgentWeb.with(this)
            .setAgentWebParent(findViewById(R.id.webcontent), LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .addJavascriptInterface("platform",JSBridgeCallNative(this))
            .createAgentWeb()
            .ready()
        preWeb!!.get()!!.agentWebSettings!!.webSettings.useWideViewPort = true
        preWeb!!.get()!!.agentWebSettings.webSettings.loadWithOverviewMode = true;
        preWeb!!.get()!!.jsInterfaceHolder.addJavaObject("platform",JSBridgeCallNative(this))
        preWeb.go("file:///android_asset/invite.html")

    }

    class JSBridgeCallNative(val mActivity:Activity) {
        private var mActivityWeakReference: WeakReference<Activity?>? = null
        init {
            mActivityWeakReference =WeakReference(mActivity)
        }


        fun JSBridgeInterface( activity: Activity?) {
            mActivityWeakReference = WeakReference(activity)
        }

        @JavascriptInterface
        fun showErrorPage():String{
            if(mActivityWeakReference?.get() != null){
                return  "love you too  mrs"
            }
            return ""
        }


    }
}