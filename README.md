# Spring Cloud for Azure 4.0 Sample
This sample is used to demonstrate how to use the Spring Cloud for Azure 4.0 starter. It contains two parts, the first part is to use Terraform to provision Azure resources and the second is to use the starter to try with Service client beans auto-configuration.

## Provision the Resources
Before running terraform, there're two parameters needed to be updated in the `./terraform/variables.tf` file. 

```hcl
variable "application_name" {
  type        = string
  description = "The name of your application"
  default     = "<your-alias>-sample"
}

variable "service_principal_id" {
  type        = string
  description = "The Azure Service Principal id or object_id to assign role to"
  default     = ""
}
```
The service principal id is the `Object Id` you could find from the portal by:

`Azure Active Directory` --> `Enterprise applications` --> .


After this you could run the terraform commands to provision resources.
```shell
cd terraform

# Login to azure-cli
az login
az account set --subscription <your-subscription-id>

# 
terraform init
terraform apply
```


## Copy the outputs from Terraform to Your IDE or Environment
```shell
# Make sure jq is installed before you run this command

 terraform output -json | jq -r '
  . as $in
  | keys[]
  | ($in[.].value | tostring) as $value
  | ($in[.].sensitive | tostring) as $sensitive
  | [
    (. | ascii_upcase) + "=" + $value
    ]
  | .[]'  
  
```
## Run the Sample Application
There are three profiles defined in this application, `credential-default`, `credential-sp`, and `credential-other`.

The `credential-default` profile will try to leverage your local developing environment, such as your credential stored in Intellij, VS Code, Azure CLI, Azure Powershell, etc.
The `credential-sp` profile requires a service principal, which is the same service principal you configured in the terraform variables.
The `credential-other` profile tries to use credentials other than a token credential, such as SAS token or access key.

Make sure you specify the corresponding profile before running the application.
