package com.rviannaoliveira.vconstraintlayoutanimation

import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.AnticipateOvershootInterpolator
import kotlinx.android.synthetic.main.page1.*

class MainActivity : AppCompatActivity() {
    private lateinit var constraintSetDetail: ConstraintSet
    private lateinit var constraintSet: ConstraintSet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page1)

        constraintSetDetail = ConstraintSet()
        constraintSetDetail.clone(this, R.layout.page2)
        constraintSet = ConstraintSet()
        constraintSet.clone(this, R.layout.page1)

        backgroundImage.setOnClickListener {
            frameOne()
        }
    }


    private fun frameOne() {
        val transition = ChangeBounds()
        val animFadeInFrameBeforeSSB = AnimationUtils.loadAnimation(applicationContext, android.R.anim.fade_in)
        val animFadeInSSB = AnimationUtils.loadAnimation(applicationContext, android.R.anim.fade_in)
        val animFadeOut = AnimationUtils.loadAnimation(applicationContext, android.R.anim.fade_out)
        val animFadeOutBeforeSSB = AnimationUtils.loadAnimation(applicationContext, android.R.anim.fade_out)

        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 5000

        TransitionManager.beginDelayedTransition(constraint, transition)
        constraintSetDetail.applyTo(constraint)

        animFadeInFrameBeforeSSB.setAnimationListener(object : VAnimationListener {
            override fun onAnimationEnd(animation: Animation?) {
                animFadeOutBeforeSSB.duration = 4000
                backgroundImageBeforeSSB.visibility = View.GONE
                backgroundImageBeforeSSB.startAnimation(animFadeOutBeforeSSB)
                backgroundImageSSB.visibility = View.VISIBLE
                backgroundImageSSB.startAnimation(animFadeInSSB)
            }
        })

        backgroundImage.startAnimation(animFadeOut)
        backgroundImageBeforeSSB.startAnimation(animFadeInFrameBeforeSSB)
        backgroundImageBeforeSSB.visibility = View.VISIBLE
    }

}
