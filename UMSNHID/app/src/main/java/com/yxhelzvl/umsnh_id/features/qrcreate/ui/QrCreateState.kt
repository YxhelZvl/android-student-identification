package com.yxhelzvl.umsnh_id.features.qrcreate.ui

import android.graphics.Bitmap
import com.yxhelzvl.umsnh_id.features.home.network.model.Data
import com.yxhelzvl.umsnh_id.features.qrcreate.data.models.Daum

data class QrCreateState (
    val isLoading: Boolean = false,
    val studentData: Daum? = null,
    val id_student: String? = null,
    val error: String? = null,
    val qrBitmap : Bitmap? = null
)
