package com.syarah.core.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import com.syarah.core.geastures.RotationGestureDetector
import kotlin.math.min

/**
 * Project: Syarah Assignment
 * Created: Jun 18, 2021
 *
 * @author Mohamed Hamdan
 */
@SuppressLint("ClickableViewAccessibility")
class OverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes), View.OnTouchListener,
    ScaleGestureDetector.OnScaleGestureListener, RotationGestureDetector.OnRotationGestureListener {

    private var scaleFactor = 1f
    private var dY = 0f
    private var dX = 0f

    private var scaleDetector: ScaleGestureDetector? = null
    private var rotationDetector: RotationGestureDetector? = null

    init {
        scaleDetector = ScaleGestureDetector(context, this)
        rotationDetector = RotationGestureDetector(this, this)
        setOnTouchListener(this)
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        scaleDetector?.onTouchEvent(event)
        rotationDetector?.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                dX = view.x - event.rawX
                dY = view.y - event.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                view.y = event.rawY + dY
                view.x = event.rawX + dX
            }
        }
        return true
    }

    override fun onScale(detector: ScaleGestureDetector): Boolean {
        scaleFactor *= detector.scaleFactor
        scaleFactor = 0.1f.coerceAtLeast(min(scaleFactor, 5.0f))
        scaleX = scaleFactor
        scaleY = scaleFactor
        return false
    }

    override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
        return true
    }

    override fun onScaleEnd(detector: ScaleGestureDetector?) {
        // No impl
    }

    override fun onRotation(rotationDetector: RotationGestureDetector) {
        val angle = rotationDetector.angle
        rotation = angle
    }
}
