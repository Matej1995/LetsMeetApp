package cz.sandera.letsmeet.presentation.setting.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import cz.sandera.letsmeet.ui.theme.spacing

@Composable
fun SettingTabRow(
    @StringRes title: Int,
    tabBackgroundColor: Color,
    selectedColor: Color,
    textColor: Color,
    tabList: List<String>,
    valueIsSelected: Int,
    onClickAction: (selected: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedIndex by rememberSaveable { mutableStateOf(valueIsSelected) }

    Column(modifier) {
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.h5,
            color = textColor,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
        TabRow(
            selectedTabIndex = selectedIndex,
            backgroundColor = tabBackgroundColor,
            modifier = modifier
                .padding(horizontal = MaterialTheme.spacing.small)
                .clip(RoundedCornerShape(50))
                .padding(MaterialTheme.spacing.extraSmall)
                .border(
                    width = MaterialTheme.spacing.extraSmall,
                    tabBackgroundColor
                ),
        ) {
            tabList.forEachIndexed { index, text ->
                val selected = selectedIndex == index
                Tab(
                    selected = selected,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(if (selected) selectedColor else tabBackgroundColor),
                    onClick = {
                        selectedIndex = index
                        onClickAction(selectedIndex)
                    },
                    text = {
                        Text(
                            text = text,
                            color = textColor
                        )
                    }
                )
            }
        }
    }
}