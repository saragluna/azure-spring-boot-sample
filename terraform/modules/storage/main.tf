terraform {
  required_providers {
    azurecaf = {
      source = "aztfmod/azurecaf"
      version = "1.2.6"
    }
  }
}

resource "azurecaf_name" "storage_account" {
  name          = var.application_name
  resource_type = "azurerm_storage_account"
  suffixes      = [var.environment]
}

resource "azurerm_storage_account" "storage" {
  name                     = azurecaf_name.storage_account.result
  resource_group_name      = var.resource_group
  location                 = var.location
  account_tier             = "Standard"
  account_replication_type = "LRS"
  allow_blob_public_access = true

  tags = {
    "environment"      = var.environment
    "application-name" = var.application_name
  }
}

resource "azurecaf_name" "storage_container" {
  name          = var.application_name
  resource_type = "azurerm_storage_container"
  suffixes      = [var.environment]
}

resource "azurerm_storage_container" "storage_container" {
  name                  = azurecaf_name.storage_container.result
  storage_account_name  = azurerm_storage_account.storage.name
  container_access_type = "container"
}

resource "azurerm_role_assignment" "storage-blob-data-contributor-role" {
  scope                = azurerm_storage_container.storage_container.resource_manager_id
  role_definition_name = "Storage Blob Data Contributor"
  principal_id         = var.service_principal_id
}

resource "azurecaf_name" "storage_share" {
  name          = var.application_name
  resource_type = "azurerm_storage_share"
  suffixes      = [var.environment]
}

resource "azurerm_storage_share" "storage-share" {
  name                  = azurecaf_name.storage_share.result
  storage_account_name  = azurerm_storage_account.storage.name
}


