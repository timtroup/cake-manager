terraform {
  required_version = ">= 0.14"

  required_providers {
    github = {
      source = "integrations/github"
      version = "4.2.0"
    }
  }
}

provider "github" {
  organization = var.github_org
  token = var.github_personal_access_token
}
