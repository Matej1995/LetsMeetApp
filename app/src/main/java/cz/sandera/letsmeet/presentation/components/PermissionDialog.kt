package cz.sandera.letsmeet.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import cz.sandera.letsmeet.R
import cz.sandera.letsmeet.presentation.util.PermissionTextProvider
import cz.sandera.letsmeet.ui.theme.spacing

@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onSuccessClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(id = R.string.dialog_requirePermission))
        },
        buttons = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Divider()
                Text(
                    text = if (isPermanentlyDeclined) {
                        stringResource(id = R.string.dialog_grantPermission)
                    } else {
                        stringResource(id = R.string.general_ok)
                    },
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isPermanentlyDeclined) {
                                onGoToAppSettingsClick()
                            } else {
                                onSuccessClick()
                            }
                        }
                        .padding(MaterialTheme.spacing.medium)
                )
            }
        },
        text = {
            Text(
                text = permissionTextProvider.getDescription(
                    isPermanentlyDeclined = isPermanentlyDeclined
                )
            )
        },
        modifier = modifier
    )
}