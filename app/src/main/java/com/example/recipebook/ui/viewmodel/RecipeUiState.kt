package com.example.recipebook.ui.viewmodel

import com.example.recipebook.data.Recipe
import com.example.recipebook.data.RecipeState

data class RecipeUiState(

    val allRecipes: List<Recipe> = emptyList(),

    val recipes: List<Recipe> = emptyList(),

    val searchQuery: String = "",

    val selectedFilter: RecipeState? = null
)