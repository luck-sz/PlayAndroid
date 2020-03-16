package com.example.play_android.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.example.play_android.mvp.contract.TreeInfoContract
import com.example.play_android.mvp.model.TreeInfoModel


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
@Module
//构建TreeInfoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class TreeInfoModule(private val view: TreeInfoContract.View) {
    @ActivityScope
    @Provides
    fun provideTreeInfoView(): TreeInfoContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideTreeInfoModel(model: TreeInfoModel): TreeInfoContract.Model {
        return model
    }
}
