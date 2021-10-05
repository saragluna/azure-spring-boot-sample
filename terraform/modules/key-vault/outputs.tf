output "key_vault_uri" {
  value       = azurerm_key_vault.application.vault_uri
  description = "The Azure Key Vault URI"
}

output "key_vault_secret_name" {
  value       = azurerm_key_vault_secret.spring_storage_account_key.name
  description = "The Azure Key Vault secret name"
}
