package nl.inholland.healthysnackapp.models.responsebodies

data class ProductResponse(
    val code: String?,
    val product: SubProductResponse?,
    val status: Int?,
    val statusVerbose: String?
)

data class SubProductResponse(
    val id: String?,
    val product_name: String?,
    val brands: String?,
    val categories: String?,
    val labels: String?,
    val quantity: String?,
    val packaging: String?,
    val ingredientsText: String?,
    val image_url: String?,
    val nutriments: Nutriments?,
    val ingredientsAnalysisTags: List<String>?,
    val countries: String?,
    val countriesTags: List<String>?,
    val ecoscoreGrade: String?,
    val ecoscoreScore: Double?,
    val nutriscore_grade: String?,
    val allergens: String?,
    val additivesTags: List<String>?,
    val languagesTags: List<String>?
)

data class Nutriments(
    val energy: Double?,
    val energy100g: Double?,
    val sugars: Double?,
    val sugars100g: Double?
)
