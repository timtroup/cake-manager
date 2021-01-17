provider "aws" {
  region = "eu-west-1"

  allowed_account_ids = flatten([
    var.account_id])

  # Doesn't work with MFA's yet.
  # https://github.com/terraform-providers/terraform-provider-aws/issues/5078
  # assume_role {
  #   role_arn = "arn:aws:iam::<account-number>:role/admin"
  # }

  # Make it faster by skipping something
  skip_get_ec2_platforms = true
  skip_metadata_api_check = true
  skip_region_validation = true
  skip_credentials_validation = true
}

provider "null" {}
provider "random" {}
provider "template" {}

terraform {
  required_providers {
    aws = {
      source = "hashicorp/aws"
      version = "3.22.0"
    }
    null = {
      source = "hashicorp/null"
      version = "~> 2.1"
    }
    random = {
      source = "hashicorp/random"
      version = "~> 2.2"
    }
    template = {
      source = "hashicorp/template"
      version = "~> 2.1"
    }
  }
}
