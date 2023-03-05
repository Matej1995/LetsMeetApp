package cz.sandera.letsmeet.domain.permission

import androidx.compose.runtime.snapshots.SnapshotStateList

interface PermissionHandler {

    val visiblePermissionDialogQueue: SnapshotStateList<String>

    fun dismissDialog()

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean,
    )
}