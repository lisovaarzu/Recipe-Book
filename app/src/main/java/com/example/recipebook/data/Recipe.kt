package com.example.recipebook.data

data class Recipe(
    val id: Int,
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val time: String,
    val difficulty: String,
    val state: RecipeState
)