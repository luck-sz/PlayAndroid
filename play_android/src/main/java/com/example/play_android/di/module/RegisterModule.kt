package com.example.play_android.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.example.play_android.mvp.contract.RegisterContract
import com.example.play_android.mvp.model.RegisterModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/17/2020 17:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建RegisterModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class RegisterModule(private val view: RegisterContract.View) {
    @ActivityScope
    @Provides
    fun provideRegisterView(): RegisterContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideRegisterModel(model: RegisterModel): RegisterContract.Model {
        return model
    }
}
