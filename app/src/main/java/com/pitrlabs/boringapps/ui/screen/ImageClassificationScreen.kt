package com.pitrlabs.boringapps.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.pitrlabs.boringapps.ui.ImageClassificationViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@Composable
fun ImageClassificationScreen() {
    val viewModel: ImageClassificationViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedImageFile by remember { mutableStateOf<File?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri

        uri?.let {
            val inputStream: InputStream? = context.contentResolver.openInputStream(it)
            val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir)
            val outputStream = FileOutputStream(tempFile)
            inputStream?.copyTo(outputStream)
            outputStream.close()
            inputStream?.close()
            selectedImageFile = tempFile
        }
    }

    Box(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxSize()
            .wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .width(320.dp)
                .wrapContentHeight()
                .clip(RoundedCornerShape(20.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.25f),
                            Color.White.copy(alpha = 0.15f),
                            Color.White.copy(alpha = 0.10f)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(400f, 400f)
                    )
                )
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.4f),
                            Color.White.copy(alpha = 0.1f)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(300f, 300f)
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .verticalScroll(rememberScrollState())
        ) {
            selectedImageUri?.let {
                SubcomposeAsyncImage(
                    model = it,
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentScale = ContentScale.FillWidth,
                    loading = {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    },
                    error = {
                        Text("Failed to load image", color = Color.Red)
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlassButton(text = "Choose Image") {
                    launcher.launch("image/*")
                }

                Spacer(modifier = Modifier.width(16.dp))

                GlassButton(
                    text = "Classify",
                    enabled = selectedImageFile != null
                ) {
                    selectedImageFile?.let { file ->
                        viewModel.classifyImage(file)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 3.dp
                        )
                    }
                }

                uiState.error != null -> {
                    Text(
                        text = "Oops! Something went wrong euy:\n${uiState.error}",
                        color = Color(0xFFD32F2F),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }

                uiState.results.isNotEmpty() -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp, start = 10.dp, end = 10.dp, bottom = 5.dp)
                    ) {
                        Text(
                            text = "Prediction Result:",
                            fontSize = 18.sp,
                            color = Color(0xFF68E1FD)
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        uiState.results.forEach {
                            Box(
                                modifier = Modifier
                                    .width(320.dp)
                                    .wrapContentHeight()
                                    .clip(RoundedCornerShape(15.dp))
                                    .background(
                                        brush = Brush.linearGradient(
                                            colors = listOf(
                                                Color.White.copy(alpha = 0.25f),
                                                Color.White.copy(alpha = 0.15f),
                                                Color.White.copy(alpha = 0.10f)
                                            ),
                                            start = Offset(0f, 0f),
                                            end = Offset(400f, 400f)
                                        )
                                    )
                                    .border(
                                        width = 1.dp,
                                        brush = Brush.linearGradient(
                                            colors = listOf(
                                                Color.White.copy(alpha = 0.4f),
                                                Color.White.copy(alpha = 0.1f)
                                            ),
                                            start = Offset(0f, 0f),
                                            end = Offset(300f, 300f)
                                        ),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .padding(12.dp) // padding isi box
                            ) {
                                Text(
                                    text = "Object ${it.clazz} have ${it.confidence}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color(0xFF68E1FD)
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GlassButton(
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(20.dp)

    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White.copy(alpha = 0.2f),
            contentColor = Color.Black
        ),
        modifier = Modifier
            .height(44.dp)
            .clip(shape)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.50f),
                        Color.White.copy(alpha = 0.30f),
                        Color.White.copy(alpha = 0.16f)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(300f, 300f)
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.6f),
                        Color.White.copy(alpha = 0.2f)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(200f, 200f)
                ),
                shape = shape
            )

    ) {
        Text(text = text)
    }
}
