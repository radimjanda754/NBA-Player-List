package net.rjanda.casestudy.nba.navigation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import net.rjanda.casestudy.nba.player.navigation.PlayerDetailDestination
import net.rjanda.casestudy.nba.player.navigation.PlayerListDestination
import net.rjanda.casestudy.nba.player.navigation.PlayerTeamDestination
import net.rjanda.casestudy.nba.player.screen.detail.PlayerDetailScreen
import net.rjanda.casestudy.nba.player.screen.list.PlayerListScreen
import net.rjanda.casestudy.nba.player.screen.team.PlayerTeamScreen

@Composable
fun MainNavigation() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
        Surface {
            val navController: NavHostController = rememberNavController()
            NavHost(navController = navController, startDestination = PlayerListDestination) {
                composable<PlayerListDestination> {
                    PlayerListScreen(navigateToDetails = { objectId ->
                        navController.navigate(PlayerDetailDestination(objectId))
                    })
                }
                composable<PlayerDetailDestination> { backStackEntry ->
                    PlayerDetailScreen(
                        playerId = backStackEntry.toRoute<PlayerDetailDestination>().playerId,
                        navigateToTeamDetails = { objectId ->
                            navController.navigate(PlayerTeamDestination(objectId))
                        },
                        navigateBack = {
                            navController.popBackStack()
                        }
                    )
                }
                composable<PlayerTeamDestination> { backStackEntry ->
                    PlayerTeamScreen(
                        playerId = backStackEntry.toRoute<PlayerTeamDestination>().playerId,
                        navigateBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
