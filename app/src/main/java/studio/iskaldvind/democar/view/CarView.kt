package studio.iskaldvind.democar.view

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.scale
import studio.iskaldvind.democar.R


enum class CarState {
    OFF, ON;

    fun next() = when (this) {
        OFF -> ON
        ON -> OFF
    }
}

class CarView  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var carState = CarState.OFF
    private val carOff = BitmapFactory.decodeResource(resources, R.drawable.car_off)
    private val carOn = BitmapFactory.decodeResource(resources, R.drawable.car_on)
    var lockState = false

    init {
        isClickable = true
    }

    override fun performClick(): Boolean {
        super.performClick()
        if (!lockState) {
            carState = carState.next()
            invalidate()
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        when (carState) {
            CarState.OFF -> carOff
            CarState.ON -> carOn
        }?.let { bitmap ->
            val resized = bitmap.scale(width, height)
            canvas.drawBitmap(resized, 0.0f , 0.0f, null)
        }
    }
}