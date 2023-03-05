package cz.sandera.letsmeet.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import cz.sandera.letsmeet.ui.theme.spacing

@Composable
fun WeatherDataDisplay(
    value: Int,
    unit: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(),
    iconTine: Color = Color.White,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon, contentDescription = null,
            tint = iconTine,
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
        Text(
            text = "$value $unit",
            style = textStyle,
        )
    }
}