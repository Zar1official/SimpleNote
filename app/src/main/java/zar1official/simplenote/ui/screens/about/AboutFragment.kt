package zar1official.simplenote.ui.screens.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import zar1official.simplenote.R
import zar1official.simplenote.databinding.FragmentAboutBinding
import zar1official.simplenote.ui.base.view.Subscriber
import zar1official.simplenote.ui.screens.about.webview.WebViewFragment


class AboutFragment : Fragment(), Subscriber {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AboutViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
            lifecycleOwner = this@AboutFragment
        }
        subscribeViewModel()
        return binding.root
    }

    companion object {
        private const val TAG = "AboutFragment"

        @JvmStatic
        fun newInstance() = AboutFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun subscribeViewModel() {
        viewModel.onAttemptOpenWebViewSuccessfully.observe(this) {
            parentFragmentManager.beginTransaction().addToBackStack(TAG)
                .replace(R.id.fragment_wrapper, WebViewFragment.newInstance()).commit()
        }
    }
}