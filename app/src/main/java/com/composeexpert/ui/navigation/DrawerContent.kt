package com.composeexpert.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun DrawerContent(
    drawerOptions: List<NavItem>,
    onOptionsClick: (NavItem) -> Unit,
    selectedIndex: Int,
) {
    ModalDrawerSheet {
        drawerOptions.forEachIndexed { index, navItem ->
            val selected = index == selectedIndex
            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.name,
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = navItem.title),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                selected = selected,
                onClick = { onOptionsClick(navItem) }
            )
        }
    }
}
