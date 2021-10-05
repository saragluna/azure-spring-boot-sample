output "storage_account_name" {
  value       = azurerm_storage_account.application.name
  description = "The Azure Blob storage account name."
}

output "storage_account_key" {
  value       = azurerm_storage_account.application.primary_access_key
  sensitive   = true
  description = "The Azure Blob storage access key."
}

output "storage_blob_endpoint" {
  value       = azurerm_storage_account.application.primary_blob_endpoint
  description = "The Azure Blob storage endpoint."
}

output "storage_container_name" {
  value       = azurerm_storage_container.application.name
  description = "The Azure Storage blob container name."
}

output "storage_share_name" {
  value       = azurerm_storage_share.application.name
  description = "The Azure Stroage share name name."
}