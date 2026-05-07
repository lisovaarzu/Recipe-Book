package com.example.recipebook.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.recipebook.data.FakeRepository
import com.example.recipebook.data.Recipe
import com.example.recipebook.data.RecipeState

class RecipeViewModel(
    recipes: List<Recipe> = FakeRepository.recipes
) : ViewModel() {

    var uiState by mutableStateOf(
        RecipeUiState(
            allRecipes = recipes,
            recipes = recipes
        )
    )
        private set

    fun applyFilters(query: String, filter: RecipeState?) {

        val filtered = uiState.allRecipes.filter { recipe ->

            val matchesSearch =
                recipe.title.contains(query, ignoreCase = true)

            val matchesFilter =
                filter == null || recipe.state == filter

            matchesSearch && matchesFilter
        }

        uiState = uiState.copy(
            recipes = filtered,
            searchQuery = query,
            selectedFilter = filter
        )
    }

    fun onSearch(query: String) {
        applyFilters(query, uiState.selectedFilter)
    }

    fun onFilterChange(filter: RecipeState?) {
        applyFilters(uiState.searchQuery, filter)
    }

    fun changeState(recipeId: Int, newState: RecipeState) {

        val updatedRecipes = uiState.allRecipes.map { recipe ->

            if (recipe.id == recipeId) {
                recipe.copy(state = newState)
            } else {
                recipe
            }
        }

        val filtered = updatedRecipes.filter { recipe ->

            val matchesSearch =
                recipe.title.contains(
                    uiState.searchQuery,
                    ignoreCase = true
                )

            val matchesFilter =
                uiState.selectedFilter == null ||
                        recipe.state == uiState.selectedFilter

            matchesSearch && matchesFilter
        }

        uiState = uiState.copy(
            allRecipes = updatedRecipes,
            recipes = filtered
        )
    }

    fun getRecipeById(id: Int): Recipe? {
        return uiState.allRecipes.find { recipe ->
            recipe.id == id
        }
    }
}