package app.masterclass.kotlinmasterclassapplication.whatsapp.model

/**
 * @author Martin Trollip
 */
data class WhatsappUser(var displayName: String, var status: String = "Hello there!", var image: String = "default")