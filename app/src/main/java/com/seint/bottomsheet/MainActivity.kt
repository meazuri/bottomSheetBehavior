package com.seint.bottomsheet

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt
import android.annotation.SuppressLint



class MainActivity : AppCompatActivity() {

    lateinit var  displayMetrics: DisplayMetrics;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        // set bottom sheet visible
        bottom_sheet.visibility = View.VISIBLE
        // make bottom_sheet transparent first
        bottom_sheet.background.alpha=0



        // collapse your sheet at the bottom first
        var mBottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED;


        main_content.getViewTreeObserver().addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            @SuppressLint("NewApi")
            override fun onGlobalLayout() {

                // Obtain layout data from view...
                val locationY = btnSave.bottom
                Log.i("Save Y :", locationY.toString())

                val displayMetrics = DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(displayMetrics)

                var screenHeight = displayMetrics.heightPixels
                Log.i("Screen Height :", screenHeight.toString())

                val possiblePeakHeigh = screenHeight- locationY
                mBottomSheetBehavior.peekHeight = possiblePeakHeigh.toInt();

                main_content.viewTreeObserver.removeOnGlobalLayoutListener(this)

            }
        })

        mBottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    val locationY = btnSave.bottom
                    Log.i("Save Y :", locationY.toString())

                    val displayMetrics = DisplayMetrics()
                    windowManager.defaultDisplay.getMetrics(displayMetrics)

                    var screenHeight = displayMetrics.heightPixels
                    Log.i("Screen Height :", screenHeight.toString())

                    val possiblePeakHeigh = screenHeight- locationY
                    mBottomSheetBehavior.peekHeight = possiblePeakHeigh.toInt();

                    bottom_sheet.background.alpha=0

                }else if(newState == BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheet.background.alpha= 255
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                bottom_sheet.background.alpha= (255 *slideOffset).roundToInt()

            }
        })

    }

}
