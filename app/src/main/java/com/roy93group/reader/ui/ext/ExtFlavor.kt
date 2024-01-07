@file:Suppress("SpellCheckingInspection")

package com.roy93group.reader.ui.ext

import com.roy93group.reader.BuildConfig

const val GITHUB = "github"
const val FDROID = "fdroid"
const val isFdroid = BuildConfig.FLAVOR == FDROID
const val notFdroid = !isFdroid
