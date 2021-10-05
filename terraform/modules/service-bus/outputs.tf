output "servicebus_namespace_name" {
  value       = azurerm_servicebus_namespace.application.name
  description = "The Azure Service Bus namespace name."
}

output "servicebus_queue_name" {
  value       = azurerm_servicebus_queue.application.name
  description = "The Azure Service Bus queue name."
}

output "servicebus_topic_name" {
  value       = azurerm_servicebus_topic.application.name
  description = "The Azure Service Bus topic name."
}

output "servicebus_subscription_name" {
  value       = azurerm_servicebus_subscription.application.name
  description = "The Azure Service Bus subscription name."
}

output "servicebus_connection_string" {
  value       = azurerm_servicebus_namespace_authorization_rule.application.primary_connection_string
  description = "The Azure Service Bus namespace primary connection string."
}