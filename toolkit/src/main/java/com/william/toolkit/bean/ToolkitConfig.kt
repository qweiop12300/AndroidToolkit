/*
 * Copyright WeiLianYang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.william.toolkit.bean

import android.graphics.Bitmap
import com.william.toolkit.BuildConfig

/**
 * author：William
 * date：2021/6/14 21:29
 * description：配置数据
 */
class ToolkitConfig(builder: Builder) {

    /**
     * 调试模式
     */
    var debugMode: Boolean = builder.debugMode
    var bmp: Bitmap? = builder.bmp

    class Builder {
        internal var debugMode: Boolean = BuildConfig.DEBUG
        internal var bmp: Bitmap? = null

        fun setDebugMode(debugMode: Boolean) = apply { this.debugMode = debugMode }

        fun setBitMap(bmp: Bitmap) = apply { this.bmp = bmp }

        fun build() = ToolkitConfig(this)
    }
}