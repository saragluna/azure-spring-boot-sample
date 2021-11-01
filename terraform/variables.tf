variable "application_name" {
  type        = string
  description = "The name of your application"
  default     = "moaryc-sample"
}

variable "environment" {
  type        = string
  description = "The environment (dev, test, prod...)"
  default     = "dat"
}

variable "location" {
  type        = string
  description = "The Azure region where all resources in this example should be created"
  default     = "eastasia"
}

variable "service_principal_id" {
  type        = string
  description = "The Azure Service Principal object_id to assign role to, not the application id here"
  default     = "0dfd3115-09fb-442f-9798-c91a139a53be"
}
