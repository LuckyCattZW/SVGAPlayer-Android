package com.example.ponycui_home.svgaplayer

import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import java.io.File

@GlideModule
class FlowerWordGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        builder.setLogLevel(Log.DEBUG)
        builder.setMemoryCache(LruResourceCache(GLIDE_CACHE_MEMORY))
        builder.setBitmapPool(LruBitmapPool(GLIDE_LRU_POOL_SIZE))
        builder.setDiskCache(
                InternalCacheDiskCacheFactory(
                        context,
                        context.getDiskCacheName(),
                        GLIDE_DISK_CACHE_SIZE
                )
        )
        builder.setDefaultRequestOptions(RequestOptions().format(DecodeFormat.PREFER_ARGB_8888).disallowHardwareConfig())
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
//        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory())
    }

    //设置缓存目录
    private fun Context.getDiskCacheName(): String {
        val dirFile = File(cacheDir.path + GLIDE_CACHE_PATH)
        val tempFile = File(dirFile, "bitmaps")
        if (!tempFile.parentFile.exists()) {
            tempFile.parentFile.mkdirs()
        }
        return tempFile.path.toString()
    }
}

const val GLIDE_CACHE_MEMORY = 1024L * 1024L * 36L //36MB
const val GLIDE_DISK_CACHE_SIZE = GLIDE_CACHE_MEMORY.shl(2) // 36MB << 2 = 144MB
const val GLIDE_CACHE_PATH = ".GlideCache"
const val GLIDE_LRU_POOL_SIZE = 3L
const val GLIDE_SKIP_MEMORY = false