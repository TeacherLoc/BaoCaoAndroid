package com.example.app_n1.bottomSheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.app_n1.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class WightBottomSheet : BottomSheetDialogFragment() {
    private var currentValue = 50

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_weight_bottom_sheet, container, false)

        val numberEditText = view.findViewById<EditText>(R.id.numberDisplayWeight)

//        view.findViewById<ImageView>(R.id.decreaseButtonWeight).setOnClickListener {
//            if (currentValue > 10) { // Kiểm tra không cho phép nhỏ hơn 500
//                currentValue--
//                numberEditText.setText(currentValue.toString())
//            }
//        }

//        view.findViewById<ImageView>(R.id.increaseButtonWeight).setOnClickListener {
//            if(currentValue < 300){
//                currentValue++
//                numberEditText.setText(currentValue.toString())    }
//        }

        view.findViewById<Button>(R.id.closeButtonWeight).setOnClickListener {
            dismiss()
        }

        return view
    }
}