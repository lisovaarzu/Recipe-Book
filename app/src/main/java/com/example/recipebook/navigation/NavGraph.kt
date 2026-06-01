package com.example.recipebook.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipebook.ui.screens.RecipeDetailsScreen
import com.example.recipebook.ui.screens.RecipeListScreen
import com.example.recipebook.ui.viewmodel.RecipeViewModel

@Composable
fun NavGraph(viewModel: RecipeViewModel) {

    val navController = rememberNavController()
    val uiState = viewModel.uiState

    NavHost(navController = navController, startDestination = "list") {

        composable("list") {
            RecipeListScreen(
                uiState = uiState,
                onSearch = { viewModel.onSearch(it) },
                onFilterChange = { viewModel.onFilterChange(it) },
                onRecipeClick = {
                    navController.navigate("details/$it")
                }
            )
        }

        composable(
            route = "details/{recipeId}",
            arguments = listOf(
                navArgument("recipeId") { type = NavType.IntType }
            )
        ) { backStackEntry ->

            val recipeId = backStackEntry.arguments?.getInt("recipeId")
                ?: return@composable

            val recipe = viewModel.getRecipeById(recipeId)
                ?: return@composable

            RecipeDetailsScreen(
                recipeId = recipeId,
                recipe = recipe,
                onChangeState = { id, state ->
                    viewModel.changeState(id, state)
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}