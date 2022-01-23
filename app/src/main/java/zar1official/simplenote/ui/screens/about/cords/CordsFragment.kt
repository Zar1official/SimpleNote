package zar1official.simplenote.ui.screens.about.cords

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import org.koin.androidx.viewmodel.ext.android.viewModel
import zar1official.simplenote.databinding.FragmentCordsBinding
import zar1official.simplenote.ui.base.view.Subscriber

class CordsFragment : Fragment(), Subscriber {

    private var _binding: FragmentCordsBinding? = null
    private val binding: FragmentCordsBinding get() = _binding!!
    private val viewModel: CordsViewModel by viewModel()
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        FusedLocationProviderClient(
            requireActivity()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCordsBinding.inflate(inflater, container, false)
        subscribeViewModel()
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

        }
        fusedLocationClient.lastLocation.addOnSuccessListener {
            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = CordsFragment()
    }

    override fun subscribeViewModel() {

    }
}