package nl.inholland.healthysnackapp.models.responsebodies

data class ProductResponse(
    val code: String?,
    val product: SubProductResponse?,
    val status: Int?,
    val status_verbose: String?
)

data class SubProductResponse(
    val id: String?,
    val product_name: String?,
    val brands: String?,
    val categories: String?,
    val labels: String?,
    val quantity: String?,
    val packaging: String?,
    val ingredients_text_en: String?,
    val image_url: String?,
    val nutriments: Nutriments?,
    val ingredients_analysis_tags: List<String>?,
    val countries: String?,
    val countries_tags: List<String>?,
    val ecoscore_grade: String?,
    val ecoscore_score: Double?,
    val nutriscore_grade: String?,
    val allergens: String?,
    val additives_tags: List<String>?,
    val languages_tags: List<String>?
)

data class Nutriments(
    val energy: Double?,
    val energy100g: Double?,
    val sugars: Double?,
    val sugars100g: Double?
)
