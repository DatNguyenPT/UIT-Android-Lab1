package com.example.myapplication

import android.animation.ValueAnimator.REVERSE
import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat


class EX1:AppCompatActivity() {
    private var btnFadeInXml: Button? = null
    private var btnFadeInCode:android.widget.Button? = null
    private var btnFadeOutXml:android.widget.Button? = null
    private var btnFadeOutCode:android.widget.Button? = null
    private var btnBlinkXml: Button? = null
    private var btnBlinkCode: Button? = null
    private var btnZoomInXml:android.widget.Button? = null
    private var btnZoomInCode:android.widget.Button? = null
    private var btnZoomOutXml:android.widget.Button? = null
    private var btnZoomOutCode: Button? = null
    private var btnRotateXml:android.widget.Button? = null
    private var btnRotateCode: Button? = null
    private var btnMoveXml:android.widget.Button? = null
    private var btnMoveCode:android.widget.Button? = null
    private var btnSlideUpXml:android.widget.Button? = null
    private var btnSlideUpCode:android.widget.Button? = null
    private var btnBounceXml: Button? = null
    private var btnBounceCode: Button? = null
    private var btnCombineXml:android.widget.Button? = null
    private var btnCombineCode:android.widget.Button? = null
    private var ivUitLogo: ImageView? = null
    private var animationListener: Animation.AnimationListener? = null


    private fun findViewsByIds() {
        ivUitLogo = findViewById(R.id.iv_uit_logo) as ImageView
        btnFadeInXml = findViewById(R.id.btn_fade_in_xml) as Button
        btnFadeInCode = findViewById(R.id.btn_fade_in_code) as Button
        btnFadeOutXml = findViewById(R.id.btn_fade_out_xml) as Button
        btnFadeOutCode = findViewById(R.id.btn_fade_out_code) as Button
        btnBlinkXml = findViewById(R.id.btn_blink_xml) as Button
        btnBlinkCode = findViewById(R.id.btn_blink_code) as Button
        btnZoomInXml = findViewById(R.id.btn_zoom_in_xml) as Button
        btnZoomInCode = findViewById(R.id.btn_zoom_in_code) as Button
        btnZoomOutXml = findViewById(R.id.btn_zoom_out_xml) as Button
        btnZoomOutCode = findViewById(R.id.btn_zoom_out_code) as Button
        btnRotateXml = findViewById(R.id.btn_rotate_xml) as Button
        btnRotateCode = findViewById(R.id.btn_rotate_code) as Button
        btnMoveXml = findViewById(R.id.btn_move_xml) as Button
        btnMoveCode = findViewById(R.id.btn_move_code) as Button
        btnSlideUpXml = findViewById(R.id.btn_slide_up_xml) as Button
        btnSlideUpCode = findViewById(R.id.btn_slide_up_code) as Button
        btnBounceXml = findViewById(R.id.btn_bounce_xml) as Button
        btnBounceCode = findViewById(R.id.btn_bounce_code) as Button
        btnCombineXml = findViewById(R.id.btn_combine_xml) as Button
        btnCombineCode = findViewById(R.id.btn_combine_code) as Button
    }

    private fun initVariables() {
        animationListener = object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                Toast.makeText(
                    applicationContext, "Animation Stopped",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ex1)
        findViewsByIds()
        initVariables()
        btnFadeInXml?.let { handleClickAnimationXml(it, R.anim.anim_fade_in) }
        btnBlinkXml?.let { handleClickAnimationXml(it, R.anim.anim_blink) }
        btnBounceXml?.let { handleClickAnimationXml(it, R.anim.anim_bounce) }
        btnCombineXml?.let { handleClickAnimationXml(it, R.anim.anim_combine) }
        btnFadeOutXml?.let { handleClickAnimationXml(it, R.anim.anim_fade_out) }
        btnMoveXml?.let { handleClickAnimationXml(it, R.anim.anim_move) }
        btnRotateXml?.let { handleClickAnimationXml(it, R.anim.anim_rotate) }
        btnSlideUpXml?.let { handleClickAnimationXml(it, R.anim.anim_slide_up) }
        btnZoomInXml?.let { handleClickAnimationXml(it, R.anim.anim_zoom_in) }
        btnZoomOutXml?.let { handleClickAnimationXml(it, R.anim.anim_zoom_out) }

        btnFadeInCode?.let { handleClickAnimationCode(it, initFadeInAnimation()) }
        btnBlinkCode?.let { handleClickAnimationCode(it, initBlinkAnimation()) }
        btnBounceCode?.let { handleClickAnimationCode(it, initBounceInAnimation()) }
        btnCombineCode?.let { handleClickAnimationCode(it, initCombineInAnimation()) }
        btnFadeOutCode?.let { handleClickAnimationCode(it, initFadeOutAnimation()) }
        btnMoveCode?.let { handleClickAnimationCode(it, initMoveAnimation()) }
        btnRotateCode?.let { handleClickAnimationCode(it, initRotateAnimation()) }
        btnSlideUpCode?.let { handleClickAnimationCode(it, initSlideUpAnimation()) }
        btnZoomInCode?.let { handleClickAnimationCode(it, initZoomInAnimation()) }
        btnZoomOutCode?.let { handleClickAnimationCode(it, initZoomOutAnimation()) }

        moveToActivity2()
    }

    //EX3
    private fun moveToActivity2() {
        ivUitLogo?.setOnClickListener {
            val newActivity = Intent(this@EX1, EX3::class.java)
            val options = ActivityOptionsCompat.makeCustomAnimation(
                this@EX1,
                R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_left
            )
            startActivity(newActivity, options.toBundle())
        }
    }


    //EX1
    private fun handleClickAnimationXml(btn: Button, animId: Int){
        var animation = AnimationUtils.loadAnimation(this@EX1, animId)
        animation.setAnimationListener(animationListener)
        btn.setOnClickListener(){
            ivUitLogo?.startAnimation(animation)
        }
    }

    private fun handleClickAnimationCode(btn: Button, animation: Animation){
        btn.setOnClickListener(){
            ivUitLogo?.startAnimation(animation)
        }
    }

    fun initFadeInAnimation(): Animation {
        val animation = AlphaAnimation(0f, 1f)
        animation.duration = 1000
        animation.fillAfter = true
        animation.setAnimationListener(animationListener)
        return animation
    }

    fun initBlinkAnimation(): Animation {
        val animation = AlphaAnimation(0f, 1f)
        animation.duration = 300
        animation.fillAfter = true
        animation.repeatCount = 3
        animation.repeatMode = REVERSE
        animation.setAnimationListener(animationListener)
        return animation
    }

    fun initBounceInAnimation(): Animation {
        val animation = ScaleAnimation(
            1.0f, 1.0f,
            0.0f, 1.0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        animation.duration = 500
        animation.fillAfter = true
        animation.interpolator = BounceInterpolator()
        animation.setAnimationListener(animationListener)
        return animation
    }

    fun initCombineInAnimation(): Animation {
        val scaleAnimation = ScaleAnimation(
            1.0f, 3.0f,
            1.0f, 3.0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 4000
        }

        val rotateAnimation = RotateAnimation(
            0.0f, 360.0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 500
            repeatCount = 2
            repeatMode = Animation.RESTART
        }

        val animationSet = AnimationSet(true).apply {
            interpolator = LinearInterpolator()
            fillAfter = true
            addAnimation(scaleAnimation)
            addAnimation(rotateAnimation)
            setAnimationListener(animationListener)
        }

        return animationSet
    }

    fun initFadeOutAnimation(): Animation {
        val animation = AlphaAnimation(1.0f, 0.0f)
        animation.duration = 1000
        animation.fillAfter = true
        animation.setAnimationListener(animationListener)
        return animation
    }

    fun initMoveAnimation(): Animation {
        val translateAnimation = TranslateAnimation(
            Animation.RELATIVE_TO_PARENT, 0.0f,
            Animation.RELATIVE_TO_PARENT, 0.75f,
            Animation.RELATIVE_TO_PARENT, 0.0f,
            Animation.RELATIVE_TO_PARENT, 0.0f
        ).apply {
            duration = 800
            interpolator = LinearInterpolator()
            fillAfter = true
            setAnimationListener(animationListener)
        }

        return translateAnimation
    }

    fun initRotateAnimation(): Animation {
        val rotateAnimation = RotateAnimation(
            0.0f, 360.0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 600
            repeatMode = Animation.RESTART
            repeatCount = 2
            interpolator = LinearInterpolator()
            setAnimationListener(animationListener)
        }

        return rotateAnimation
    }

    fun initSlideUpAnimation(): Animation {
        val scaleAnimation = ScaleAnimation(
            1.0f, 1.0f,
            1.0f, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 500
            fillAfter = true
            setAnimationListener(animationListener)
        }

        return scaleAnimation
    }

    fun initZoomInAnimation(): Animation {
        val scaleAnimation = ScaleAnimation(
            1.0f, 3.0f,
            1.0f, 3.0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 1000
            fillAfter = true
            setAnimationListener(animationListener)
        }

        return scaleAnimation
    }

    fun initZoomOutAnimation(): Animation {
        val scaleAnimation = ScaleAnimation(
            1.0f, 0.5f,
            1.0f, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 1000
            fillAfter = true
            setAnimationListener(animationListener)
        }
        return scaleAnimation
    }
}