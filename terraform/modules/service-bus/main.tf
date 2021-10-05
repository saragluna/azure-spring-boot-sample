terraform {
  required_providers {
    azurecaf = {
      source  = "aztfmod/azurecaf"
      version = "1.2.6"
    }
  }
}

data "azurerm_client_config" "current" {}

resource "azurecaf_name" "servicebus_namespace" {
  name          = var.application_name
  resource_type = "azurerm_servicebus_namespace"
  suffixes      = [var.environment]
}

resource "azurerm_servicebus_namespace" "application" {
  name                = azurecaf_name.servicebus_namespace.result
  location            = var.location
  resource_group_name = var.resource_group

  sku            = "Standard"
  zone_redundant = false
}

resource "azurecaf_name" "servicebus_namespace_authorization_rule" {
  name          = var.application_name
  resource_type = "azurerm_servicebus_namespace_authorization_rule"
  suffixes      = [var.environment]
}

resource "azurerm_servicebus_namespace_authorization_rule" "application" {
  name                = azurecaf_name.servicebus_namespace_authorization_rule.result
  namespace_name      = azurerm_servicebus_namespace.application.name
  resource_group_name = var.resource_group

  listen = true
  send   = true
  manage = true
}

resource "azurecaf_name" "queue" {
  name          = var.application_name
  resource_type = "azurerm_servicebus_queue"
  suffixes      = [var.environment]
}

resource "azurerm_servicebus_queue" "application" {
  name                = azurecaf_name.queue.result
  namespace_name      = azurerm_servicebus_namespace.application.name
  resource_group_name = var.resource_group

  enable_partitioning   = false
  max_delivery_count    = 10
  lock_duration         = "PT30S"
  max_size_in_megabytes = 1024
  requires_session      = false
  default_message_ttl   = "P14D"
}

resource "azurecaf_name" "topic" {
  name          = var.application_name
  resource_type = "azurerm_servicebus_topic"
  suffixes      = [var.environment]
}

resource "azurerm_servicebus_topic" "application" {
  name                = azurecaf_name.topic.result
  namespace_name      = azurerm_servicebus_namespace.application.name
  resource_group_name = var.resource_group
}

resource "azurecaf_name" "subscription" {
  name          = var.application_name
  resource_type = "azurerm_servicebus_subscription"
  suffixes      = [var.environment]
}

resource "azurerm_servicebus_subscription" "application" {
  name                = azurecaf_name.subscription.result
  resource_group_name = var.resource_group
  namespace_name      = azurerm_servicebus_namespace.application.name
  topic_name          = azurerm_servicebus_topic.application.name
  max_delivery_count  = 1
}

resource "azurerm_role_assignment" "servicebus_data_owner" {
  scope                = azurerm_servicebus_namespace.application.id
  role_definition_name = "Azure Service Bus Data Owner"
  principal_id         = var.service_principal_id
}

resource "azurerm_role_assignment" "servicebus_data_owner_user" {
  scope                = azurerm_servicebus_namespace.application.id
  role_definition_name = "Azure Service Bus Data Owner"
  principal_id         = data.azurerm_client_config.current.object_id
}


