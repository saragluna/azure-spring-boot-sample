output "cosmos_endpoint" {
  value       = azurerm_cosmosdb_account.application.endpoint
  description = "The Azure Cosmos DB endpoint."
}

output "cosmos_database_name" {
  value       = azurerm_cosmosdb_sql_database.db.name
  description = "The Azure Cosmos DB sql database name."
}

output "cosmos_key" {
  value       = azurerm_cosmosdb_account.application.primary_key
  description = "The Azure Cosmos DB primary key."
}
