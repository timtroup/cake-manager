module "cake-manager" {
  source = "../modules/codebuild"
  app_name = "cake-manager"
  source_location = "timtroup/cake-manager"
  protected_branch = "master"
  compute_type = "BUILD_GENERAL1_LARGE"
  image = "aws/codebuild/amazonlinux2-x86_64-standard:3.0"
  vpc_id = data.aws_vpc.current.id
  subnets = data.aws_subnet_ids.current.ids
  security_group_ids = [
    data.aws_security_group.current.id]
  github_personal_access_token = var.github_personal_access_token
  github_org = var.github_org
  enable_webhook = true
  source_branch = "master"
  tags = {}
}