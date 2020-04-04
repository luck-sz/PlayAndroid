package com.example.play_android.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.example.play_android.di.module.SettingModule

import com.jess.arms.di.scope.ActivityScope
import com.example.play_android.mvp.ui.activity.SettingActivity
import com.example.play_android.mvp.ui.fragment.SettingFragment


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/21/2020 14:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = arrayOf(SettingModule::class), dependencies = arrayOf(AppComponent::class))
interface SettingComponent {
    fun inject(activity: SettingActivity)
    fun inject(fragment: SettingFragment)
}
