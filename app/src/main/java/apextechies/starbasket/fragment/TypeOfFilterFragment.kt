package apextechies.starbasket.fragment

import android.annotation.SuppressLint
import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apextechies.starbasket.R

@SuppressLint("ValidFragment")
class TypeOfFilterFragment(s: String) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragmer_filtertype, container, false)
    }

}