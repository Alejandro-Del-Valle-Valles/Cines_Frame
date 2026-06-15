package com.alejandro.proyecto_cines_frame.ui.components.profileAndTickets

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Compra
import com.alejandro.proyecto_cines_frame.domain.model.LineaCompraEntrada
import com.alejandro.proyecto_cines_frame.domain.model.LineaCompraProducto
import com.alejandro.proyecto_cines_frame.ui.theme.ColorFondoHeader
import com.alejandro.proyecto_cines_frame.ui.theme.TextWhite
import kotlinx.datetime.LocalDateTime
import qrcode.QRCode
import qrcode.QRCode.Companion.ofSquares

data class QrCodeMatrix(
    val size: Int,
    val modules: BooleanArray
) {
    fun isDark(row: Int, column: Int): Boolean = modules[row * size + column]
}

fun generatePurchaseQrMatrix(content: String): QrCodeMatrix? = runCatching {
    val qrCode: QRCode = ofSquares().build(content)
    val rawData = qrCode.rawData
    val size = rawData.size
    val modules = BooleanArray(size * size)

    rawData.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { columnIndex, square ->
            modules[rowIndex * size + columnIndex] = square.dark
        }
    }

    QrCodeMatrix(size = size, modules = modules)
}.getOrNull()

fun Compra.buildQrPayload(movieTitlesById: Map<String, String>): String {
    val entradas = lineasCompra.filterIsInstance<LineaCompraEntrada>()
    val productos = lineasCompra.filterIsInstance<LineaCompraProducto>()
    val entradaPrincipal = entradas.firstOrNull()?.entrada
    val peliculaId = entradaPrincipal?.peliculaId
    val tituloPelicula = peliculaId?.let(movieTitlesById::get) ?: "Pelicula no disponible"
    val fecha = entradaPrincipal?.horario?.formatQrDate() ?: "Sin fecha"
    val sala = entradaPrincipal?.numSala?.toString() ?: "-"
    val total = calcularTotalCompra()

    return buildString {
        appendLine("CINES_FRAME_COMPRA")
        appendLine("id=$id")
        appendLine("usuario=${usuario.correo}")
        appendLine("fecha=$fecha")
        appendLine("pelicula=$tituloPelicula")
        appendLine("sala=$sala")

        if (entradas.isNotEmpty()) {
            appendLine(
                "entradas=${entradas.joinToString(separator = " | ") { entrada ->
                    "${entrada.entrada.tipo.nombre},fila=${entrada.entrada.fila},butaca=${entrada.entrada.butaca},precio=${entrada.entrada.tipo.precio}"
                }}"
            )
        } else {
            appendLine("entradas=sin_entradas")
        }

        if (productos.isNotEmpty()) {
            appendLine(
                "productos=${productos.groupBy { it.producto.nombre }.entries.joinToString(separator = " | ") { (nombre, lineas) ->
                    val cantidad = lineas.size
                    val precioUnidad = lineas.first().producto.precio
                    val totalProducto = cantidad * precioUnidad
                    "$nombre,x$cantidad,precio=$precioUnidad,total=$totalProducto"
                }}"
            )
        } else {
            appendLine("productos=sin_productos")
        }

        appendLine("total=$total")
        appendLine("verifica_este_qr_en_tu_compra")
    }
}

fun Compra.buildQrSummaryLines(movieTitlesById: Map<String, String>): List<String> {
    val entradas = lineasCompra.filterIsInstance<LineaCompraEntrada>()
    val productos = lineasCompra.filterIsInstance<LineaCompraProducto>()
    val entradaPrincipal = entradas.firstOrNull()?.entrada
    val peliculaId = entradaPrincipal?.peliculaId
    val tituloPelicula = peliculaId?.let(movieTitlesById::get) ?: "Pelicula no disponible"
    val fecha = entradaPrincipal?.horario?.formatQrDate() ?: "Sin fecha"
    val total = calcularTotalCompra()

    val entradasResumen = if (entradas.isEmpty()) {
        "Entradas: no hay entradas"
    } else {
        val detalles = entradas.joinToString(separator = "; ") {
            "F${it.entrada.fila} A${it.entrada.butaca} (${it.entrada.tipo.nombre})"
        }
        "Entradas: $detalles"
    }

    val productosResumen = if (productos.isEmpty()) {
        "Productos: ninguno"
    } else {
        val detalles = productos.groupBy { it.producto.nombre }.entries.joinToString(separator = "; ") { (nombre, lineas) ->
            "$nombre x${lineas.size}"
        }
        "Productos: $detalles"
    }

    return listOf(
        "Compra #$id",
        "Usuario: ${usuario.correo}",
        "Fecha: $fecha",
        "Película: $tituloPelicula",
        entradasResumen,
        productosResumen,
        "Total: $total€"
    )
}

private fun Compra.calcularTotalCompra(): Float {
    return lineasCompra.sumOf {
        when (it) {
            is LineaCompraEntrada -> it.entrada.tipo.precio.toDouble()
            is LineaCompraProducto -> it.producto.precio.toDouble()
            else -> 0.0
        }
    }.toFloat()
}

private fun LocalDateTime.formatQrDate(): String =
    "${year.toString().padStart(4, '0')}-${monthNumber.toString().padStart(2, '0')}-${dayOfMonth.toString().padStart(2, '0')}T${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"

@Composable
fun CompraQrCard(
    compra: Compra,
    movieTitlesById: Map<String, String>,
    modifier: Modifier = Modifier
) {
    val qrPayload = remember(compra.id, movieTitlesById) {
        compra.buildQrPayload(movieTitlesById)
    }
    val qrMatrix = remember(qrPayload) {
        generatePurchaseQrMatrix(qrPayload)
    }
    val summaryLines = remember(compra.id, movieTitlesById) {
        compra.buildQrSummaryLines(movieTitlesById)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(ColorFondoHeader, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text(
            text = "QR de la compra",
            color = TextWhite,
            style = MaterialTheme.typography.titleSmall
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (qrMatrix != null) {
                QrMatrixView(
                    matrix = qrMatrix,
                    modifier = Modifier.size(200.dp)
                )
            } else {
                Text(
                    text = "No se pudo generar el QR.",
                    color = TextWhite
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                summaryLines.take(3).forEach { line ->
                    Text(
                        text = line,
                        color = TextWhite,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Al escanearlo se mostrará la información de la compra.",
                    color = TextWhite,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun QrMatrixView(
    matrix: QrCodeMatrix,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        drawQrMatrix(matrix)
    }
}

private fun DrawScope.drawQrMatrix(matrix: QrCodeMatrix) {
    drawRect(color = Color.White)

    if (matrix.size <= 0) return

    val cellSize = size.minDimension / matrix.size.toFloat()
    val offsetX = (size.width - cellSize * matrix.size) / 2f
    val offsetY = (size.height - cellSize * matrix.size) / 2f

    for (row in 0 until matrix.size) {
        for (column in 0 until matrix.size) {
            if (matrix.isDark(row, column)) {
                drawRect(
                    color = Color.Black,
                    topLeft = Offset(
                        x = offsetX + column * cellSize,
                        y = offsetY + row * cellSize
                    ),
                    size = Size(cellSize, cellSize)
                )
            }
        }
    }
}