package zar1official.simplenote.ui.screens.about.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import zar1official.simplenote.R
import zar1official.simplenote.databinding.FragmentWebViewBinding
import zar1official.simplenote.ui.base.view.Subscriber
import zar1official.simplenote.ui.screens.about.webview.client.AboutWebViewClient
import zar1official.simplenote.utils.other.observeOnce

class WebViewFragment : Fragment(), Subscriber {

    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WebViewFragmentViewModel by viewModel()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebViewBinding.inflate(inflater, container, false).apply {
            webView.apply {
                loadUrl(GITHUB_URL)
                settings.javaScriptEnabled = true
                webViewClient = AboutWebViewClient {
                    viewModel.onPageLoaded()
                }
            }
            aboutToolbar.apply {
                setNavigationIcon(R.drawable.ic_back)
                setNavigationOnClickListener {
                    viewModel.onAttemptGoBack(webView.canGoBack())
                }
            }
        }
        subscribeViewModel()
        return binding.root
    }

    companion object {
        private const val GITHUB_URL = "https://github.com/Zar1official/SimpleNote"

        @JvmStatic
        fun newInstance() = WebViewFragment()
    }

    override fun subscribeViewModel() {
        viewModel.onGoBackFragment.observe(this) {
            parentFragmentManager.popBackStack()
        }

        viewModel.onAttemptGoBackSuccessfully.observe(this) {
            binding.webView.goBack()
        }

        viewModel.onPageLoadedSuccessfully.observeOnce(this) {
            with(binding) {
                progressBar.visibility = View.GONE
                webView.visibility = View.VISIBLE
            }
        }
    }
}