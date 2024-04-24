package com.mckimquyen.reader.ui.theme.palette.colorspace.rgb.transfer

interface TransferFunction {

    // nonlinear -> linear
    fun EOTF(x: Double): Double

    // linear -> nonlinear
    fun OETF(x: Double): Double
}
