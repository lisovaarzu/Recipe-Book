package com.example.recipebook.navigation

import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.example.recipebook.ui.screens.*
import com.example.recipebook.ui.viewmodel.RecipeViewModel

@Composable
fun NavGraph(viewModel: RecipeViewModel) {

    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsState()

    NavHost(navController = navController, startDestination = "list") {

        composable("list") {

            RecipeListScreen(
                uiState = uiState,
                onSearch = { viewModel.onSearch(it) },
                onFilterChange = { viewModel.applyFilters(uiState.searchQuery, it) },
                onRecipeClick = {
                    navController.navigate("details/$it")
                }
            )
        }

        composable("details/{recipeId}") { backStackEntry ->

            val recipeId = backStackEntry.arguments
                ?.getString("recipeId")
                ?.toInt() ?: 0

            RecipeDetailsScreen(
                recipeId = recipeId,
                uiState = uiState,
                onChangeState = { id, state ->
                    viewModel.changeState(id, state)
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}