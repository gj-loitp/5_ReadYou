

package com.roy93group.reader.ui.theme.palette.colorspace.rgb.transferfunction

interface TransferFunction {

    // nonlinear -> linear
    fun EOTF(x: Double): Double

    // linear -> nonlinear
    fun OETF(x: Double): Double
}
