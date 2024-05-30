package ch.allianceswisspass.beaconsdk.example.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun HomeMenu(navController: NavHostController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(HomeMenuItem.entries) {
            HomeMenuItemTile(
                menuItem = it,
                navController = navController,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeMenuItemTile(menuItem: HomeMenuItem, navController: NavHostController) {
    Card(
        modifier = Modifier.aspectRatio(1f),
        onClick = {
            menuItem.onClick(navController)
        },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Icon(
                painter = painterResource(id = menuItem.icon),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .padding(bottom = 16.dp),
            )
            Text(
                text = stringResource(id = menuItem.label),
                textAlign = TextAlign.Center,
            )
        }
    }
}
