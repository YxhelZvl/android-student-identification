package com.yxhelzvl.umsnh_id.features.qrcreate.ui

import com.yxhelzvl.umsnh_id.features.home.ui.HomeEvent

sealed class QrCreateEvent() {
    data class SetError(val error: String?) : QrCreateEvent()
    data class SetId_Estudiante(val id_student: String) : QrCreateEvent()
    object LoadDataStudent : QrCreateEvent()
    object GenerarQr : QrCreateEvent()
    object NavigateToBack : QrCreateEvent()
}