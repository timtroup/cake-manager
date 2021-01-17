module "githubdeploykey" {
  source = "../githubdeploykey"
  github_org_repo = var.source_location
  read_only = false
}

resource "aws_ssm_parameter" "deploy_key" {
  name = "/codebuild/github/deploy_key/${var.app_name}"
  type = "SecureString"
  value = module.githubdeploykey.private_key_pem
}

resource "github_branch_protection" "master" {
  repository_id = split("/", var.source_location)[1]
  pattern = var.protected_branch
  enforce_admins = false

  required_status_checks {
    contexts = [
      "AWS CodeBuild eu-west-1 (${var.app_name})",
    ]
    strict = false
  }

  required_pull_request_reviews {
    dismiss_stale_reviews = false
    dismissal_restrictions = []
    require_code_owner_reviews = false
    required_approving_review_count = 1
  }

  push_restrictions = []
}
