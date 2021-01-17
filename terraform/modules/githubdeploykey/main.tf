locals {
  github_parts = split("/", var.github_org_repo)
  github_org = local.github_parts[0]
  github_repo = local.github_parts[1]
}

resource "tls_private_key" "main" {
  algorithm = "RSA"
  rsa_bits = "4096"
}

# Add a deploy key
resource "github_repository_deploy_key" "main" {
  title = "${var.name}-${data.aws_region.current.name}-${data.aws_caller_identity.current.account_id}"
  repository = local.github_repo
  key = tls_private_key.main.public_key_openssh
  read_only = var.read_only
}
