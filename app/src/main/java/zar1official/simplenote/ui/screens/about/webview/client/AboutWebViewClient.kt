package zar1official.simplenote.ui.screens.about.webview.client

import android.webkit.WebView
import android.webkit.WebViewClient

class AboutWebViewClient(private val listener: () -> Unit) : WebViewClient() {

    override fun onPageCommitVisible(view: WebView?, url: String?) {
        super.onPageCommitVisible(view, url)
        listener.invoke()
    }
}