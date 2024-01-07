

package com.roy93group.reader.ui.theme.palette.dynamic

import com.roy93group.reader.ui.theme.palette.colorspace.zcam.Zcam
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.sign

fun Zcam.harmonizeTowards(
    target: Zcam,
    factor: Double = 0.5,
    maxHueShift: Double = 15.0,
): Zcam = copy(
    hz = hz + (
            ((180.0 - abs(abs(hz - target.hz) - 180.0)) * factor).coerceAtMost(maxHueShift)
            ) * (
            listOf(
                target.hz - hz,
                target.hz - hz + 360.0,
                target.hz - hz - 360.0
            ).minOf {
                it.absoluteValue
            }.sign.takeIf { it != 0.0 } ?: 1.0
            )
)
