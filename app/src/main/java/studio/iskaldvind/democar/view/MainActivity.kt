package studio.iskaldvind.democar.view

import android.os.Bundle
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.*
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import studio.iskaldvind.democar.base.BaseActivity
import studio.iskaldvind.democar.R.layout.main_activity
import studio.iskaldvind.democar.databinding.MainActivityBinding

class MainActivity : BaseActivity(main_activity) {

    private val binding: MainActivityBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            car.setOnTouchListener { _, _ ->
                car.performClick()
                car.lockState = true
                car.setOnTouchListener(null)
                startAnimation()
                true
            }
        }
    }

    private fun startAnimation() {
        val delay = 500L
        val durationShort = 1000L
        val durationLong = 1500L
        lifecycleScope.launchWhenCreated {
            with(binding) {
                val height = (field.height - car.height).toFloat()
                val width = (field.width - car.width).toFloat()
                provideCarAnimation(0.0f, -height/8, 0f, delay, durationShort, AccelerateInterpolator())
                    .withEndAction {
                        provideCarAnimation(width/2, -height/2, 45.0f, 0, durationLong)
                            .withEndAction {
                                provideCarAnimation(width, -height*7/8, 0f, 0, durationLong)
                                    .withEndAction {
                                        provideCarAnimation(width, -height, 0f, 0, durationShort, DecelerateInterpolator())
                                            .withEndAction {
                                                car
                                                    .animate()
                                                    .setDuration(delay)
                                                car.lockState = false
                                                car.performClick()
                                                car.lockState = true
                                            }
                                    }

                            }
                    }
            }
        }
    }

    private fun provideCarAnimation(
        x: Float,
        y: Float,
        rotate: Float,
        delay: Long,
        duration: Long,
        interpolator: Interpolator? = null
    ): ViewPropertyAnimator {
        return binding.car
            .animate()
            .translationY(y)
            .translationX(x)
            .rotation(rotate)
            .setDuration(duration)
            .setStartDelay(delay)
            .setInterpolator(interpolator)
    }
}