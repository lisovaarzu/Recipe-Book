package com.example.recipebook.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.example.recipebook.data.RecipeState
import com.example.recipebook.ui.theme.CardBackground
import com.example.recipebook.ui.theme.CookedStatusColor
import com.example.recipebook.ui.theme.CookingStatusColor
import com.example.recipebook.ui.theme.DarkBackground
import com.example.recipebook.ui.theme.RedAccent
import com.example.recipebook.ui.theme.RedPrimary
import com.example.recipebook.ui.theme.RedSecondary
import com.example.recipebook.ui.theme.TextPrimary
import com.example.recipebook.ui.theme.TextSecondary
import com.example.recipebook.ui.theme.WantStatusColor
import com.example.recipebook.ui.viewmodel.RecipeUiState

@Composable
fun RecipeListScreen(
    uiState: RecipeUiState,
    onSearch: (String) -> Unit,
    onFilterChange: (RecipeState?) -> Unit,
    onRecipeClick: (Int) -> Unit
) {
    val wantCount = uiState.allRecipes.count { it.state == RecipeState.WANT_TO_COOK }
    val cookingCount = uiState.allRecipes.count { it.state == RecipeState.COOKING }
    val cookedCount = uiState.allRecipes.count { it.state == RecipeState.COOKED }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        DarkBackground,
                        RedSecondary,
                        DarkBackground
                    )
                )
            )
            .padding(16.dp)
    ) {
        Text(
            text = "Recipe Book",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Найди рецепт и отметь, что хочешь приготовить",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = onSearch,
            label = { Text("Поиск рецепта") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(18.dp),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = RedAccent,
                unfocusedBorderColor = RedPrimary,
                focusedLabelColor = RedAccent,
                unfocusedLabelColor = TextSecondary,
                cursorColor = RedAccent,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                focusedContainerColor = CardBackground,
                unfocusedContainerColor = CardBackground
            )
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            FilterButton("Все", uiState.selectedFilter == null) {
                onFilterChange(null)
            }

            FilterButton("Хочу", uiState.selectedFilter == RecipeState.WANT_TO_COOK) {
                onFilterChange(RecipeState.WANT_TO_COOK)
            }

            FilterButton("Готовлю", uiState.selectedFilter == RecipeState.COOKING) {
                onFilterChange(RecipeState.COOKING)
            }

            FilterButton("Готово", uiState.selectedFilter == RecipeState.COOKED) {
                onFilterChange(RecipeState.COOKED)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = CardBackground
            ),
            border = BorderStroke(1.dp, RedPrimary)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Статистика",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("Хочу приготовить: $wantCount", color = TextSecondary)
                Text("Готовлю сейчас: $cookingCount", color = TextSecondary)
                Text("Уже готово: $cookedCount", color = TextSecondary)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (uiState.recipes.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardBackground
                ),
                border = BorderStroke(1.dp, RedPrimary)
            ) {
                Text(
                    text = "Рецепты не найдены",
                    modifier = Modifier.padding(16.dp),
                    color = TextSecondary
                )
            }
        } else {
            LazyColumn {
                items(
                    items = uiState.recipes,
                    key = { recipe -> recipe.id }
                ) { recipe ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                            .clickable {
                                onRecipeClick(recipe.id)
                            },
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = CardBackground
                        ),
                        border = BorderStroke(1.dp, RedPrimary),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = recipe.title,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Время: ${recipe.time}",
                                color = TextSecondary
                            )

                            Text(
                                text = "Сложность: ${recipe.difficulty}",
                                color = TextSecondary
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            StatusBadge(recipe.state)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FilterButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) RedAccent else CardBackground,
            contentColor = TextPrimary
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (isSelected) RedAccent else RedPrimary
        ),
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Text(text)
    }
}

@Composable
fun StatusBadge(state: RecipeState) {
    val text = when (state) {
        RecipeState.WANT_TO_COOK -> "Хочу приготовить"
        RecipeState.COOKING -> "Готовлю"
        RecipeState.COOKED -> "Готово"
    }

    val color = when (state) {
        RecipeState.WANT_TO_COOK -> WantStatusColor
        RecipeState.COOKING -> CookingStatusColor
        RecipeState.COOKED -> CookedStatusColor
    }

    Surface(
        shape = RoundedCornerShape(50),
        color = color
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
    }
}