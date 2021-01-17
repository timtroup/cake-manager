data "aws_region" "current" {}
data "aws_caller_identity" "current" {}
data "aws_vpc" "current" {
  filter {
    name = "tag:Name"
    values = [
      "somerled-vpc"]
  }
}

data "aws_subnet_ids" "current" {
  vpc_id = data.aws_vpc.current.id

  filter {
    name = "tag:Name"
    values = [
      "somerled-private-subnet"]
  }
}

data "aws_security_group" "current" {
  vpc_id = data.aws_vpc.current.id
  name = "default"
}
