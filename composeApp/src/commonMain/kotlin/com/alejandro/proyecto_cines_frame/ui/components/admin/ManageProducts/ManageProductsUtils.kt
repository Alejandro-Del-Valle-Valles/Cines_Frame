package com.alejandro.proyecto_cines_frame.ui.components.admin.ManageProducts

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alejandro.proyecto_cines_frame.domain.model.Alergeno
import com.alejandro.proyecto_cines_frame.domain.model.Producto

enum class ManageProductsTab {
    PRODUCTOS,
    ALERGENOS
}

enum class ProductFilter {
    TODOS,
    CON_STOCK,
    SIN_STOCK
}

data class ProductUiModel(
    val nombre: String,
    val precio: Float,
    val stock: Int,
    val alergenos: List<String>,
    val descripcion: String = ""
)

data class AlergenoUiModel(
    val nombre: String
)

data class ProductFormState(
    val nombre: String = "",
    val precio: String = "",
    val stock: String = "",
    val alergenosCsv: String = "",
    val descripcion: String = ""
)

data class AlergenoFormState(
    val nombre: String = ""
)

data class ManageProductsUiState(
    val selectedTab: ManageProductsTab = ManageProductsTab.PRODUCTOS,
    val search: String = "",
    val selectedFilter: ProductFilter = ProductFilter.TODOS,

    val products: List<ProductUiModel> = emptyList(),
    val alergenos: List<AlergenoUiModel> = emptyList(),

    val isProductDialogVisible: Boolean = false,
    val editingProduct: ProductUiModel? = null,
    val productForm: ProductFormState = ProductFormState(),

    val isAlergenoDialogVisible: Boolean = false,
    val editingAlergeno: AlergenoUiModel? = null,
    val alergenoForm: AlergenoFormState = AlergenoFormState()
)

object ManageProductsUtils {

    val PuntoCorteEscritorio: Dp = 700.dp

    fun esEscritorio(maxWidth: Dp): Boolean {
        return maxWidth >= PuntoCorteEscritorio
    }

    fun Producto.toUiModel(): ProductUiModel {
        return ProductUiModel(
            nombre = nombre,
            precio = precio,
            stock = stock,
            alergenos = alergenos.map { it.nombre },
            descripcion = ""
        )
    }

    fun Alergeno.toUiModel(): AlergenoUiModel {
        return AlergenoUiModel(nombre = nombre)
    }

    fun filterProducts(
        products: List<ProductUiModel>,
        search: String,
        filter: ProductFilter
    ): List<ProductUiModel> {
        val query = search.trim().lowercase()

        return products.filter { product ->
            val matchesSearch =
                query.isBlank() ||
                        product.nombre.lowercase().contains(query) ||
                        product.alergenos.any { it.lowercase().contains(query) }

            val matchesFilter = when (filter) {
                ProductFilter.TODOS -> true
                ProductFilter.CON_STOCK -> product.stock > 0
                ProductFilter.SIN_STOCK -> product.stock <= 0
            }

            matchesSearch && matchesFilter
        }
    }

    fun parseAlergenosCsv(csv: String): Set<Alergeno> {
        return csv
            .split(",")
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .map { Alergeno(it) }
            .toSet()
    }

    fun alergenosToCsv(alergenos: List<String>): String {
        return alergenos.joinToString(", ")
    }
}