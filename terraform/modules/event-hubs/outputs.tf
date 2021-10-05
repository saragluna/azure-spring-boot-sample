output "eventhubs_namespace_name" {
  value       = azurerm_eventhub_namespace.application.name
  description = "The Azure Event Hubs namespace name."
}

output "eventhubs_eventhub_name" {
  value       = azurerm_eventhub.application.name
  description = "The Azure Event Hubs event hub name."
}

output "eventhubs_consumer_group_name" {
  value       = azurerm_eventhub_consumer_group.application.name
  description = "The Azure Event Hubs event hub consumer group name."
}

output "eventhubs_connection_string" {
  value       = azurerm_eventhub_namespace_authorization_rule.application.primary_connection_string
  description = "The Azure Event Hubs namespace primary connection string."
}