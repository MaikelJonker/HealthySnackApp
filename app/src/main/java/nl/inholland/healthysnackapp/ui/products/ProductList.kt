package nl.inholland.healthysnackapp.ui.products

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.QrCode
import androidx.compose.material.icons.outlined.QrCode2
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material.icons.outlined.SettingsOverscan
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nl.inholland.healthysnackapp.ui.cells.BarcodeScannerDialog
import nl.inholland.healthysnackapp.ui.cells.SearchBar

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
    Column(

    ) {
        SearchBar("Producten", "Welk product zoek je?", Modifier.height(100.dp))
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
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .size(100.dp),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(width = 1.dp, color = Color.White)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.QrCodeScanner,
                        contentDescription = "Scan Barcode",
                        tint = Color.White,
                        modifier = Modifier
                            .size(50.dp)
                    )
                }
            }

            if (cameraPermissionState.value) {
                BarcodeScannerDialog(onDismiss = {
                    cameraPermissionState.value = false
                }) { barcodeData ->
                    coroutineScope.launch(Dispatchers.Main) {
                        println("Scanned Data: $barcodeData")
                        toProductDetail(barcodeData)
                    }
                }
            }
        }
    }
}
