# Codebuild Infrastructure as Code

[Terraform](https://www.terraform.io/intro/index.html) is a tool for building, changing, and versioning infrastructure
safely and efficiently. Here we are using terraform to provision AWS codebuild infrastructure such that a build is run
each time code is pushed to the Github repository.

## Bootstrap the terraform backend

The first step to using terraform is to provision a backend that terraform can use to store state. This can be achieved
by:

`cd remote-state && terraform apply`

This will create an s3 bucket to store the state. Since s3 bucket names need to be globally unique you may need to
modify the main.tf file.

## Create Codebuild Infrastructure with Github Webhook

It is assumed that you already have an AWS account set up with a VPC with both public and private subnets and a NAT
Gateway.

First modify codebuild/terraform-variables.tf to hold the AWS account ID for the AWS account you wish to run codebuild
from and specify the github root account name for the github account you wish to run the build for.

Then run the following command to provision codebuild infrastructure that will build the specified code each time code
is pushed to the Github repository:

`cd codebuild && terraform apply`

