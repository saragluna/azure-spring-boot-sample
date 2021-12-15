terraform {
  required_providers {
    azurecaf = {
      source  = "aztfmod/azurecaf"
      version = "1.2.10"
    }
  }
}

data "azurerm_client_config" "current" {}

resource "azurecaf_name" "eventhub_namespace" {
  name          = var.application_name
  resource_type = "azurerm_eventhub_namespace"
  suffixes      = [var.environment]
}

resource "azurerm_eventhub_namespace" "application" {
  name                = azurecaf_name.eventhub_namespace.result
  location            = var.location
  resource_group_name = var.resource_group

  sku      = "Standard"
  capacity = 1

  zone_redundant           = false
  auto_inflate_enabled     = false
  maximum_throughput_units = 0
}

resource "azurecaf_name" "eventhub_namespace_authorization_rule" {
  name          = var.application_name
  resource_type = "azurerm_eventhub_namespace_authorization_rule"
  suffixes      = [var.environment]
}

resource "azurerm_eventhub_namespace_authorization_rule" "application" {
  name                = azurecaf_name.eventhub_namespace_authorization_rule.result
  namespace_name      = azurerm_eventhub_namespace.application.name
  resource_group_name = var.resource_group

  listen = true
  send   = true
  manage = true
}

resource "azurecaf_name" "eventhub" {
  name          = var.application_name
  resource_type = "azurerm_eventhub"
  suffixes      = [var.environment]
}

resource "azurerm_eventhub" "application" {
  name                = azurecaf_name.eventhub.result
  namespace_name      = azurerm_eventhub_namespace.application.name
  resource_group_name = var.resource_group
  partition_count     = 4
  message_retention   = 1
}

resource "azurecaf_name" "eventhub_consumer_group" {
  name          = var.application_name
  resource_type = "azurerm_eventhub_consumer_group"
  suffixes      = [var.environment]
}

resource "azurerm_eventhub_consumer_group" "application" {
  name                = azurecaf_name.eventhub_consumer_group.result
  namespace_name      = azurerm_eventhub_namespace.application.name
  eventhub_name       = azurerm_eventhub.application.name
  resource_group_name = var.resource_group
}

resource "azurerm_role_assignment" "eventhub_data_owner" {
  scope                = azurerm_eventhub_namespace.application.id
  role_definition_name = "Azure Event Hubs Data Owner"
  principal_id         = var.service_principal_id
}

resource "azurerm_role_assignment" "eventhub_data_owner_user" {
  scope                = azurerm_eventhub_namespace.application.id
  role_definition_name = "Azure Event Hubs Data Owner"
  principal_id         = data.azurerm_client_config.current.object_id
}

