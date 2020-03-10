package com.example.play_android.di.module

import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.example.play_android.mvp.contract.PublicChildContract
import com.example.play_android.mvp.model.PublicChildModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/10/2020 14:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建PublicChildModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class PublicChildModule(private val view: PublicChildContract.View) {
    @FragmentScope
    @Provides
    fun providePublicChildView(): PublicChildContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun providePublicChildModel(model: PublicChildModel): PublicChildContract.Model {
        return model
    }
}
