package nl.inholland.healthysnackapp.ui.products

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nl.inholland.healthysnackapp.ui.cells.BarcodeScannerDialog
import nl.inholland.healthysnackapp.ui.cells.ProductCell

@Composable
fun ProductList(
    toProductDetail: (String) -> Unit,
    viewModel: ProductViewModel
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.getProductList()
    }

    val product by viewModel.product.collectAsState()

    // State to track camera permission
    val cameraPermissionState = remember { mutableStateOf(false) }

    // Permission launcher
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        cameraPermissionState.value = isGranted
        if (!isGranted) {
            // Show a message if permission is denied
            Toast.makeText(context, "Camera permission is required to scan barcodes", Toast.LENGTH_LONG).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (product != null) {
            Column {
                ProductCell(product!!, modifier = Modifier, toProductDetail = toProductDetail)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 72.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                modifier = Modifier.size(72.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Build,
                    contentDescription = "Scan Barcode",
                    tint = Color.White
                )
            }
        }

        if (cameraPermissionState.value) {
            BarcodeScannerDialog(onDismiss = { cameraPermissionState.value = false }) { barcodeData ->
                coroutineScope.launch(Dispatchers.Main) {
                    println("Scanned Data: $barcodeData")
                    toProductDetail(barcodeData)
                }
            }
        }
    }
}
