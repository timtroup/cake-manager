variable "source_location" {}
variable "app_name" {}

variable "enable_webhook" {
  description = "Whether or not to enable the Github Webhook to triger the build"
  type = bool
  default = false
}

variable "source_branch" {
  description = "The branch name to monitor and use to trigger the pipeline. On production pipelines, this should be main or master."
  type = string
  default = "development"
}

variable "protected_branch" {
  description = "The main branch in the repository to which PRs are merged into. This should be main or master."
  default = "main"
}

variable "tags" {
  type = map(string)
  default = {}
}

variable "git_clone_depth" {
  description = "0 for full clone, 1 or more for shallow clone"
  type = number
  default = 0
}

variable "github_personal_access_token" {
  type = string
}

variable "compute_type" {
  default = "BUILD_GENERAL1_SMALL"
  type = string
}

variable "build_timeout" {
  default = 60
  type = number
}

variable "github_org" {
  description = "GitHub organization name"
  type = string
}

variable "artifacts" {
  description = "Whether or not to export any artifacts. Valid values are NO_ARTIFACTS or S3."
  default = "NO_ARTIFACTS"
}

variable "packaging" {
  description = "Whether or not to zip the artifacts. Default: NONE Optional: ZIP"
  type = string
  default = "NONE"
}

variable "package_name" {
  description = "The name of the artifact file. For example bootstrap.zip or app.zip"
  type = string
  default = null
}

variable "namespace_type" {
  description = "The namespace to use in storing build artifacts. If type is set to S3, then valid values for this parameter are: BUILD_ID or NONE."
  type = string
  default = "NONE"
}

variable "image" {
  type = string
  default = "aws/codebuild/amazonlinux2-x86_64-standard:3.0"
}

# TODO: vpc config with vpc_id, subnets and security_group_ids should be removed when we move away from on-prem Nexus.
# Giving the build system access to the VPC is a security risk.
variable "subnets" {
  type = list(string)
}
variable "vpc_id" {}
variable "security_group_ids" {
  type = list(string)
}
