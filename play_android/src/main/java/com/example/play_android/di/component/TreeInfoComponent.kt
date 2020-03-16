package com.example.play_android.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.example.play_android.di.module.TreeInfoModule

import com.jess.arms.di.scope.ActivityScope
import com.example.play_android.mvp.ui.activity.TreeInfoActivity
import com.example.play_android.mvp.ui.fragment.TreeInfoFragment


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/16/2020 21:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = arrayOf(TreeInfoModule::class), dependencies = arrayOf(AppComponent::class))
interface TreeInfoComponent {
    fun inject(activity: TreeInfoActivity)
    fun inject(fragment: TreeInfoFragment)
}
