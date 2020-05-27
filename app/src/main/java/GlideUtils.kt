package com.example.ponycui_home.svgaplayer

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v4.app.Fragment
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.BaseTarget
import java.io.File

/**
 * 释放加载器资源取消请求
 *
 * 一般不需要清除；清除动作应该是交由Glide的自己处理
 */
fun <T> BaseTarget<T>.recycleResource() {
    this@recycleResource.request?.apply request@{
        this@request.clear()
        this@request.recycle()
    }
}

fun ImageView.setImage(
        attach: Context,
        source:DataSource?,
        skipMemoryCache:Boolean = GLIDE_SKIP_MEMORY,
        diskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.ALL,
        glideRequest : (GlideRequest<Drawable>) -> GlideRequest<Drawable> = EMPTY)
        : BaseTarget<Drawable>?
        = GlideApp.with(attach)
        .checkDataSource(source)
        ?.operatingRequests(skipMemoryCache, diskCacheStrategy, glideRequest)
        ?.into(this@setImage)

fun ImageView.setImage(
        attach: Activity,
        source:DataSource?,
        skipMemoryCache:Boolean = GLIDE_SKIP_MEMORY,
        diskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.ALL,
        glideRequest : (GlideRequest<Drawable>) -> GlideRequest<Drawable> = EMPTY)
        : BaseTarget<Drawable>?
        = attach.checkDestroyed()
        ?.checkDataSource(source)
        ?.operatingRequests(skipMemoryCache, diskCacheStrategy, glideRequest)
        ?.into(this@setImage)


fun ImageView.setImage(
        attach: Fragment,
        source:DataSource?,
        skipMemoryCache:Boolean = GLIDE_SKIP_MEMORY,
        diskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.ALL,
        glideRequest : (GlideRequest<Drawable>) -> GlideRequest<Drawable> = EMPTY)
        : BaseTarget<Drawable>?
        = GlideApp.with(attach)
        .checkDataSource(source)
        ?.operatingRequests(skipMemoryCache, diskCacheStrategy, glideRequest)
        ?.into(this@setImage)

fun ImageView.setImage(
        attach: View,
        source:DataSource?,
        skipMemoryCache:Boolean = GLIDE_SKIP_MEMORY,
        diskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.ALL,
        glideRequest : (GlideRequest<Drawable>) -> GlideRequest<Drawable> = EMPTY)
        : BaseTarget<Drawable>?
        = GlideApp.with(attach)
        .checkDataSource(source)
        ?.operatingRequests(skipMemoryCache, diskCacheStrategy, glideRequest)
        ?.into(this@setImage)

fun com.bumptech.glide.request.target.Target<Drawable>.loadTarget(
        attach: Context,
        source:DataSource?,
        skipMemoryCache:Boolean = GLIDE_SKIP_MEMORY,
        diskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.ALL,
        glideRequest : (GlideRequest<Drawable>) -> GlideRequest<Drawable> = EMPTY)
        = GlideApp.with(attach)
        .checkDataSource(source)
        ?.operatingRequests(skipMemoryCache, diskCacheStrategy, glideRequest)
        ?.into(this@loadTarget)

fun com.bumptech.glide.request.target.Target<Drawable>.loadTarget(
        attach: Activity,
        source:DataSource?,
        skipMemoryCache:Boolean = GLIDE_SKIP_MEMORY,
        diskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.ALL,
        glideRequest : (GlideRequest<Drawable>) -> GlideRequest<Drawable> = EMPTY)
        = attach.checkDestroyed()
        ?.checkDataSource(source)
        ?.operatingRequests(skipMemoryCache, diskCacheStrategy, glideRequest)
        ?.into(this@loadTarget)

fun com.bumptech.glide.request.target.Target<Drawable>.loadTarget(
        attach: Fragment,
        source:DataSource?,
        skipMemoryCache:Boolean = GLIDE_SKIP_MEMORY,
        diskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.ALL,
        glideRequest : (GlideRequest<Drawable>) -> GlideRequest<Drawable> = EMPTY)
        = GlideApp.with(attach)
        .checkDataSource(source)
        ?.operatingRequests(skipMemoryCache, diskCacheStrategy, glideRequest)
        ?.into(this@loadTarget)

fun com.bumptech.glide.request.target.Target<Drawable>.loadTarget(
        attach: View,
        source:DataSource?,
        skipMemoryCache:Boolean = GLIDE_SKIP_MEMORY,
        diskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.ALL,
        glideRequest : (GlideRequest<Drawable>) -> GlideRequest<Drawable> = EMPTY)
        = GlideApp.with(attach)
        .checkDataSource(source)
        ?.operatingRequests(skipMemoryCache, diskCacheStrategy, glideRequest)
        ?.into(this@loadTarget)

private fun GlideRequest<Drawable>.operatingRequests(
        skipMemoryCache:Boolean = GLIDE_SKIP_MEMORY,
        diskCacheStrategy: DiskCacheStrategy = DiskCacheStrategy.ALL,
        glideRequest : (GlideRequest<Drawable>) -> GlideRequest<Drawable>): GlideRequest<Drawable>
        = this@operatingRequests
        .skipMemoryCache(skipMemoryCache)
        .diskCacheStrategy(diskCacheStrategy)
        .let { glideRequest(it) }

// 如果activity被销毁就返回null 不再加载图片
private val checkDestroyed: Activity.() -> GlideRequests? = activity@{ if(this@activity.isDestroyed) null else GlideApp.with(this@activity)}

// 如果数据源是null,Glide将不再加载
private val checkDataSource: GlideRequests.(DataSource?) -> GlideRequest<Drawable>? = request@{ source ->
    source?.takeIf { it.checkType() }?.let { this@request.load(source) }
}

private typealias DataSource = Any

private val EMPTY: (GlideRequest<Drawable>) -> GlideRequest<Drawable> = { o -> o}

private fun DataSource.checkType():Boolean = when(this@checkType){
    is File -> true
    is String -> true
    is ByteArray -> true
    is Uri -> true
    is Int -> true
    is Bitmap -> true
    is Drawable -> true
    else -> false
}