terraform {
  required_providers {
    azurecaf = {
      source  = "aztfmod/azurecaf"
      version = "1.2.6"
    }
  }
}

resource "azurecaf_name" "cosmos" {
  name          = var.application_name
  resource_type = "azurerm_cosmosdb_account"
  suffixes      = [var.environment]
}

resource "azurerm_cosmosdb_account" "application" {
  name                = azurecaf_name.cosmos.result
  location            = var.location
  resource_group_name = var.resource_group
  offer_type          = "Standard"
  kind                = "GlobalDocumentDB"

  enable_automatic_failover = true

  consistency_policy {
    consistency_level = "Session"
  }

  geo_location {
    location          = var.location
    failover_priority = 0
  }
}

resource "azurerm_cosmosdb_sql_database" "db" {
  name                = "products"
  resource_group_name = var.resource_group
  account_name        = azurerm_cosmosdb_account.application.name
  throughput          = 400
}