package com.himelz.shopper.ui.feature.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.himelz.domain.model.Categories
import com.himelz.domain.model.Product
import com.himelz.shopper.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavController, viewmodel: HomeViewModel = koinViewModel()) {

    val uiState by viewmodel.uiState.collectAsState()
    var search by remember { mutableStateOf("") }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        item {
            ProfileHeader()
            Spacer(modifier = Modifier.height(18.dp))
        }
        item {
            SearchBar(
                value = search,
                onValueChange = { search = it }
            )
        }
        item {
            Card(
                modifier = Modifier
                    .padding(start = 18.dp, top = 14.dp, bottom = 20.dp, end = 18.dp)
                    .fillMaxWidth()
                    .height(115.dp),
            ) {
                AsyncImage(
                    model = "https://images.remotehub.com/d42c62669a7711eb91397e038280fee0/original_thumb/ec1eb042.jpg?version=1618112516",
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.ic_launcher_background),
                    error = painterResource(R.drawable.ic_launcher_foreground)
                )
            }
        }

        when (uiState) {
            is HomeScreenUIEvents.Loading -> {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            strokeWidth = 2.dp,
                            modifier = Modifier
                                .padding(16.dp)
                                .size(36.dp)
                        )
                    }
                }
            }

            is HomeScreenUIEvents.Error -> {
                item {
                    Text(
                        text = "Error: ${(uiState as HomeScreenUIEvents.Error).message}",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            is HomeScreenUIEvents.Success -> {
                val data = uiState as HomeScreenUIEvents.Success

                if (data.categories.isNotEmpty()) {
                    item {
                        HomeCategoryRow(
                            products = data.categories
                        )
                    }
                }
                if (data.popularProducts.isNotEmpty()) {
                    item {
                        HomeProductRow(
                            products = data.featuredProducts,
                            title = "Featured Products"
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                if (data.featuredProducts.isNotEmpty()) {
                    item {
                        HomeProductRow(
                            products = data.popularProducts,
                            title = "Popular Products"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(start = 16.dp, end = 16.dp),
    ) {
        AsyncImage(
            model = "https://i.imgur.com/LDOO4Qs.jpg",
            contentDescription = null,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .padding(3.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_launcher_background),
            error = painterResource(R.drawable.ic_launcher_foreground)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "John Doe",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "View Profile",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 2.dp, start = 4.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        IconButton(
            onClick = { /* TODO: Handle click */ },
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp)
                .background(
                    if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primaryContainer.copy(
                        alpha = 0.1f
                    ) else Color.LightGray.copy(alpha = 0.1f)
                )
        ) {
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = null,
                tint = if (isSystemInDarkTheme()) Color.White else MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Composable
fun SearchBar(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        placeholder = {
            Text(
                text = "Search a product...",
                style = MaterialTheme.typography.bodySmall
            )
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = CircleShape,
        leadingIcon = {
            Icon(
                Icons.Filled.Search,
                contentDescription = null,
                tint = if (isSystemInDarkTheme()) Color.White else MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 8.dp)
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        visualTransformation = VisualTransformation.None

    )
    Spacer(modifier = Modifier.height(18.dp))
}

@Composable
fun HomeCategoryRow(products: List<String>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 14.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(products) { product ->
            CategoryItem(product = product)
        }
    }
}

@Composable
fun CategoryItem(product: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(4.dp)
    ) {
        Text(
            text = product,
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.SemiBold,
            )
        )
    }
}

@Composable
fun HomeProductRow(products: List<Product>, title: String) {
    val context = LocalContext.current
    Column {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = "View all",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    Toast.makeText(context, "View all clicked", Toast.LENGTH_SHORT).show()
                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 14.dp)
        ) {
            items(products) { product ->
                ProductItem(
                    product = product,
                    onClick = {
                        Toast.makeText(context, product.title, Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}


@Composable
fun ProductItem(product: Product, onClick: () -> Unit = {}) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(width = 140.dp, height = 165.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(105.dp)
            ) {
                if (product.image.isNotEmpty()) {
                    AsyncImage(
                        model = product.image,
                        contentDescription = product.title,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(R.drawable.ic_launcher_background),
                        error = painterResource(R.drawable.ic_launcher_foreground)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            // Display a loading bar when loading

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 8.dp),
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = product.priceString,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}