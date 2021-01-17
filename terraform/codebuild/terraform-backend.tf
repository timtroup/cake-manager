terraform {
  backend "s3" {
    key = "codebuild.tfstate"
    bucket = "cakemgr-terraform-state"
    dynamodb_table = "app-state"
    region = "eu-west-1"
  }
}
