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

data class ProductFormState(
    val nombre: String = "",
    val precio: String = "",
    val stock: String = "",
    val alergenosCsv: String = ""
)

data class AlergenoFormState(
    val nombre: String = ""
)

data class ManageProductsUiState(
    val selectedTab: ManageProductsTab = ManageProductsTab.PRODUCTOS,
    val search: String = "",
    val selectedFilter: ProductFilter = ProductFilter.TODOS,

    val products: List<Producto> = emptyList(),
    val alergenos: List<Alergeno> = emptyList(),

    val isProductDialogVisible: Boolean = false,
    val editingProduct: Producto? = null,
    val productForm: ProductFormState = ProductFormState(),

    val isAlergenoDialogVisible: Boolean = false,
    val editingAlergeno: Alergeno? = null,
    val alergenoForm: AlergenoFormState = AlergenoFormState()
)

object ManageProductsUtils {

    val PuntoCorteEscritorio: Dp = 700.dp

    fun esEscritorio(maxWidth: Dp): Boolean {
        return maxWidth >= PuntoCorteEscritorio
    }

    fun filterProducts(
        products: List<Producto>,
        search: String,
        filter: ProductFilter
    ): List<Producto> {
        val query = search.trim().lowercase()

        return products.filter { product ->
            val matchesSearch =
                query.isBlank() ||
                        product.nombre.lowercase().contains(query) ||
                        product.alergenos.any { it.nombre.lowercase().contains(query) }

            val matchesFilter = when (filter) {
                ProductFilter.TODOS -> true
                ProductFilter.CON_STOCK -> product.stock > 0
                ProductFilter.SIN_STOCK -> product.stock <= 0
            }

            matchesSearch && matchesFilter
        }
    }


    fun alergenosToCsv(alergenos: Iterable<Alergeno>): String {
        return alergenos.joinToString(", ") { it.nombre }
    }
}