package com.example.musicapp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.fragment.app.DialogFragment
import com.example.musicapp.databinding.DiaolgErrorEmailBinding


class CustomDialogErrorEmail : DialogFragment()  {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val key = requireArguments().getString("key")
        Log.d(key, "mes")
        val position = requireArguments().getInt(POSITION)
        Log.d(position.toString(), "pos")
        val textStyle = buildSpannedString {
            inSpans(
                ForegroundColorSpan(Color.rgb(246, 243, 12)),
                BackgroundColorSpan(Color.rgb(176, 23, 175))
            ) {

            }
        }


        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return DiaolgErrorEmailBinding.inflate(inflater, container, false)
            .also {
                it.headline.run {
                    append(textStyle)
                }

                it.OK.setOnClickListener{
                    dismiss()
                }
            }
            .root
    }

    companion object{
        val KEY = "key"
        val POSITION = "position"
    }

}