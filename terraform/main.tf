terraform {
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = ">= 2.75"
    }
    azurecaf = {
      source  = "aztfmod/azurecaf"
      version = "1.2.10"
    }
  }
}

provider "azurerm" {
  features {}
}

locals {
  // If an environment is set up (dev, test, prod...), it is used in the application name
  environment = var.environment == "" ? "dev" : var.environment
}

resource "azurecaf_name" "resource_group" {
  name          = var.application_name
  resource_type = "azurerm_resource_group"
  suffixes      = [local.environment]
}

resource "azurerm_resource_group" "main" {
  name     = azurecaf_name.resource_group.result
  location = var.location

  tags = {
    "terraform"        = "true"
    "environment"      = local.environment
    "application-name" = var.application_name
  }
}

# module "application" {
#   source           = "./modules/app-service"
#   resource_group   = azurerm_resource_group.main.name
#   application_name = var.application_name
#   environment      = local.environment
#   location         = var.location

#   vault_id = module.key-vault.vault_id

#   azure_storage_account_name  = module.storage-blob.azurerm_storage_account_name
#   azure_storage_blob_endpoint = module.storage-blob.azurerm_storage_blob_endpoint
#   azure_storage_account_key   = "@Microsoft.KeyVault(SecretUri=${module.key-vault.vault_uri}secrets/storage-account-key)"
# }

module "key-vault" {
  source               = "./modules/key-vault"
  resource_group       = azurerm_resource_group.main.name
  application_name     = var.application_name
  environment          = local.environment
  location             = var.location
  service_principal_id = var.service_principal_id

  storage_account_key = module.storage.storage_account_key
}

module "storage" {
  source               = "./modules/storage"
  resource_group       = azurerm_resource_group.main.name
  application_name     = var.application_name
  environment          = local.environment
  location             = var.location
  service_principal_id = var.service_principal_id
}

module "cosmos" {
  source               = "./modules/cosmos"
  resource_group       = azurerm_resource_group.main.name
  application_name     = var.application_name
  environment          = local.environment
  location             = var.location
  service_principal_id = var.service_principal_id
}

module "event-hubs" {
  source               = "./modules/event-hubs"
  resource_group       = azurerm_resource_group.main.name
  application_name     = var.application_name
  environment          = local.environment
  location             = var.location
  service_principal_id = var.service_principal_id
}

module "service-bus" {
  source               = "./modules/service-bus"
  resource_group       = azurerm_resource_group.main.name
  application_name     = var.application_name
  environment          = local.environment
  location             = var.location
  service_principal_id = var.service_principal_id
}

data "azurerm_client_config" "current" {}
