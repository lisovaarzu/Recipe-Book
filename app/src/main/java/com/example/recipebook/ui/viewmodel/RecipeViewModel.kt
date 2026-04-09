package com.example.recipebook.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.recipebook.data.*

class RecipeViewModel : ViewModel() {

    private var allRecipes = FakeRepository.recipes.toMutableList()

    private val _uiState = MutableStateFlow(
        RecipeUiState(recipes = allRecipes)
    )

    val uiState: StateFlow<RecipeUiState> = _uiState

    fun applyFilters(query: String, filter: RecipeState?) {

        val filtered = allRecipes.filter { recipe ->

            val matchesSearch = recipe.title.contains(query, true)
            val matchesFilter = filter == null || recipe.state == filter

            matchesSearch && matchesFilter
        }

        _uiState.value = RecipeUiState(
            recipes = filtered,
            searchQuery = query,
            selectedFilter = filter
        )
    }

    fun onSearch(query: String) {
        applyFilters(query, _uiState.value.selectedFilter)
    }

    fun changeState(recipeId: Int, newState: RecipeState) {

        allRecipes = allRecipes.map {
            if (it.id == recipeId) it.copy(state = newState) else it
        }.toMutableList()

        applyFilters(_uiState.value.searchQuery, _uiState.value.selectedFilter)
    }
}