export COSMOS_DATABASE_NAME=$(terraform output -raw cosmos_database_name)
export COSMOS_CONTAINER_NAME=$(terraform output -raw cosmos_container_name)
export COSMOS_ENDPOINT=$(terraform output -raw cosmos_endpoint)
export COSMOS_KEY=$(terraform output -raw cosmos_key)
export EVENTHUBS_CONSUMER_GROUP_NAME=$(terraform output -raw eventhubs_consumer_group_name)
export EVENTHUBS_EVENTHUB_NAME=$(terraform output -raw eventhubs_eventhub_name)
export EVENTHUBS_NAMESPACE_NAME=$(terraform output -raw eventhubs_namespace_name)
export KEY_VAULT_SECRET_NAME=$(terraform output -raw key_vault_secret_name)
export KEY_VAULT_URI=$(terraform output -raw key_vault_uri)
export RESOURCE_GROUP=$(terraform output -raw resource_group)
export SERVICEBUS_CONNECTION_STRING=$(terraform output -raw servicebus_connection_string)
export SERVICEBUS_NAMESPACE_NAME=$(terraform output -raw servicebus_namespace_name)
export SERVICEBUS_QUEUE_NAME=$(terraform output -raw servicebus_queue_name)
export SERVICEBUS_SUBSCRIPTION_NAME=$(terraform output -raw servicebus_subscription_name)
export SERVICEBUS_TOPIC_NAME=$(terraform output -raw servicebus_topic_name)
export STORAGE_ACCOUNT_KEY=$(terraform output -raw storage_account_key)
export STORAGE_ACCOUNT_NAME=$(terraform output -raw storage_account_name)
export STORAGE_BLOB_ENDPOINT=$(terraform output -raw storage_blob_endpoint)
export STORAGE_CONTAINER_NAME=$(terraform output -raw storage_container_name)
export STORAGE_QUEUE_NAME=$(terraform output -raw storage_queue_name)
export STORAGE_SHARE_NAME=$(terraform output -raw storage_share_name)

azure_cosmos_account=$(terraform output -raw azure_cosmos_account)
resource_group=$(terraform output -raw resource_group)
principal_id=$(terraform output -raw object_id)
read_only_role_definition_id=$(terraform output -raw cosmos_application_id)/sqlRoleDefinitions/00000000-0000-0000-0000-000000000001
write_only_role_definition_id=$(terraform output -raw cosmos_application_id)/sqlRoleDefinitions/00000000-0000-0000-0000-000000000002
# assign current Cosmos DB Built-in Data Reader
# https://docs.microsoft.com/azure/cosmos-db/how-to-setup-rbac#using-the-azure-cli-1
az cosmosdb sql role assignment create --account-name $azure_cosmos_account --resource-group $resource_group --scope "/" --principal-id $principal_id --role-definition-id $read_only_role_definition_id
# assign current Cosmos DB Built-in Data Contributor
az cosmosdb sql role assignment create --account-name $azure_cosmos_account --resource-group $resource_group --scope "/" --principal-id $principal_id --role-definition-id $write_only_role_definition_id
