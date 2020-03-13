package com.example.play_android.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.example.play_android.mvp.contract.SearchContract
import com.example.play_android.mvp.model.SearchModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/13/2020 13:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建SearchModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class SearchModule(private val view: SearchContract.View) {
    @ActivityScope
    @Provides
    fun provideSearchView(): SearchContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideSearchModel(model: SearchModel): SearchContract.Model {
        return model
    }
}
