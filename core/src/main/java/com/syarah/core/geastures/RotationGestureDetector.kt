package com.syarah.core.geastures

import android.graphics.PointF
import android.view.MotionEvent
import android.view.View
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

/**
 * Project: Syarah Assignment
 * Created: Jun 18, 2021
 *
 * @author Mohamed Hamdan
 */
class RotationGestureDetector(private val listener: OnRotationGestureListener, private val view: View) {

    private var fX = 0f
    private var fY = 0f
    private var sX = 0f
    private var sY = 0f
    private var ptrID1: Int = INVALID_POINTER_ID
    private var ptrID2: Int = INVALID_POINTER_ID

    var angle = 0f
        private set

    fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                ptrID1 = event.getPointerId(event.actionIndex)
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                ptrID2 = event.getPointerId(event.actionIndex)
                sX = event.getX(event.findPointerIndex(ptrID1))
                sY = event.getY(event.findPointerIndex(ptrID1))
                fX = event.getX(event.findPointerIndex(ptrID2))
                fY = event.getY(event.findPointerIndex(ptrID2))
            }
            MotionEvent.ACTION_MOVE -> {
                if (ptrID1 != INVALID_POINTER_ID && ptrID2 != INVALID_POINTER_ID) {
                    val nfPoint = getRawPoint(event, ptrID2)
                    val nsPoint = getRawPoint(event, ptrID1)
                    angle = angleBetweenLines(nfPoint.x, nfPoint.y, nsPoint.x, nsPoint.y)
                    listener.onRotation(this)
                }
            }
            MotionEvent.ACTION_UP -> {
                ptrID1 = INVALID_POINTER_ID
            }
            MotionEvent.ACTION_POINTER_UP -> {
                ptrID2 = INVALID_POINTER_ID
            }
            MotionEvent.ACTION_CANCEL -> {
                ptrID1 = INVALID_POINTER_ID
                ptrID2 = INVALID_POINTER_ID
            }
        }
        return true
    }

    private fun getRawPoint(ev: MotionEvent, index: Int): PointF {
        val point = PointF()
        val location = intArrayOf(0, 0)
        view.getLocationOnScreen(location)
        var x = ev.getX(index)
        var y = ev.getY(index)
        var angle = Math.toDegrees(atan2(y.toDouble(), x.toDouble()))
        angle += view.rotation
        val length = PointF.length(x, y)
        x = (length * cos(Math.toRadians(angle))).toFloat() + location[0]
        y = (length * sin(Math.toRadians(angle))).toFloat() + location[1]
        point.set(x, y)
        return point
    }

    private fun angleBetweenLines(nfX: Float, nfY: Float, nsX: Float, nsY: Float): Float {
        val angle1 = atan2(fY.toDouble() - sY, fX.toDouble() - sX).toFloat()
        val angle2 = atan2(nfY - nsY.toDouble(), nfX - nsX.toDouble()).toFloat()

        var angle = Math.toDegrees((angle1 - angle2).toDouble()).toFloat() % 360
        if (angle < -180f) angle += 360.0f
        if (angle > 180f) angle -= 360.0f
        return -angle
    }

    interface OnRotationGestureListener {

        fun onRotation(rotationDetector: RotationGestureDetector)
    }

    companion object {
        private const val INVALID_POINTER_ID = -1
    }
}