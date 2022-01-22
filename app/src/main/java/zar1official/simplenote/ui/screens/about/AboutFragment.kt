package zar1official.simplenote.ui.screens.about

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startAnimation()
    }

    companion object {
        private const val TAG = "AboutFragment"
        private const val ANIM_DURATION = 500L
        private const val TRANSLATION = 100
        private const val PROPERTY_NAME_X = "translationX"
        private const val PROPERTY_NAME_Y = "translationY"


        @JvmStatic
        fun newInstance() = AboutFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun startAnimation() = with(binding) {
        val imageButtonFinishX = imageButton.x
        val imageButtonStartX = imageButtonFinishX - TRANSLATION
        val imageButtonAnimator = ObjectAnimator.ofFloat(
            imageButton,
            PROPERTY_NAME_X,
            imageButtonStartX,
            imageButtonFinishX
        ).apply {
            interpolator = OvershootInterpolator()
        }

        val aboutTextFinishY = aboutText.y
        val aboutTextStartY = aboutTextFinishY - TRANSLATION
        val aboutTextAnimator = ObjectAnimator.ofFloat(
            aboutText,
            PROPERTY_NAME_Y,
            aboutTextStartY,
            aboutTextFinishY
        ).apply {
            interpolator = OvershootInterpolator()
        }

        AnimatorSet().run {
            play(imageButtonAnimator).with(aboutTextAnimator)
            duration = ANIM_DURATION
            start()
        }
    }

    override fun subscribeViewModel() {
        viewModel.onAttemptOpenWebViewSuccessfully.observe(this) {
            parentFragmentManager.beginTransaction().addToBackStack(TAG)
                .replace(R.id.fragment_wrapper, WebViewFragment.newInstance()).commit()
        }
    }
}