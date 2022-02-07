package zar1official.simplenote.ui.screens.about.cords

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import org.koin.androidx.viewmodel.ext.android.viewModel
import zar1official.simplenote.R
import zar1official.simplenote.databinding.FragmentCordsBinding
import zar1official.simplenote.ui.base.view.Subscriber
import zar1official.simplenote.utils.other.showSnackBar

class CordsFragment : Fragment(), Subscriber {

    private var _binding: FragmentCordsBinding? = null
    private val binding: FragmentCordsBinding get() = _binding!!
    private val viewModel: CordsViewModel by viewModel()
    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            viewModel.onAttemptSubscribeLocation(it.values)
        }
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        FusedLocationProviderClient(
            requireActivity()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCordsBinding.inflate(inflater, container, false).apply {
            aboutToolbar.apply {
                setNavigationIcon(R.drawable.ic_back)
                setNavigationOnClickListener {
                    viewModel.onAttemptGoBack()
                }
            }
        }
        subscribeViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launcher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = CordsFragment()
    }

    @SuppressLint("MissingPermission")
    override fun subscribeViewModel() {
        viewModel.onAttemptSubscribeLocationSuccessfully.observe(this) {
            fusedLocationClient.lastLocation.addOnSuccessListener { cords ->
                viewModel.onAttemptShowCords(cords)
            }
        }

        viewModel.onAttemptShowCordsFailed.observe(this) {
            view?.showSnackBar(R.string.show_location_failed)
        }

        viewModel.location.observe(this) { location ->
            binding.locationText.text = "${location.latitude}\n${location.longitude}"
        }

        viewModel.onAttemptGoBackSuccessfully.observe(this) {
            parentFragmentManager.popBackStack()
        }
    }


}