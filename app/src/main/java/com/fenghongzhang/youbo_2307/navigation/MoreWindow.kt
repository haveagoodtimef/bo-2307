//package com.haibaoshow.youbo.widget.navigation
//
//
//import android.Manifest
//import android.animation.Animator
//import android.animation.AnimatorListenerAdapter
//import android.content.res.Resources
//import android.os.Build
//import android.os.Handler
//import android.view.Gravity
//import android.view.View
//import android.view.ViewAnimationUtils
//import android.view.ViewGroup
//import android.view.animation.*
//import android.widget.ImageView
//import android.widget.LinearLayout
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//
//
//
//class MoreWindow(val act: AppCompatActivity) : BasePopupWindow(act) {
//    private var close: ImageView? = null
//    private var bgView: View? = null
//    private var blurringView: BlurringView? = null
//    private var mWidth = 0
//    private var mHeight = 0
//    private val mHandler = Handler()
//
//    override fun onCreateContentView(): View {
//        return createPopupById(R.layout.more_window)
//    }
//
//    override fun onViewCreated(contentView: View) {
//        super.onViewCreated(contentView)
//        popupWindow.setClippingEnabled(false);
//
//        close = contentView!!.findViewById<View>(R.id.iv_close) as ImageView
//        close!!.setOnClickListener {
//            dismiss()
//        }
//        blurringView = contentView!!.findViewById<View>(R.id.blurring_view) as BlurringView
//        blurringView!!.blurredView(contentView!!) //模糊背景cong xiangce xuanze
//        if (UserBiz.overdue && UserBiz.isAnchor) {//主播过期
//            contentView.findViewById<View>(R.id.tv_live).hide()
//            contentView.findViewById<View>(R.id.tv_anchor).hide()
//        }
//        contentView.findViewById<View>(R.id.ll_short_video).setSingClick {
//            //青少年模式
//            if (YouthModelBiz.is_open == 1) {
//                showNormalToast("青少年模式开启中，不支持发布短视频", Toast.LENGTH_SHORT, Gravity.CENTER)
//            } else {
//                if (UserBiz.notLogin) {
//                    OneLoginBiz.start(act)
//                    return@setSingClick
//                }
//                XXPermissions.with(act)
//                        .permission(Manifest.permission.CAMERA)
//                        .permission(Manifest.permission.RECORD_AUDIO)
//                        .request(object : OnPermissionCallback {
//                            override fun onGranted(permissions: MutableList<String>?, all: Boolean) {
//                                SelectShortVideoPopup(act).showPopupWindow()
//                                dismiss()
//                            }
//                        }
//                        )
//            }
//
//        }
//        contentView.findViewById<View>(R.id.ll_live).setSingClick {
//            //青少年模式
//            if (YouthModelBiz.is_open == 1) {
//                showNormalToast("青少年模式开启中，不支持开播", Toast.LENGTH_SHORT, Gravity.CENTER)
//            } else {
//                if (UserBiz.notLogin) {
//                    OneLoginBiz.start(act)
//                    return@setSingClick
//                }
//                if (!UserBiz.isAnchor) {
//                    MsgDialog.init(act.supportFragmentManager)
//                            .setMsg("您当前是粉丝身份，暂无直播权限，请开通主播身份后再直播哦~")
//                            .setRight("去开通", {
//                                AnchorRenewAct.start(act)
//                            }).show()
//                    return@setSingClick
//                }
//                newApi.store().bindMain(act).normalSub({ shopBean ->
//                    XXPermissions.with(act)
//                            .permission(Manifest.permission.CAMERA)
//                            .permission(Manifest.permission.RECORD_AUDIO)
//                            .request(object : OnPermissionCallback {
//                                override fun onGranted(permissions: MutableList<String>?, all: Boolean) {
//                                    LiveBiz.createLiveRoom(act, shopBean.data!!)
//                                    dismiss()
//                                }
//                            }
//                            )
//                })
//            }
//
//        }
//        contentView!!.findViewById<View>(R.id.tv_anchor).setSingClick {
//            //青少年模式
//            if (YouthModelBiz.is_open == 1) {
//                showNormalToast("青少年模式开启中，不支持开播", Toast.LENGTH_SHORT, Gravity.CENTER)
//            } else {
//                if (UserBiz.notLogin) {
//                    OneLoginBiz.start(act)
//                    return@setSingClick
//                }
//                if (!UserBiz.isAnchor) {
//                    MsgDialog.init(act.supportFragmentManager)
//                            .setMsg("您当前是粉丝身份，暂无直播权限，请开通主播身份后再直播哦~")
//                            .setRight("去开通", {
//                                AnchorRenewAct.start(act)
//                            }).show()
//                    return@setSingClick
//                }
//                newApi.store().bindMain(act).normalSub({ shopBean ->
//                    LiveBiz.createLiveRoom(act, shopBean.data!!)
//                    dismiss()
//                })
//            }
//
//        }
//        contentView!!.findViewById<View>(R.id.ll_circle).setSingClick {
//            //青少年模式
//            if (YouthModelBiz.is_open == 1) {
//                showNormalToast("青少年模式开启中，不支持发布圈子", Toast.LENGTH_SHORT, Gravity.CENTER)
//            } else {
//                if (UserBiz.notLogin) {
//                    OneLoginBiz.start(act)
//                    return@setSingClick
//                }
//                EditCircleAct.start(act)
//                dismiss()
//            }
//
//        }
//        contentView!!.setSingClick {
//            dismiss()
//        }
//    }
//
//    override fun onShowing() {
//        super.onShowing()
//        mHandler.post { //＋ 旋转动画
//            close!!.animate().rotation(90f).duration = 300
//            contentView.findViewById<View>(R.id.ll_short_video).animate()
//                    .alpha(1f)
//                    .scaleX(1.5f).scaleY(1.5f)
//                    .translationXBy(-250f).translationYBy(-250f)
//                    .setInterpolator(AccelerateInterpolator())
//                    .setDuration(200).setListener(object : AnimatorLis() {
//                        override fun onAnimationEnd(animation: Animator) {
//                            super.onAnimationEnd(animation)
//                            contentView.findViewById<View>(R.id.tv_short_video).show()
//                        }
//                    })
//            contentView.findViewById<View>(R.id.ll_live).animate()
//                    .alpha(1f)
//                    .scaleX(1.5f).scaleY(1.5f)
//                    .translationYBy(-500f)
//                    .setInterpolator(AccelerateInterpolator())
//                    .setDuration(200).setListener(object : AnimatorLis() {
//                        override fun onAnimationEnd(animation: Animator) {
//                            super.onAnimationEnd(animation)
//                            contentView.findViewById<View>(R.id.tv_live).show()
//                        }
//                    })
//            contentView.findViewById<View>(R.id.ll_circle).animate()
//                    .alpha(1f)
//                    .scaleX(1.5f).scaleY(1.5f)
//                    .translationXBy(250f).translationYBy(-250f)
//                    .setInterpolator(AccelerateInterpolator())
//                    .setDuration(200).setListener(object : AnimatorLis() {
//                        override fun onAnimationEnd(animation: Animator) {
//                            super.onAnimationEnd(animation)
//                            contentView.findViewById<View>(R.id.tv_circle).show()
//                            contentView.findViewById<View>(R.id.tv_anchor).gone(UserBiz.isAnchor)
//                        }
//                    })
//        }
//    }
//
//    /**
//     * 显示window动画
//     *
//     * @param anchor
//     */
//    fun showMoreWindow(anchor: View?) {
////        showAtLocation(anchor, Gravity.TOP or Gravity.START, 0, 0)
//        mHandler.post {
////            try {
//            //圆形扩展的动画
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                val x = mWidth / 2
//                val y = (mHeight - fromDpToPx(25f)).toInt()
//                val animator = ViewAnimationUtils.createCircularReveal(
//                        bgView, x,
//                        y, 0f, bgView!!.height.toFloat()
//                )
//                animator.addListener(object : AnimatorListenerAdapter() {
//                    override fun onAnimationStart(animation: Animator) {
////                        layout?.show()
//                        bgView?.show()
//                    }
//
//                    override fun onAnimationEnd(animation: Animator) {
//                        bgView?.show()
//                    }
//                })
//                animator.duration = 300
//                animator.start()
//            }
////            } catch (e: Exception) {
////                e.printStackTrace()
////            }
//        }
////        showAnimation(layout)
//    }
//
//    private fun showAnimation(layout: ViewGroup?) {
////        try {
////        val linearLayout = layout!!.findViewById<ConstraintLayout>(R.id.lin)
//        mHandler.post { //＋ 旋转动画
//            close!!.animate().rotation(90f).duration = 300
//        }
//        //菜单项弹出动画
////        for (i in 0 until linearLayout.childCount) {
////            val child = linearLayout.getChildAt(i)
////            child.visibility = View.INVISIBLE
////            mHandler.postDelayed({
////                child.visibility = View.VISIBLE
////                val fadeAnim: ValueAnimator = ObjectAnimator.ofFloat(child, "translationY", 600f, 0f)
////                fadeAnim.duration = 200
////                val kickAnimator = KickBackAnimator()
////                kickAnimator.setDuration(150f)
////                fadeAnim.setEvaluator(kickAnimator)
////                fadeAnim.start()
////            }, i * 50 + 100.toLong())
////        }
////        } catch (e: Exception) {
////            e.printStackTrace()
////        }
//    }
//
//    /**
//     * 关闭window动画
//     */
//    private fun closeAnimation() {
//        mHandler.post { close!!.animate().rotation(-90f).duration = 300 }
////        try {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val x = mWidth / 2
//            val y = (mHeight - fromDpToPx(25f)).toInt()
//            val animator = ViewAnimationUtils.createCircularReveal(
//                    bgView, x,
//                    y, bgView!!.height.toFloat(), 0f
//            )
//            animator.addListener(object : AnimatorListenerAdapter() {
//                override fun onAnimationStart(animation: Animator) {
////                    layout?.setVisibility(View.VISIBLE);
//                }
//
//                override fun onAnimationEnd(animation: Animator) {
////                    layout?.hide()
//                    bgView?.hide()
//                    dismiss()
//                }
//            })
//            animator.duration = 300
//            animator.start()
//        }
////        } catch (e: Exception) {
////        }
//    }
//
//    fun fromDpToPx(dp: Float): Float {
//        return dp * Resources.getSystem().displayMetrics.density
//    }
//
//    fun dim(cl: LinearLayout?) {
//        blurringView = contentView!!.findViewById<View>(R.id.blurring_view) as BlurringView
//        blurringView!!.blurredView(cl!!) //模糊背景cong xiangce xuanze
//    }
//
//
//}