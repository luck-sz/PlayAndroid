package com.example.play_android.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.example.play_android.di.module.NavigationModule

import com.jess.arms.di.scope.FragmentScope
import com.example.play_android.mvp.ui.fragment.NavigationFragment


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/10/2020 20:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = arrayOf(NavigationModule::class), dependencies = arrayOf(AppComponent::class))
interface NavigationComponent {
    fun inject(fragment: NavigationFragment)
}
