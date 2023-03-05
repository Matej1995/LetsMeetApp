package cz.sandera.letsmeet.data.permission

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import cz.sandera.letsmeet.domain.permission.PermissionHandler

class PermissionHandlerImpl : PermissionHandler {

    override val visiblePermissionDialogQueue: SnapshotStateList<String> = mutableStateListOf()

    override fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    override fun onPermissionResult(
        permission: String,
        isGranted: Boolean,
    ) {
        if (!isGranted && !visiblePermissionDialogQueue.contains(permission)) {
            visiblePermissionDialogQueue.add(permission)
        }
    }
}