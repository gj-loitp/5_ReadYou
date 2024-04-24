@file:Suppress("SpellCheckingInspection")

package com.mckimquyen.reader.ui.ext

import com.mckimquyen.reader.BuildConfig

const val GITHUB = "github"
const val FDROID = "fdroid"
const val isFdroid = BuildConfig.FLAVOR == FDROID
const val notFdroid = !isFdroid
