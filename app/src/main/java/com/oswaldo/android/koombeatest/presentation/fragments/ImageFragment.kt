package com.oswaldo.android.koombeatest.presentation.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.oswaldo.android.koombeatest.R
import com.oswaldo.android.koombeatest.utils.OnSwipeTouchListener
import kotlinx.android.synthetic.main.image_visualization.view.*

class ImageFragment: DialogFragment() {

    private lateinit var dialogView: Dialog

    companion object {
        val TAG = ImageFragment::class.qualifiedName
        val ARG_VALUE = "image"

        fun show(supportFragment: FragmentManager, value: String) {
            ImageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_VALUE, value)
                }
            }.show(supportFragment, TAG)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.image_visualization, null)
        Glide.with(activity!!).load(arguments?.getString("image")).into(view.iv_image)

        view.setOnTouchListener(object : OnSwipeTouchListener(activity!!){
            override fun onSwipeBottom() {
                super.onSwipeBottom()
                dialogView.cancel()
            }
        })

        dialogView = AlertDialog.Builder(activity!!)
            .setTitle("")
            .setView(view)
            .create()
            .apply {
                setCanceledOnTouchOutside(false)
            }

        return dialogView
    }
}