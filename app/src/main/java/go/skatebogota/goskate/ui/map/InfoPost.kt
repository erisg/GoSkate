package go.skatebogota.goskate.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import go.skatebogota.goskate.R
import go.skatebogota.goskate.ui.viewmodels.MapsViewModel

class InfoPost: BottomSheetDialogFragment() {

    private val viewModelMaps: MapsViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container:  ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.info_spot, container,false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}