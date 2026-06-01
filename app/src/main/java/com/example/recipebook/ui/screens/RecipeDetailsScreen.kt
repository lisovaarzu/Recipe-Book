package com.example.recipebook.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.recipebook.data.Recipe
import com.example.recipebook.data.RecipeState
import com.example.recipebook.ui.theme.CardBackground
import com.example.recipebook.ui.theme.DarkBackground
import com.example.recipebook.ui.theme.RedAccent
import com.example.recipebook.ui.theme.RedPrimary
import com.example.recipebook.ui.theme.RedSecondary
import com.example.recipebook.ui.theme.TextSecondary

@Composable
fun RecipeDetailsScreen(
    recipeId: Int,
    recipe: Recipe?,
    onChangeState: (Int, RecipeState) -> Unit,
    onBack: () -> Unit
) {
    Box(
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
        if (recipe == null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardBackground
                ),
                border = BorderStroke(1.dp, RedPrimary)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Рецепт не найден",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onBack,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RedPrimary
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Назад")
                    }
                }
            }
            return@Box
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CardBackground,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                border = BorderStroke(1.dp, RedPrimary),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Назад")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardBackground
                ),
                border = BorderStroke(1.dp, RedPrimary),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                )
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = recipe.title,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    StatusBadge(recipe.state)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = recipe.description,
                        color = TextSecondary
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = "Ингредиенты",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    recipe.ingredients.forEach { ingredient ->
                        Text(
                            text = "• $ingredient",
                            color = TextSecondary
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = DarkBackground
                        ),
                        border = BorderStroke(1.dp, RedSecondary)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "Время: ${recipe.time}",
                                color = TextSecondary
                            )

                            Text(
                                text = "Сложность: ${recipe.difficulty}",
                                color = TextSecondary
                            )

                            Text(
                                text = "Текущий статус: ${statusText(recipe.state)}",
                                color = TextSecondary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Изменить статус",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Column {
                        StatusButton(
                            text = "Хочу приготовить",
                            selected = recipe.state == RecipeState.WANT_TO_COOK
                        ) {
                            onChangeState(recipe.id, RecipeState.WANT_TO_COOK)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        StatusButton(
                            text = "Готовлю сейчас",
                            selected = recipe.state == RecipeState.COOKING
                        ) {
                            onChangeState(recipe.id, RecipeState.COOKING)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        StatusButton(
                            text = "Уже готово",
                            selected = recipe.state == RecipeState.COOKED
                        ) {
                            onChangeState(recipe.id, RecipeState.COOKED)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) RedAccent else DarkBackground,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (selected) RedAccent else RedPrimary
        )
    ) {
        Text(text)
    }
}

fun statusText(state: RecipeState): String {
    return when (state) {
        RecipeState.WANT_TO_COOK -> "Хочу приготовить"
        RecipeState.COOKING -> "Готовлю сейчас"
        RecipeState.COOKED -> "Уже готово"
    }
}